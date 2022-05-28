package com.vjserver.vjserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentGroup extends BaseEntity {
    private String groupNumber;

    public StudentGroup(Integer id, String groupNumber) {
        super(id);
        this.groupNumber = groupNumber;
    }
}
