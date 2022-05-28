package com.vjserver.vjserver.resource;

import com.vjserver.vjserver.entity.Discipline;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineResource extends BaseResource {
    private Integer id;
    private String name;

    public DisciplineResource(Discipline discipline) {
        this.id = discipline.getId();
        this.name = discipline.getName();
    }

    public Discipline toEntity() {
        return new Discipline(
                this.id,
                this.name
        );
    }
}
