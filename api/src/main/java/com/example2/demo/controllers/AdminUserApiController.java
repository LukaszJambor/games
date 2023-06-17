package com.example2.demo.controllers;

import com.example2.demo.model.enums.Role;
import com.example2.demo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
