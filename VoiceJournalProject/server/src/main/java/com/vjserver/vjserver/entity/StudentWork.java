package com.vjserver.vjserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentWork extends BaseEntity {
    private Integer studentId; 
    private Integer classRecordId; 
    private Boolean attendance;
    private String note;
    private Integer grade;

    public StudentWork(Integer id, Integer studentId, Integer classRecordId, Boolean attendance, String note, Integer grade) {
        super(id);
        this.studentId = studentId;
        this.classRecordId = classRecordId;
        this.attendance = attendance;
        this.note = note;
        this.grade = grade;
    }
}
