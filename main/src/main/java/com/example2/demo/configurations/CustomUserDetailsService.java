package com.example2.demo.configurations;

import com.example2.demo.annotation.CustomUser;
import com.example2.demo.dao.UserRepository;
import com.example2.demo.model.RoleEntity;
import com.example2.demo.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final String ROLE = "ROLE_";
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity userEntityByLogin = userRepository.findUserEntityByLogin(login);
        if (userEntityByLogin == null) {
            throw new UsernameNotFoundException(login);
        }
        return new CustomUser(userEntityByLogin.getLogin(), userEntityByLogin.getPassword(), Boolean.TRUE, Boolean.TRUE,
                Boolean.TRUE, Boolean.TRUE, buildUserAuthority(userEntityByLogin.getRoles()), userEntityByLogin.getId());
    }

    private List<GrantedAuthority> buildUserAuthority(List<RoleEntity> userRoles) {

        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(ROLE + role.getRole().toString()))
                .collect(Collectors.toList());
    }
}
