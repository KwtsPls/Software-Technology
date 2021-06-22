package gr.uoa.di.jete.controllers;

import gr.uoa.di.jete.exceptions.*;
import gr.uoa.di.jete.models.*;
import gr.uoa.di.jete.repositories.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
public class TaskController {

    private final TaskRepository repository;
    private final TaskModelAssembler assembler;
    private final StoryRepository storyRep;
    private final EpicRepository epicRep;
    private final SprintRepository sprintRep;
    private final ProjectRepository projectRep;

    TaskController(TaskRepository repository, TaskModelAssembler assembler, StoryRepository storyRep, EpicRepository epicRep, SprintRepository sprintRep, ProjectRepository projectRep){
        this.repository = repository;
        this.assembler = assembler;
        this.storyRep = storyRep;
        this.epicRep = epicRep;
        this.sprintRep = sprintRep;
        this.projectRep = projectRep;
    }

    //Aggregate root
    //tag::get-aggregate-root[]
    @GetMapping("/tasks")
    CollectionModel<EntityModel<Task>> all(){
        List<EntityModel<Task>> task = repository.findAll().stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(task,
                linkTo(methodOn(TaskController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{story_id}/tasks")
    Task newTask(@RequestBody Task newTask,@PathVariable Long project_id,@PathVariable Long sprint_id,@PathVariable Long epic_id,@PathVariable Long story_id){
        //Search for project with given id
        epicRep.findById(new EpicId(epic_id,project_id)).orElseThrow(()-> new EpicNotFoundException(new EpicId(epic_id,project_id)));
        sprintRep.findById(new SprintId(sprint_id,project_id)).orElseThrow(()-> new SprintNotFoundException(new SprintId(sprint_id,project_id)));
        storyRep.findById(new StoryId(story_id,epic_id,sprint_id,project_id)).orElseThrow(()-> new StoryNotFoundException(new StoryId(story_id,epic_id,sprint_id,project_id)));

        Long newId = repository.findMaxId().orElse(0L);
        newTask.setId(newId+1L);

        return repository.save(newTask);
    }

    //Single item
    @GetMapping("/projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{story_id}/tasks/{id}")
    EntityModel<Task> one(@PathVariable Long id,@PathVariable Long project_id,@PathVariable Long sprint_id,@PathVariable Long epic_id,@PathVariable Long story_id){
        Task task = repository.findById(new TaskId(id,epic_id,sprint_id,project_id,story_id)) //
                .orElseThrow(()-> new TaskNotFoundException(new TaskId(id,epic_id,sprint_id,project_id,story_id)));

        return assembler.toModel(task);
    }

    @DeleteMapping("/projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{story_id}/tasks/{id}")
    void deleteEpic(@PathVariable Long id,@PathVariable Long project_id,@PathVariable Long sprint_id,@PathVariable Long epic_id,@PathVariable Long story_id){
        repository.deleteById(new TaskId(id,epic_id,sprint_id,project_id,story_id));
    }
}
