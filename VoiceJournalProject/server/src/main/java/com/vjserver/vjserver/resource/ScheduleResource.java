package com.vjserver.vjserver.resource;

import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vjserver.vjserver.entity.Schedule;
import com.vjserver.vjserver.entity.Student;
import com.vjserver.vjserver.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResource extends BaseResource {
    private Integer id;
    private Integer groupId;
    private Integer disciplineId;
    private Integer teacherId;
    @JsonFormat(pattern = "THH:mm")
    private Time time;
    private String dayName; 
    private Short frequency;
    private Short classroom;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StudentGroupResource group;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DisciplineResource discipline;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserResource teacher;

    public ScheduleResource(Schedule schedule) {
        this.id = schedule.getId();
        this.groupId = schedule.getGroupId();
        this.disciplineId = schedule.getDisciplineId();
        this.teacherId = schedule.getTeacherId();
        this.time = schedule.getTime();
        this.dayName = schedule.getDayName();
        this.frequency = schedule.getFrequency();
        this.classroom = schedule.getClassroom();
        
    }

    public Schedule toEntity() {
        return new Schedule(
                this.id,
                this.groupId,
                this.disciplineId,
                this.teacherId,
                this.time,
                this.dayName,
                this.frequency,
                this.classroom
        );
    }
}