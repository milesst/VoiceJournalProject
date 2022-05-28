package com.vjserver.vjserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discipline extends BaseEntity {
    private String name;

    public Discipline(Integer id, String name) {
      super(id);
		  this.name = name;
	}
}
