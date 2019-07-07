package com.example2.demo.services;

import com.example2.demo.converters.UserEntityUserDataMapper;
import com.example2.demo.dao.HashRepository;
import com.example2.demo.dao.UserRepository;
import com.example2.demo.data.UserData;
import com.example2.demo.exception.UserFoundException;
import com.example2.demo.model.RoleEntity;
import com.example2.demo.model.UserEntity;
import com.example2.demo.model.UserTokenEntity;
import com.example2.demo.model.enums.ActivationType;
import com.example2.demo.model.enums.Role;
import com.example2.demo.queue.RegistrationEmailSender;
import com.example2.demo.util.SecurityUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.activation.ActivationException;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private UserEntityUserDataMapper userEntityUserDataMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RegistrationEmailSender registrationEmailSender;
    private HashRepository hashRepository;

    public UserService(UserRepository userRepository, UserEntityUserDataMapper userEntityUserDataMapper, BCryptPasswordEncoder bCryptPasswordEncoder,
                       RegistrationEmailSender registrationEmailSender, HashRepository hashRepository) {
        this.userRepository = userRepository;
        this.userEntityUserDataMapper = userEntityUserDataMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.registrationEmailSender = registrationEmailSender;
        this.hashRepository = hashRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity userEntityByLogin = userRepository.findUserEntityByLogin(login);
        if (userEntityByLogin == null) {
            throw new UsernameNotFoundException(login);
        }
        Set<GrantedAuthority> role = new HashSet<>();
        userEntityByLogin.getRoleEntityList().stream()
                .forEach(roleInternal -> role.add(new SimpleGrantedAuthority(role.toString())));
        return new User(userEntityByLogin.getLogin(), userEntityByLogin.getPassword(), userEntityByLogin.isActive(), true, true, true, role);
    }

    @Transactional(readOnly = true)
    public void addUser(UserData userData) {
        if (userRepository.findUserEntityByLogin(userData.getLogin()) != null) {
            throw new UserFoundException();
        }
        UserEntity userEntity = userEntityUserDataMapper.toEntity(userData);
        setRole(userEntity);
        setToken(userEntity);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setActive(Boolean.FALSE);
        userRepository.save(userEntity);
        addToQueue(userEntity);
    }

    public void confirmAccount(String hash) throws ActivationException {
        UserTokenEntity userTokenEntityByHash = hashRepository.findActivationEntityByHashAndActivationTimestampIsNull(hash);
        if (userTokenEntityByHash == null) {
            throw new ActivationException("token not found");
        }
        userTokenEntityByHash.setActivationTimestamp(LocalDateTime.now());
        UserEntity userEntity = userTokenEntityByHash.getUserEntity();
        userEntity.setActive(Boolean.TRUE);
        hashRepository.save(userTokenEntityByHash);
    }

    public Long getLoggedUserId() {
        String userName = SecurityUtil.getUserName();
        UserEntity userEntityByLogin = userRepository.findUserEntityByLogin(userName);
        if (userEntityByLogin != null) {
            return userEntityByLogin.getId();
        }
        return null;
    }

    private void addToQueue(UserEntity userEntity) {
        Map<String, String> map = new HashMap<>();
        map.put("email", userEntity.getLogin());
        map.put("hash", userEntity.getLastHash());
        registrationEmailSender.send(map);
    }

    private void setToken(UserEntity userEntity) {
        UserTokenEntity userTokenEntity = new UserTokenEntity();
        userTokenEntity.setHash(UUID.randomUUID().toString());
        userTokenEntity.setActivationType(ActivationType.EMAIL);
        List<UserTokenEntity> userTokenEntityList = new ArrayList<>();
        userTokenEntityList.add(userTokenEntity);
        userEntity.setUserTokenEntityList(userTokenEntityList);
    }

    private void setRole(UserEntity userEntity) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(Role.USER);
        List<RoleEntity> roleEntityList = new ArrayList<>();
        roleEntityList.add(roleEntity);
        userEntity.setRoleEntityList(roleEntityList);
    }
}