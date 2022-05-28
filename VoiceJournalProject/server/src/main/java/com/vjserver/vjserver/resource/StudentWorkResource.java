package com.vjserver.vjserver.resource;

import com.vjserver.vjserver.entity.StudentWork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentWorkResource extends BaseResource {
    private Integer id;
    private Integer studentId; 
    private Integer classRecordId; 
    private Boolean attendance;
    private String note;
    private Integer grade;

    public StudentWorkResource(StudentWork studentWork) {
        this.id = studentWork.getId();
        this.studentId = studentWork.getStudentId();
        this.classRecordId = studentWork.getClassRecordId();
        this.attendance = studentWork.getAttendance();
        this.note = studentWork.getNote();
        this.grade = studentWork.getGrade();
    }

    public StudentWork toEntity() {
        return new StudentWork(
                this.id,
                this.studentId,
                this.classRecordId,
                this.attendance,
                this.note,
                this.grade
        );
    }
}
