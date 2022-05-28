package com.vjserver.vjserver.dictionary;


import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    // @Value("dictionarySource.json")
    // Resource resourceFile;  

    // public DictionaryController(DictionaryController dictionaryController) {
    // }

    @RequestMapping(value = "", method = RequestMethod.GET)
    DictionaryResource get(
                    @RequestParam(required = false) Object expand) throws StreamReadException, DatabindException, IOException {
        // return resourceFile;
        ObjectMapper objectMapper = new ObjectMapper();

        //read json file and convert to customer object
        DictionaryResource customer = objectMapper.readValue(new File("src/main/java/com/vjserver/vjserver/dictionary/dictionarySource.json"), DictionaryResource.class);

        //print customer details
        System.out.println(customer);
        return customer;
    }

    // @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    // UserResource get(@PathVariable Integer id,
    //                   @RequestParam(required = false) Object expand) {
    //     User entity = userRepository.select(id);
    //     if (entity == null) return null;
    //     UserResource resource = new UserResource(entity);
    //     return resource;
    // }

    // @RequestMapping(value = "", method = RequestMethod.POST)
    // UserResource post(@RequestBody UserResource resource) {
    //     User entity = userRepository.insert(resource.toEntity());
    //     if (entity == null) return null;
    //     resource = new UserResource(entity);
    //     return resource;
    // }

    // @CrossOrigin
    // @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    // UserResource put(@PathVariable Integer id,
    //                   @RequestBody UserResource resource) {
    //     User entity = userRepository.update(id, resource.toEntity());
    //     if (entity == null) return null;
    //     resource = new UserResource(entity);
    //     return resource;
    // }

    // @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    // UserResource delete(@PathVariable Integer id) {
    //     User entity = userRepository.delete(id);
    //     if (entity == null) return null;
    //     UserResource resource = new UserResource(entity);
    //     return resource;
    // }
}
