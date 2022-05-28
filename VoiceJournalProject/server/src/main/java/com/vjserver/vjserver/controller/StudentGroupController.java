package com.vjserver.vjserver.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.vjserver.vjserver.entity.StudentGroup;
import com.vjserver.vjserver.repository.StudentGroupRepository;
import com.vjserver.vjserver.resource.StudentGroupResource;

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
@RequestMapping("/student_group")
public class StudentGroupController {
    private final StudentGroupRepository studentGroupRepository;

    public StudentGroupController(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    StudentGroupResource[] getAll(@RequestParam(required = false) String groupNumber,
                           @RequestParam(required = false) Object expand) {
        System.out.println(groupNumber);
        StudentGroup[] entities = groupNumber == null ?
            studentGroupRepository.select() :
            studentGroupRepository.selectByGroupNumber(groupNumber);
        return Arrays.stream(entities)
                .map(entity -> {
                    StudentGroupResource resource = new StudentGroupResource(entity);
//                    if (expand != null)
//                        resource.setDeadline(new OrderResource(
//                                orderRepository.select(entity.getDeadline()))
//                        );
                    return resource;
                })
                .toArray(StudentGroupResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    StudentGroupResource get(@PathVariable Integer id,
                      @RequestParam(required = false) Object expand) {
        StudentGroup entity = studentGroupRepository.select(id);
        if (entity == null) return null;
        StudentGroupResource resource = new StudentGroupResource(entity);
//        if (expand != null)
//            resource.setSource(
//                    new OrderResource(rssSourceRepository.select(entity.getSourceId()))
//            );
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    StudentGroupResource post(@RequestBody StudentGroupResource resource) {
        StudentGroup entity = studentGroupRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new StudentGroupResource(entity);
        return resource;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    StudentGroupResource put(@PathVariable Integer id,
                      @RequestBody StudentGroupResource resource) {
        StudentGroup entity = studentGroupRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new StudentGroupResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    StudentGroupResource delete(@PathVariable Integer id) {
        StudentGroup entity = studentGroupRepository.delete(id);
        if (entity == null) return null;
        StudentGroupResource resource = new StudentGroupResource(entity);
        return resource;
    }
}
