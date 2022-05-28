package com.vjserver.vjserver.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassRecord extends BaseEntity {
    private Integer scheduleId;
    private Timestamp startDate;
    private Timestamp endDate;

    public ClassRecord(Integer id, Integer scheduleId, Timestamp startDate, Timestamp endDate) {
      super(id);
	  this.scheduleId = scheduleId;
      this.startDate = startDate;
      this.endDate = endDate;
	}
}
