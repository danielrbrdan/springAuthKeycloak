package com.project.base.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {
    public String password;
    public String clientId;
    public String grantType;
    public String username;
    public String email;
    public String firstName;
    public String lastName;
    public String phoneNumber;
}
