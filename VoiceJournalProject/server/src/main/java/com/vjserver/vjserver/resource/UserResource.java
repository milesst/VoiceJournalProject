package com.vjserver.vjserver.resource;

import com.vjserver.vjserver.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResource extends BaseResource {
    private Integer id;
    private String userLogin;
    private String userPassword;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String role;

    public UserResource(User user) {
        this.id = user.getId();
        this.userLogin = user.getUserLogin();
        this.userPassword = user.getUserPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.patronymic = user.getPatronymic();
        this.role = user.getRole();
    }

    public User toEntity() {
        return new User(
                this.id,
                this.userLogin,
                this.userPassword,
                this.firstName,
                this.lastName,
                this.patronymic,
                this.role
        );
    }
}