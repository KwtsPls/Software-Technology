package gr.uoa.di.jete.controllers;


import gr.uoa.di.jete.auth.MessageResponse;
import gr.uoa.di.jete.exceptions.DeveloperNotFoundException;
import gr.uoa.di.jete.exceptions.EpicNotFoundException;
import gr.uoa.di.jete.exceptions.ProjectNotFoundException;
import gr.uoa.di.jete.models.Developer;
import gr.uoa.di.jete.models.DeveloperId;
import gr.uoa.di.jete.models.Epic;
import gr.uoa.di.jete.models.EpicId;
import gr.uoa.di.jete.repositories.DeveloperRepository;
import gr.uoa.di.jete.repositories.EpicRepository;
import gr.uoa.di.jete.repositories.ProjectRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
public class EpicController {

    private final EpicRepository repository;
    private final EpicModelAssembler assembler;
    private final ProjectRepository projectRep;
    private final DeveloperRepository devRep;

    EpicController(EpicRepository repository, EpicModelAssembler assembler, ProjectRepository projectRep, DeveloperRepository devRep){
        this.repository = repository;
        this.assembler = assembler;
        this.projectRep = projectRep;
        this.devRep = devRep;
    }

    public EpicController(EpicRepository repository, ProjectRepository projectRep, DeveloperRepository devRep) {
        this.repository = repository;
        this.assembler = new EpicModelAssembler();
        this.projectRep = projectRep;
        this.devRep = devRep;
    }

    //Aggregate root
    //tag::get-aggregate-root[]
    @GetMapping("/epics")
    CollectionModel<EntityModel<Epic>> all(){
        List<EntityModel<Epic>> epic = repository.findAll().stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(epic,
                linkTo(methodOn(EpicController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/projects/epics/create")
    Epic newEpic(@RequestBody Epic newEpic){
        //Search for project with given id
        projectRep.findById(newEpic.getProject_id())
                .orElseThrow(()-> new ProjectNotFoundException(newEpic.getProject_id()));

        Long newId = repository.findMaxId().orElse(0L);
        newEpic.setId(newId+1L);

        return repository.save(newEpic);
    }

    //Single item
    @GetMapping("/projects/{project_id}/epics/{id}")
    EntityModel<Epic> one(@PathVariable Long id,@PathVariable Long project_id){
        Epic epic = repository.findById(new EpicId(id,project_id)) //
                .orElseThrow(()-> new EpicNotFoundException(new EpicId(id,project_id)));

        return assembler.toModel(epic);
    }

    //Endpoint to get all epics in a project
    @GetMapping("/projects/{project_id}/epics")
    CollectionModel<EntityModel<Epic>> getEpicsInProject(@PathVariable Long project_id){
        List<EntityModel<Epic>> epic = repository.findAllByProjectId(project_id).stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(epic,
                linkTo(methodOn(EpicController.class).getEpicsInProject(project_id)).withSelfRel());
    }

    //Endpoint to archive a given epic
    @PutMapping("projects/{project_id}/epics/{id}/archive/{user_id}")
    ResponseEntity<?> archiveEpic(@PathVariable Long id, @PathVariable Long project_id, @PathVariable Long user_id){
        //Check that the developer requesting to archive this epic is the product owner of the project
        Developer developer =  devRep.findById(new DeveloperId(user_id,project_id)).orElseThrow(()->new DeveloperNotFoundException(new DeveloperId(user_id,project_id)));
        if(developer.getRole()!=1L)
            throw new DeveloperNotFoundException(user_id,project_id);

        repository.archiveEpic(id,project_id);
        repository.archiveAllStoriesInEpic(id,project_id);
        repository.archiveAllTasksInEpic(id,project_id);
        return ResponseEntity.ok(new MessageResponse("OK"));
    }

    @DeleteMapping("projects/{project_id}/epics/{id}/delete/{user_id}")
    ResponseEntity<?> deleteEpic( @PathVariable Long id,@PathVariable Long project_id,@PathVariable Long user_id){
        //Check that the developer requesting to delete this epic is the product owner of the project
        Developer developer =  devRep.findById(new DeveloperId(user_id,project_id)).orElseThrow(()->new DeveloperNotFoundException(new DeveloperId(user_id,project_id)));
        if(developer.getRole()!=1L)
            throw new DeveloperNotFoundException(user_id,project_id);

        repository.deleteAllAssigneesInEpic(id,project_id);
        repository.deleteAllTasksInEpic(id,project_id);
        repository.deleteAllStoriesInEpic(id,project_id);
        repository.deleteById(new EpicId(id,project_id));
        return ResponseEntity.ok(new MessageResponse("OK"));
    }
}
