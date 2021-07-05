package gr.uoa.di.jete.controllers;

import gr.uoa.di.jete.assemblers.AssigneeModelAssembler;
import gr.uoa.di.jete.assemblers.UserModelAssembler;
import gr.uoa.di.jete.auth.MessageResponse;
import gr.uoa.di.jete.exceptions.AssigneeNotFoundException;
import gr.uoa.di.jete.models.Assignee;
import gr.uoa.di.jete.models.AssigneeId;
import gr.uoa.di.jete.models.User;
import gr.uoa.di.jete.repositories.AssigneeRepository;
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
public class AssigneeController {
    private final AssigneeRepository repository;
    private final AssigneeModelAssembler assembler;
    private final UserModelAssembler userModelAssembler;

    public AssigneeController(AssigneeRepository repository,AssigneeModelAssembler assembler,UserModelAssembler userModelAssembler) {
        this.repository = repository;
        this.assembler = assembler;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping("/assignees/")
    public CollectionModel<EntityModel<Assignee>> all(){
        List<EntityModel<Assignee>> assignees = repository.findAll().stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(assignees,
                linkTo(methodOn(AssigneeController.class).all()).withSelfRel());
    }

    //Single item
    @GetMapping("/assignees/users/{user_id}/projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{story_id}/tasks/{task_id}")
    public EntityModel<Assignee> one(@PathVariable Long user_id, @PathVariable Long project_id, @PathVariable Long sprint_id,
                                     @PathVariable Long epic_id, @PathVariable Long story_id, @PathVariable Long task_id){
        Assignee assignee = repository.findById(new AssigneeId(user_id,epic_id,sprint_id,project_id,story_id,task_id)) //
                .orElseThrow(()-> new AssigneeNotFoundException(new AssigneeId(user_id,epic_id,sprint_id,project_id,story_id,task_id)));

        return assembler.toModel(assignee);
    }

    //Endpoint to get all users assigned to a task
    @GetMapping("/assignees/users/projects/{project_id}/tasks/{task_id}")
    public CollectionModel<EntityModel<User>> getAllUsersInTask(@PathVariable Long project_id,@PathVariable Long task_id){
        List<EntityModel<User>> users = repository.findUsersInTask(project_id,task_id).stream() //
                .map(userModelAssembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(users,
                linkTo(methodOn(AssigneeController.class).getAllUsersInTask(project_id,task_id)).withSelfRel());
    }

    @PostMapping("/assignees/")
    Assignee createAssignee(@RequestBody Assignee newAssignee){
        return repository.save(newAssignee);
    }

    @DeleteMapping("/assignees/users/{user_id}/projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{story_id}/tasks/{task_id}")
    ResponseEntity<?> deleteAssignee(@PathVariable Long user_id, @PathVariable Long project_id, @PathVariable Long sprint_id,
                                     @PathVariable Long epic_id, @PathVariable Long story_id, @PathVariable Long task_id) {
        repository.deleteById(new AssigneeId(user_id,epic_id,sprint_id,project_id,story_id,task_id));
        return ResponseEntity.ok(new MessageResponse("OK"));
    }

}
