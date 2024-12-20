package com.project.base.auth.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDTO {
    public String password;
    public String clientId;
    public String grantType;
    public String username;
}
