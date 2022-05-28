package com.vjserver.vjserver.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.vjserver.vjserver.entity.ClassRecord;
import com.vjserver.vjserver.entity.Discipline;
import com.vjserver.vjserver.repository.ClassRecordRepository;
import com.vjserver.vjserver.repository.DisciplineRepository;
import com.vjserver.vjserver.repository.ScheduleRepository;
import com.vjserver.vjserver.repository.StudentGroupRepository;
import com.vjserver.vjserver.repository.UserRepository;
import com.vjserver.vjserver.resource.ClassRecordResource;
import com.vjserver.vjserver.resource.DisciplineResource;
import com.vjserver.vjserver.resource.ScheduleResource;
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
@RequestMapping("/class_record")
public class ClassRecordController {
    private final ClassRecordRepository classRecordRepository;
    private final ScheduleRepository scheduleRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final UserRepository userRepository;
    private final DisciplineRepository disciplineRepository;

    public ClassRecordController(ClassRecordRepository classRecordRepository,
                                ScheduleRepository scheduleRepository,
                                StudentGroupRepository studentGroupRepository, UserRepository userRepository, DisciplineRepository disciplineRepository) {
        this.classRecordRepository = classRecordRepository;
        this.scheduleRepository = scheduleRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.userRepository = userRepository;
        this.disciplineRepository = disciplineRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    ClassRecordResource[] getAll(@RequestParam(required = false) String name,
                                @RequestParam(required = false) Integer scheduleId,
                           @RequestParam(required = false) Object expand) {
        ClassRecord[] entities = scheduleId != null ? classRecordRepository.selectByScheduleId(scheduleId) : 
                                classRecordRepository.select();
        return Arrays.stream(entities)
                .map(entity -> {
                    ClassRecordResource resource = new ClassRecordResource(entity);
                   if (expand != null) {
                       ScheduleResource schRes = new ScheduleResource(scheduleRepository.select(entity.getScheduleId()));
                       schRes.setGroup(new StudentGroupResource(studentGroupRepository.select(schRes.getGroupId())));
                       schRes.setDiscipline(new DisciplineResource(disciplineRepository.select(schRes.getDisciplineId())));
                       resource.setSchedule(schRes);
                   }
                    return resource;
                })
                .toArray(ClassRecordResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ClassRecordResource get(@PathVariable Integer id,
                      @RequestParam(required = false) Integer scheduleId,
                      @RequestParam(required = false) Object expand) {
        ClassRecord entity = classRecordRepository.select(id);
        if (entity == null) return null;
        ClassRecordResource resource = new ClassRecordResource(entity);
        if (expand != null) {
            ScheduleResource schRes = new ScheduleResource(scheduleRepository.select(entity.getScheduleId()));
            schRes.setGroup(new StudentGroupResource(studentGroupRepository.select(schRes.getGroupId())));
            schRes.setDiscipline(new DisciplineResource(disciplineRepository.select(schRes.getDisciplineId())));
            resource.setSchedule(schRes);
        }
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    ClassRecordResource post(@RequestBody ClassRecordResource resource) {
        ClassRecord entity = classRecordRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new ClassRecordResource(entity);
        System.out.println(resource.getStartDate());
        System.out.println(resource.getEndDate());
        return resource;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ClassRecordResource put(@PathVariable Integer id,
                      @RequestBody ClassRecordResource resource) {
        ClassRecord entity = classRecordRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new ClassRecordResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ClassRecordResource delete(@PathVariable Integer id) {
        ClassRecord entity = classRecordRepository.delete(id);
        if (entity == null) return null;
        ClassRecordResource resource = new ClassRecordResource(entity);
        return resource;
    }
}
