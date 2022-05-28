package com.vjserver.vjserver.dictionary;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class DictionaryResource {
    // String lastName;
    // String[] possibleResults;

    // DictionaryResource(String lastName, String[] possibleResults) {
    //     this.lastName = lastName;
    //     this.possibleResults = possibleResults;
    // }
    LastNameResource[] lastNames;

}
