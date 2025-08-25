package com.cg.spring_boot_microservice_3_api_gateway.service;

import com.cg.spring_boot_microservice_3_api_gateway.model.Role;
import com.cg.spring_boot_microservice_3_api_gateway.model.User;

import java.util.Optional;

public interface UserService
{
    User saveUser(User user);

    Optional<User> findByUsername(String username);

    void changeRole(Role newRole, String username);
}