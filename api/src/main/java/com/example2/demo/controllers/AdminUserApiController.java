package com.example2.demo.controllers;

import com.example2.demo.model.RoleEntity;
import com.example2.demo.model.UserEntity;
import com.example2.demo.model.enums.Role;
import com.example2.demo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/restapi/v1")
public class AdminUserApiController {

    private UserService userService;

    public AdminUserApiController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/users/{userId}/roles")
    public ResponseEntity<List<Role>> getUserRoles(@PathVariable("userId") Long userId) {
        List<Role> userRoles = userService.getUserRoles(userId);
        if (CollectionUtils.isEmpty(userRoles)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userRoles);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "users/{userId}/roles")
    public ResponseEntity<List<Role>> addUserRole(@PathVariable("userId") Long userId, @RequestBody List<Role> roles) {
        UserEntity userEntity = userService.addRole(userId, roles);
        return ResponseEntity.ok(getRoles(userEntity));
    }

    private static List<Role> getRoles(UserEntity userEntity) {
        return userEntity.getRoles().stream().map(RoleEntity::getRole).collect(Collectors.toList());
    }
}
