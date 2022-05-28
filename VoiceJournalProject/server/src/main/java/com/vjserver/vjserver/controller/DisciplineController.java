package com.vjserver.vjserver.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.vjserver.vjserver.entity.Discipline;
import com.vjserver.vjserver.repository.DisciplineRepository;
import com.vjserver.vjserver.resource.DisciplineResource;

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
@RequestMapping("/discipline")
public class DisciplineController {
    private final DisciplineRepository disciplineRepository;

    public DisciplineController(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    DisciplineResource[] getAll(@RequestParam(required = false) String name,
                           @RequestParam(required = false) Object expand) {
        Discipline[] entities = name == null ?
			disciplineRepository.select() :
			disciplineRepository.selectByName(name);
        return Arrays.stream(entities)
                .map(entity -> {
                    DisciplineResource resource = new DisciplineResource(entity);
//                    if (expand != null)
//                        resource.setDeadline(new OrderResource(
//                                orderRepository.select(entity.getDeadline()))
//                        );
                    return resource;
                })
                .toArray(DisciplineResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    DisciplineResource get(@PathVariable Integer id,
                      @RequestParam(required = false) Object expand) {
        Discipline entity = disciplineRepository.select(id);
        if (entity == null) return null;
        DisciplineResource resource = new DisciplineResource(entity);
//        if (expand != null)
//            resource.setSource(
//                    new OrderResource(rssSourceRepository.select(entity.getSourceId()))
//            );
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    DisciplineResource post(@RequestBody DisciplineResource resource) {
        Discipline entity = disciplineRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new DisciplineResource(entity);
        return resource;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    DisciplineResource put(@PathVariable Integer id,
                      @RequestBody DisciplineResource resource) {
        Discipline entity = disciplineRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new DisciplineResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    DisciplineResource delete(@PathVariable Integer id) {
        Discipline entity = disciplineRepository.delete(id);
        if (entity == null) return null;
        DisciplineResource resource = new DisciplineResource(entity);
        return resource;
    }
}
