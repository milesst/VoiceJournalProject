package com.vjserver.vjserver.controller;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.vjserver.vjserver.entity.StudentGroup;
import com.vjserver.vjserver.entity.StudentWork;
import com.vjserver.vjserver.repository.StudentGroupRepository;
import com.vjserver.vjserver.repository.StudentWorkRepository;
import com.vjserver.vjserver.resource.StudentGroupResource;
import com.vjserver.vjserver.resource.StudentWorkResource;

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
@RequestMapping("/student_work")
public class StudentWorkController {
    private final StudentWorkRepository studentWorkRepository;

    public StudentWorkController(StudentWorkRepository studentWorkRepository) {
        this.studentWorkRepository = studentWorkRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    StudentWorkResource[] getAll(@RequestParam(required = false) String groupNumber,
                                @RequestParam(required = false) Integer classRecordId,
                           @RequestParam(required = false) Object expand) {
        System.out.println(groupNumber);
        StudentWork[] entities = classRecordId != null ? studentWorkRepository.selectByClassRecordId(classRecordId) :
                                                            studentWorkRepository.select();
            // studentGroupRepository.selectByGroupNumber(groupNumber);
        return Arrays.stream(entities)
                .map(entity -> {
                    StudentWorkResource resource = new StudentWorkResource(entity);
//                    if (expand != null)
//                        resource.setDeadline(new OrderResource(
//                                orderRepository.select(entity.getDeadline()))
//                        );
                    return resource;
                })
                .toArray(StudentWorkResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    StudentWorkResource get(@PathVariable Integer id,
                      @RequestParam(required = false) Object expand) {
        StudentWork entity = studentWorkRepository.select(id);
        if (entity == null) return null;
        StudentWorkResource resource = new StudentWorkResource(entity);
//        if (expand != null)
//            resource.setSource(
//                    new OrderResource(rssSourceRepository.select(entity.getSourceId()))
//            );
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    StudentWorkResource post(@RequestBody StudentWorkResource resource) {
        StudentWork entity = studentWorkRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new StudentWorkResource(entity);
        return resource;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    StudentWorkResource put(@PathVariable Integer id,
                      @RequestBody StudentWorkResource resource) {
        StudentWork entity = studentWorkRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new StudentWorkResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    StudentWorkResource delete(@PathVariable Integer id) {
        StudentWork entity = studentWorkRepository.delete(id);
        if (entity == null) return null;
        StudentWorkResource resource = new StudentWorkResource(entity);
        return resource;
    }
}