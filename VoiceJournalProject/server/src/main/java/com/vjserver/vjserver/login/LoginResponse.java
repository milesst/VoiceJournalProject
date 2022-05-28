package com.vjserver.vjserver.login;

import com.vjserver.vjserver.resource.UserResource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String token;
}
