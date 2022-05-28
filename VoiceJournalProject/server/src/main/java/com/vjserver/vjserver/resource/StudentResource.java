package com.vjserver.vjserver.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vjserver.vjserver.entity.Student;
import com.vjserver.vjserver.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResource extends BaseResource {
    private Integer id;
    private Integer groupId;
    private String firstName;
    private String lastName;
    private String patronymic;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StudentGroupResource group;

    public StudentResource(Student student) {
        this.id = student.getId();
        this.groupId = student.getGroupId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.patronymic = student.getPatronymic();
    }

    public Student toEntity() {
        return new Student(
                this.id,
                this.groupId,
                this.firstName,
                this.lastName,
                this.patronymic
        );
    }
}