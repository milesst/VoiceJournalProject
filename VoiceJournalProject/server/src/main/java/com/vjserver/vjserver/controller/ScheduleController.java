package com.vjserver.vjserver.controller;

import java.util.Arrays;
import java.util.List;

import com.vjserver.vjserver.entity.Schedule;
import com.vjserver.vjserver.entity.User;
import com.vjserver.vjserver.repository.DisciplineRepository;
import com.vjserver.vjserver.repository.ScheduleRepository;
import com.vjserver.vjserver.repository.StudentGroupRepository;
import com.vjserver.vjserver.repository.UserRepository;
import com.vjserver.vjserver.resource.DisciplineResource;
import com.vjserver.vjserver.resource.ScheduleResource;
import com.vjserver.vjserver.resource.StudentGroupResource;
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
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleRepository scheduleRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final UserRepository userRepository;
    private final DisciplineRepository disciplineRepository;

    public ScheduleController(ScheduleRepository scheduleRepository, StudentGroupRepository studentGroupRepository, UserRepository userRepository, DisciplineRepository disciplineRepository) {
        this.scheduleRepository = scheduleRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.userRepository = userRepository;
        this.disciplineRepository = disciplineRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    ScheduleResource[] getAll(
        @RequestParam(required = false) Integer teacherId,
        @RequestParam(required = false) Integer groupId,
        @RequestParam(required = false) Integer disciplineId,
                           @RequestParam(required = false) Object expand) {
        Schedule[] entities = (teacherId != null && disciplineId != null) ? scheduleRepository.selectByDisciplineTeacherId(disciplineId, teacherId) :
                              teacherId != null ? scheduleRepository.selectByTeacherId(teacherId) : 
                              groupId != null ? scheduleRepository.selectByGroupId(groupId) :
                              disciplineId != null ? scheduleRepository.selectByDisciplineId(disciplineId) :
                              scheduleRepository.select();
        return Arrays.stream(entities)
                .map(entity -> {
                    ScheduleResource resource = new ScheduleResource(entity);
                   if (expand != null) {
                       resource.setGroup(new StudentGroupResource(
                               studentGroupRepository.select(entity.getGroupId()))
                       );
                       resource.setDiscipline(new DisciplineResource(disciplineRepository.select(entity.getDisciplineId())));
                       resource.setTeacher(new UserResource(userRepository.select(entity.getTeacherId())));
                       resource.setGroup(new StudentGroupResource(studentGroupRepository.select(entity.getGroupId())));
                   }
                    return resource;
                })
                .toArray(ScheduleResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ScheduleResource get(@PathVariable Integer id,
                      @RequestParam(required = false) Object expand) {
        Schedule entity = scheduleRepository.select(id);
        if (entity == null) return null;
        ScheduleResource resource = new ScheduleResource(entity);
        if (expand != null) {
            resource.setGroup(new StudentGroupResource(
                    studentGroupRepository.select(entity.getGroupId()))
            );
            resource.setDiscipline(new DisciplineResource(disciplineRepository.select(entity.getDisciplineId())));
            resource.setTeacher(new UserResource(userRepository.select(entity.getTeacherId())));
            resource.setGroup(new StudentGroupResource(studentGroupRepository.select(entity.getGroupId())));
        }
//            );
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    ScheduleResource post(@RequestBody ScheduleResource resource) {
        Schedule entity = scheduleRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new ScheduleResource(entity);
        return resource;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ScheduleResource put(@PathVariable Integer id,
                      @RequestBody ScheduleResource resource) {
        Schedule entity = scheduleRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new ScheduleResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ScheduleResource delete(@PathVariable Integer id) {
        Schedule entity = scheduleRepository.delete(id);
        if (entity == null) return null;
        ScheduleResource resource = new ScheduleResource(entity);
        return resource;
    }
}
