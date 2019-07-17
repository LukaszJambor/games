package com.example2.demo.services;

import dao.HashRepository;
import dao.UserRepository;
import com.example2.demo.exception.UserFoundException;
import model.RoleEntity;
import model.UserEntity;
import model.UserTokenEntity;
import model.WalletEntity;
import model.enums.ActivationType;
import model.enums.Role;
import com.example2.demo.queue.RegistrationEmailSender;
import com.example2.demo.util.SecurityUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.activation.ActivationException;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RegistrationEmailSender registrationEmailSender;
    private HashRepository hashRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                       RegistrationEmailSender registrationEmailSender, HashRepository hashRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.registrationEmailSender = registrationEmailSender;
        this.hashRepository = hashRepository;
    }

    @Transactional
    public void addUser(UserEntity userEntity) {
        UserEntity userEntityByLogin = userRepository.findUserEntityByLogin(userEntity.getLogin());

        if (userEntityByLogin != null && userEntityByLogin.getActive()) {
            throw new UserFoundException();
        }
        if (userEntityByLogin != null && !userEntityByLogin.getActive() && !userEntityByLogin.isActivationHashAvailable(ActivationType.EMAIL)) {
            setToken(userEntityByLogin);
            userRepository.save(userEntityByLogin);
            addToQueue(userEntityByLogin);
        }
        if (userEntityByLogin == null) {
            setRole(userEntity);
            setToken(userEntity);
            setWallet(userEntity);
            userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
            userEntity.setActive(Boolean.FALSE);
            userRepository.save(userEntity);
            addToQueue(userEntity);
        }
    }

    private void setWallet(UserEntity userEntity) {
        WalletEntity walletEntity = new WalletEntity();
        userEntity.setWallet(walletEntity);
    }

    public void confirmAccount(String hash) throws ActivationException {
        UserTokenEntity userTokenEntityByHash = hashRepository.findActivationEntityByHashAndActivationTimestampIsNull(hash);
        if (userTokenEntityByHash == null) {
            throw new ActivationException("token not found");
        }
        userTokenEntityByHash.setActivationTimestamp(LocalDateTime.now());
        UserEntity userEntity = userTokenEntityByHash.getUser();
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
        map.put("hash", userEntity.getLastHash(ActivationType.EMAIL));
        registrationEmailSender.send(map);
    }

    private void setToken(UserEntity userEntity) {
        UserTokenEntity userTokenEntity = new UserTokenEntity();
        userTokenEntity.setHash(UUID.randomUUID().toString());
        userTokenEntity.setActivationType(ActivationType.EMAIL);
        List<UserTokenEntity> userTokenEntityList = new ArrayList<>();
        userTokenEntityList.add(userTokenEntity);
        userEntity.setUserTokens(userTokenEntityList);
    }

    private void setRole(UserEntity userEntity) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(Role.USER);
        List<RoleEntity> roleEntityList = new ArrayList<>();
        roleEntityList.add(roleEntity);
        userEntity.setRoles(roleEntityList);
    }
}