package com.vjserver.vjserver.controller;

import java.util.Arrays;

import com.vjserver.vjserver.entity.Student;
import com.vjserver.vjserver.entity.User;
import com.vjserver.vjserver.repository.StudentGroupRepository;
import com.vjserver.vjserver.repository.StudentRepository;
import com.vjserver.vjserver.repository.UserRepository;
import com.vjserver.vjserver.resource.StudentGroupResource;
import com.vjserver.vjserver.resource.StudentResource;
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
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;
    private final StudentGroupRepository studentGroupRepository;

    public StudentController(StudentRepository studentRepository, StudentGroupRepository studentGroupRepository) {
        this.studentRepository = studentRepository;
        this.studentGroupRepository = studentGroupRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    StudentResource[] getAll(
                        @RequestParam(required = false) Integer groupId,
                           @RequestParam(required = false) Object expand) {
        Student[] entities = groupId != null ? studentRepository.selectByGroupId(groupId) : 
                             studentRepository.select();
            // userRepository.selectByName(name);
        return Arrays.stream(entities)
                .map(entity -> {
                    StudentResource resource = new StudentResource(entity);
                   if (expand != null)
                       resource.setGroup(new StudentGroupResource(
                            studentGroupRepository.select(entity.getGroupId()))
                       );
                    return resource;
                })
                .toArray(StudentResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    StudentResource get(@PathVariable Integer id,
                      @RequestParam(required = false) Object expand) {
        Student entity = studentRepository.select(id);
        if (entity == null) return null;
        StudentResource resource = new StudentResource(entity);
        if (expand != null)
                       resource.setGroup(new StudentGroupResource(
                            studentGroupRepository.select(entity.getGroupId()))
                       );
//        if (expand != null)
//            resource.setSource(
//                    new OrderResource(rssSourceRepository.select(entity.getSourceId()))
//            );
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    StudentResource post(@RequestBody StudentResource resource) {
        Student entity = studentRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new StudentResource(entity);
        return resource;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    StudentResource put(@PathVariable Integer id,
                      @RequestBody StudentResource resource) {
        Student entity = studentRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new StudentResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    StudentResource delete(@PathVariable Integer id) {
        Student entity = studentRepository.delete(id);
        if (entity == null) return null;
        StudentResource resource = new StudentResource(entity);
        return resource;
    }
}
