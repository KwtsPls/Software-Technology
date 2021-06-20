package gr.uoa.di.jete.controllers;


import gr.uoa.di.jete.exceptions.ProjectNotFoundException;
import gr.uoa.di.jete.exceptions.UserNotFoundException;
import gr.uoa.di.jete.models.*;
import gr.uoa.di.jete.repositories.DeveloperRepository;
import gr.uoa.di.jete.repositories.ProjectRepository;
import gr.uoa.di.jete.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProjectController {

    private final ProjectRepository repository;
    private final ProjectModelAssembler assembler;
    private final UserRepository userRep;
    private final DeveloperRepository devRep;

    ProjectController(ProjectRepository repository, ProjectModelAssembler assembler,
                      UserRepository userRep,DeveloperRepository devRep){
        this.repository = repository;
        this.assembler = assembler;
        this.userRep = userRep;
        this.devRep = devRep;
    }

    //Aggregate root
    //tag::get-aggregate-root[]
    @GetMapping("/projects")
    CollectionModel<EntityModel<Project>> all(){
        List<EntityModel<Project>> projects = repository.findAll().stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(projects,
                linkTo(methodOn(ProjectController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/projects/create/{user_id}")
    Project newProject(@RequestBody Project newProject,@PathVariable Long user_id){
        //Search for User with given id
        User user = userRep.findById(user_id) //
                .orElseThrow(()-> new UserNotFoundException(user_id));
        //Create an entry for a new developer on the db and save him as a product owner
        Project temp = repository.save(newProject);
        Developer dev = new Developer(user_id,newProject.getId(), 1L);
        this.devRep.save(dev);
        return temp;
    }

    //Single item
    @GetMapping("/projects/{id}")
    EntityModel<Project> one(@PathVariable Long id){
        Project project = repository.findById(id) //
                .orElseThrow(()-> new ProjectNotFoundException(id));

        return assembler.toModel(project);
    }

    @PutMapping("/projects/{id}")
    Project replaceProject(@RequestBody Project newProject, @PathVariable Long id){
        return repository.findById(id)
                .map(user -> {
                    newProject.setTitle(newProject.getTitle());
                    newProject.setDescription(newProject.getDescription());
                    return repository.save(newProject);
                })
                .orElseGet(()->{
                    newProject.setId(id);
                    return repository.save(newProject);
                });
    }

    @DeleteMapping("/projects/{id}")
    void deleteProject(@PathVariable Long id){
        repository.deleteById(id);
    }
}
