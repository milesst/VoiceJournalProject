package com.vjserver.vjserver.controller;

import java.util.Arrays;
import java.util.List;

import com.vjserver.vjserver.entity.User;
import com.vjserver.vjserver.repository.UserRepository;
import com.vjserver.vjserver.resource.UserResource;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    UserResource[] getAll(
                    @RequestParam(required = false) Object expand) {
        User[] entities = userRepository.select();
        return Arrays.stream(entities)
                .map(entity -> {
                    UserResource resource = new UserResource(entity);
                    return resource;
                })
                .toArray(UserResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    UserResource get(@PathVariable Integer id,
                      @RequestParam(required = false) Object expand) {
        User entity = userRepository.select(id);
        if (entity == null) return null;
        UserResource resource = new UserResource(entity);
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    UserResource post(@RequestBody UserResource resource) {
        User entity = userRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new UserResource(entity);
        return resource;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    UserResource put(@PathVariable Integer id,
                      @RequestBody UserResource resource) {
        User entity = userRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new UserResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    UserResource delete(@PathVariable Integer id) {
        User entity = userRepository.delete(id);
        if (entity == null) return null;
        UserResource resource = new UserResource(entity);
        return resource;
    }
}
