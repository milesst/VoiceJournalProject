package com.vjserver.vjserver.dictionary;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class LastNameResource {
    String lastName;
    String[] possibleResults;
}
