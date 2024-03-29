package com.example2.demo.dao;

import com.example2.demo.model.UserEntity;
import com.example2.demo.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByLogin(String login);

    @Query("select r.role FROM RoleEntity r where r.userKey=:userId")
    List<Role> findUserRoles(Long userId);
}