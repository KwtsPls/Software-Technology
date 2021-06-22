package gr.uoa.di.jete.controllers;


import gr.uoa.di.jete.exceptions.EpicNotFoundException;
import gr.uoa.di.jete.exceptions.ProjectNotFoundException;
import gr.uoa.di.jete.models.Epic;
import gr.uoa.di.jete.models.EpicId;
import gr.uoa.di.jete.repositories.EpicRepository;
import gr.uoa.di.jete.repositories.ProjectRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EpicController {

    private final EpicRepository repository;
    private final EpicModelAssembler assembler;
    private final ProjectRepository projectRep;

    EpicController(EpicRepository repository, EpicModelAssembler assembler,ProjectRepository projectRep){
        this.repository = repository;
        this.assembler = assembler;
        this.projectRep = projectRep;
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

    @PostMapping("/epics")
    Epic newEpic(@RequestBody Epic newEpic){
        //Search for project with given id
        projectRep.findById(newEpic.getProject_id())
                .orElseThrow(()-> new ProjectNotFoundException(newEpic.getProject_id()));

        Long newId = repository.findMaxId().orElse(0L);
        newEpic.setId(newId+1L);

        return repository.save(newEpic);
    }

    //Single item
    @GetMapping("/epics/{id}/{project_id}")
    EntityModel<Epic> one(@PathVariable Long id,@PathVariable Long project_id){
        Epic epic = repository.findById(new EpicId(id,project_id)) //
                .orElseThrow(()-> new EpicNotFoundException(new EpicId(id,project_id)));

        return assembler.toModel(epic);
    }

    @DeleteMapping("/epics/{id}/{project_id}")
    void deleteEpic( @PathVariable Long id,@PathVariable Long project_id){
        repository.deleteById(new EpicId(id,project_id));
    }
}
