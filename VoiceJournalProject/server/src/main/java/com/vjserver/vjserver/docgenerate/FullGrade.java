package com.vjserver.vjserver.docgenerate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FullGrade {
    private Integer studentId;
    private String studentFullName;
    private Integer grade;

    FullGrade(Integer id, String name, Integer grade) {
        studentId = id;
        studentFullName = name;
        this.grade = grade;
    }
}
