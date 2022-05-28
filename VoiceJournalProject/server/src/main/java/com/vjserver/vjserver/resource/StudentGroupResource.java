package com.vjserver.vjserver.resource;
import com.vjserver.vjserver.entity.StudentGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentGroupResource extends BaseResource {
    private Integer id;
    private String groupNumber;

    public StudentGroupResource(StudentGroup studentGroup) {
        this.id = studentGroup.getId();
        this.groupNumber = studentGroup.getGroupNumber();
    }

    public StudentGroup toEntity() {
        return new StudentGroup(
                this.id,
                this.groupNumber
        );
    }
}
