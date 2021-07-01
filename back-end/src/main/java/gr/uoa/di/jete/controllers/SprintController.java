package gr.uoa.di.jete.controllers;


import gr.uoa.di.jete.exceptions.ProjectNotFoundException;
import gr.uoa.di.jete.exceptions.SprintNotFoundException;
import gr.uoa.di.jete.models.*;
import gr.uoa.di.jete.repositories.ProjectRepository;
import gr.uoa.di.jete.repositories.SprintRepository;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
public class SprintController {

    private final SprintRepository repository;
    private final SprintModelAssembler assembler;
    private final ProjectRepository projectRep;

    SprintController(SprintRepository repository, SprintModelAssembler assembler,ProjectRepository projectRep){
        this.repository = repository;
        this.assembler = assembler;
        this.projectRep = projectRep;
    }

    //Aggregate root
    //tag::get-aggregate-root[]
    @GetMapping("/sprints")
    CollectionModel<EntityModel<Sprint>> all(){
        List<EntityModel<Sprint>> sprint = repository.findAll().stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(sprint,
                linkTo(methodOn(SprintController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/sprints")
    Sprint newSprint(@RequestBody Sprint newSprint){
        //Search for project with given id
        projectRep.findById(newSprint.getProject_id())
                .orElseThrow(()-> new ProjectNotFoundException(newSprint.getProject_id()));

        Long newId = repository.findMaxId().orElse(0L);
        newSprint.setId(newId+1L);

        return repository.save(newSprint);
    }

    //Endpoint to get all sprints in project
    @GetMapping("projects/{project_id}/sprints")
    CollectionModel<EntityModel<Sprint>> getSprintsInProject(@PathVariable Long project_id){
        List<EntityModel<Sprint>> sprint = repository.findSprintsInProject(project_id).stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(sprint,
                linkTo(methodOn(SprintController.class).getSprintsInProject(project_id)).withSelfRel());
    }

    //Endpoint to get all active sprints in project
    @GetMapping("projects/{project_id}/sprints/active")
    CollectionModel<EntityModel<Sprint>> getActiveSprintsInProject(@PathVariable Long project_id){
        List<EntityModel<Sprint>> sprint = repository.findActiveSprintsInProject(project_id).stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(sprint,
                linkTo(methodOn(SprintController.class).getActiveSprintsInProject(project_id)).withSelfRel());
    }

    //Single item
    @GetMapping("projects/{project_id}/sprints/{id}")
    EntityModel<Sprint> one(@PathVariable Long id,@PathVariable Long project_id){
        Sprint sprint = repository.findById(new SprintId(id,project_id)) //
                .orElseThrow(()-> new SprintNotFoundException(new SprintId(id,project_id)));

        return assembler.toModel(sprint);
    }

    @DeleteMapping("projects/{project_id}/sprints/{id}")
    void deleteEpic( @PathVariable Long id,@PathVariable Long project_id){
        Sprint sprint = repository.findById(new SprintId(id,project_id)) //
                .orElseThrow(()-> new SprintNotFoundException(new SprintId(id,project_id)));
        if(sprint.getStatus()!=0L)
            throw new SprintNotFoundException(0L);

        repository.deleteAllAssigneesInSprint(id,project_id);
        repository.deleteAllTasksInSprint(id,project_id);
        repository.deleteAllStoriesInSprint(id,project_id);
        repository.deleteById(new SprintId(id,project_id));
    }
}
