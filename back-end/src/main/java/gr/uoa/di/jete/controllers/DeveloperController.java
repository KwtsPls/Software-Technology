package gr.uoa.di.jete.controllers;


import gr.uoa.di.jete.auth.MessageResponse;
import gr.uoa.di.jete.exceptions.DeveloperNotFoundException;
import gr.uoa.di.jete.exceptions.ProjectNotFoundException;
import gr.uoa.di.jete.exceptions.UserNotFoundException;
import gr.uoa.di.jete.models.*;
import gr.uoa.di.jete.repositories.DeveloperRepository;
import gr.uoa.di.jete.repositories.ProjectRepository;
import gr.uoa.di.jete.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
public class DeveloperController {

    private final DeveloperRepository repository;
    private final DeveloperModelAssembler assembler;
    private final UserRepository userRep;
    private final ProjectRepository projectRep;

    DeveloperController(DeveloperRepository repository, DeveloperModelAssembler assembler,
                        UserRepository userRep,ProjectRepository projectRep){
        this.repository = repository;
        this.assembler = assembler;
        this.userRep = userRep;
        this.projectRep = projectRep;
    }

    //Aggregate root
    //tag::get-aggregate-root[]
    @GetMapping("/developers/")
    CollectionModel<EntityModel<Developer>> all(){
        List<EntityModel<Developer>> developer = repository.findAll().stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(developer,
                linkTo(methodOn(DeveloperController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/developers/")
    Developer newDeveloper(@RequestBody Developer newDeveloper){
        System.out.println(newDeveloper.getUser_id()+" "+newDeveloper.getProject_id());
        //Search for User with given id
        userRep.findById(newDeveloper.getUser_id()) //
                .orElseThrow(()-> new UserNotFoundException(newDeveloper.getUser_id()));

        //Search for project with given id
        projectRep.findById(newDeveloper.getProject_id())
                .orElseThrow(()-> new ProjectNotFoundException(newDeveloper.getProject_id()));

        return repository.save(newDeveloper);
    }

    //Single item
    @GetMapping("/developers/users/{user_id}/projects/{project_id}")
    EntityModel<Developer> one(@PathVariable Long user_id,@PathVariable Long project_id){
        Developer developer = repository.findById(new DeveloperId(user_id,project_id)) //
                .orElseThrow(()-> new DeveloperNotFoundException(new DeveloperId(user_id,project_id)));

        return assembler.toModel(developer);
    }

    @PutMapping("/developers/users/{user_id}/projects/{project_id}")
    Developer replaceDeveloper(@RequestBody Developer newDeveloper, @PathVariable Long user_id,@PathVariable Long project_id){
        return repository.findById(new DeveloperId(user_id,project_id))
                .map(user -> {
                    newDeveloper.setRole(newDeveloper.getRole());
                    return repository.save(newDeveloper);
                })
                .orElseGet(()->{
                    newDeveloper.setUser_id(user_id);
                    newDeveloper.setProject_id(project_id);
                    return repository.save(newDeveloper);
                });
    }

    @PutMapping("/developers/users/{user_id}/projects/{project_id}/accept")
    ResponseEntity<?> acceptProjectInvitation(@PathVariable Long user_id, @PathVariable Long project_id){
        int status = repository.setDeveloperAcceptedTrue(user_id,project_id);
        if(status==1)
            return ResponseEntity.ok(new MessageResponse("OK"));
        else
            return ResponseEntity.ok(new MessageResponse("ERROR"));
    }

    //Endpoint to get the vital info of the developers of a project
    @GetMapping("/developers/projects/{project_id}")
    List<DeveloperInfo> allDevelopersInfo(@PathVariable Long project_id){
        List<?> devInfos = repository.findDeveloperInfoInProject(project_id);
        return (List<DeveloperInfo>) devInfos;
    }

    @GetMapping("/developers/users/{user_id}/notifications")
    List<?> getNotificationsNumber(@PathVariable Long user_id){
        return repository.getProjectsNotificationNumber(user_id);
    }

    @DeleteMapping("/developers/users/{user_id}/projects/{project_id}")
    ResponseEntity<?> deleteDeveloper( @PathVariable Long user_id,@PathVariable Long project_id){
        repository.deleteAssigneeDeveloper(user_id,project_id);
        repository.deleteById(new DeveloperId(user_id,project_id));
        return ResponseEntity.ok(new MessageResponse("OK"));
    }
}
