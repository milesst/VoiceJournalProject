package com.vjserver.vjserver.resource;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vjserver.vjserver.entity.ClassRecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassRecordResource extends BaseResource {
    private Integer id;
    private Integer scheduleId;
    @JsonFormat(pattern="dd.MM.yyyy, HH:mm:ss")
    private Timestamp startDate;
    @JsonFormat(pattern="dd.MM.yyyy, HH:mm:ss")
    private Timestamp endDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ScheduleResource schedule;

    public ClassRecordResource(ClassRecord classRecord) {
        this.id = classRecord.getId();
        this.scheduleId = classRecord.getScheduleId();
        this.startDate = classRecord.getStartDate();
        this.endDate = classRecord.getEndDate();
    }

    public ClassRecord toEntity() {
        return new ClassRecord(
                this.id,
                this.scheduleId,
                this.startDate,
                this.endDate
        );
    }
}
