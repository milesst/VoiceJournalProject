package com.vjserver.vjserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student extends BaseEntity {
    private Integer groupId;
    private String firstName;
    private String lastName;
    private String patronymic;

    public Student(Integer id, Integer groupId, String firstName, String lastName, String patronymic) {
        super(id);
        this.groupId = groupId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }
}