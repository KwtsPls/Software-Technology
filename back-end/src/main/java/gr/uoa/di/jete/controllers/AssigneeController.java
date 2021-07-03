package gr.uoa.di.jete.controllers;

import gr.uoa.di.jete.exceptions.AssigneeNotFoundException;
import gr.uoa.di.jete.models.Assignee;
import gr.uoa.di.jete.models.AssigneeId;
import gr.uoa.di.jete.repositories.AssigneeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

    public AssigneeController(AssigneeRepository repository,AssigneeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/assignees/")
    CollectionModel<EntityModel<Assignee>> all(){
        List<EntityModel<Assignee>> assignees = repository.findAll().stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(assignees,
                linkTo(methodOn(AssigneeController.class).all()).withSelfRel());
    }

    //Single item
    @GetMapping("/assignees/users/{user_id}/projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{story_id}/tasks/{task_id}")
    EntityModel<Assignee> one(@PathVariable Long user_id, @PathVariable Long project_id, @PathVariable Long sprint_id,
                               @PathVariable Long epic_id,@PathVariable Long story_id,@PathVariable Long task_id){
        Assignee assignee = repository.findById(new AssigneeId(user_id,epic_id,sprint_id,project_id,story_id,task_id)) //
                .orElseThrow(()-> new AssigneeNotFoundException(new AssigneeId(user_id,epic_id,sprint_id,project_id,story_id,task_id)));

        return assembler.toModel(assignee);
    }

    @PostMapping("/assignees/")
    Assignee createAssignee(@RequestBody Assignee newAssignee){
        return repository.save(newAssignee);
    }

}
