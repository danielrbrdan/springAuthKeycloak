package com.project.base.auth;

import javax.ws.rs.core.Response;

import org.keycloak.representations.AccessTokenResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.base.auth.dto.UserDTO;
import com.project.base.auth.keycloack.KeycloakService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class TokenController {
    private final KeycloakService keycloakService;

    @PostMapping("/token")
    public AccessTokenResponse token(@RequestBody UserDTO user) {
        return this.keycloakService.login(user);
    }

    @PostMapping("/create")
    public Response createUser(@RequestBody UserDTO user) {
        return this.keycloakService.createUser(user);
    }

}