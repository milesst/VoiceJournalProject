package com.vjserver.vjserver.entity;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends BaseEntity {
    private Integer groupId;
    private Integer disciplineId;
    private Integer teacherId;
    private Time time;
    private String dayName; 
    private Short frequency;
    private Short classroom;


    public Schedule(Integer id, Integer groupId, Integer disciplineId, Integer teacherId, Time time, String dayName, Short frequency, Short classroom) {
      super(id);
	  this.groupId = groupId;
      this.disciplineId = disciplineId;
      this.teacherId = teacherId;
      this.time = time;
      this.dayName = dayName;
      this.frequency = frequency;
      this.classroom = classroom;
	}
}
