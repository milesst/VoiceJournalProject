package com.vjserver.vjserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    private String userLogin;
    private String userPassword;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String role;

    public User(Integer id, String userLogin, String userPassword, String firstName, String lastName, String patronymic, String role) {
        super(id);
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.role = role;
    }
}