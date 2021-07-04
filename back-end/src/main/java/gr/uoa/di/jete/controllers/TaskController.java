package gr.uoa.di.jete.controllers;

import gr.uoa.di.jete.assemblers.TaskModelAssembler;
import gr.uoa.di.jete.auth.MessageResponse;
import gr.uoa.di.jete.exceptions.*;
import gr.uoa.di.jete.models.*;
import gr.uoa.di.jete.repositories.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
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

    public TaskController(TaskRepository repository, TaskModelAssembler assembler, StoryRepository storyRep, EpicRepository epicRep, SprintRepository sprintRep){
        this.repository = repository;
        this.assembler = assembler;
        this.storyRep = storyRep;
        this.epicRep = epicRep;
        this.sprintRep = sprintRep;
    }

    //Aggregate root
    //tag::get-aggregate-root[]
    @GetMapping("/tasks")
    public CollectionModel<EntityModel<Task>> all(){
        List<EntityModel<Task>> task = repository.findAll().stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(task,
                linkTo(methodOn(TaskController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    //Endpoint to get all tasks in project
    @GetMapping("/projects/{project_id}/stories/{story_id}/tasks")
    CollectionModel<EntityModel<Task>> getAllTasksInStory(@PathVariable Long project_id,@PathVariable Long story_id){
        List<EntityModel<Task>> task = repository.findAllByProjectAndStoryId(project_id,story_id).stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(task,
                linkTo(methodOn(TaskController.class).getAllTasksInStory(project_id,story_id)).withSelfRel());
    }

    //Endpoint to get all tasks in an sprint
    @GetMapping("/projects/{project_id}/sprints/{sprint_id}/stories/{story_id}/tasks")
    CollectionModel<EntityModel<Task>> getAllTasksInSprint(@PathVariable Long project_id,@PathVariable Long sprint_id,@PathVariable Long story_id){
        List<EntityModel<Task>> task = repository.findAllByProjectAndSprintAndStoryId(project_id,sprint_id,story_id).stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(task,
                linkTo(methodOn(TaskController.class).getAllTasksInSprint(project_id,sprint_id,story_id)).withSelfRel());
    }

    //Endpoint to get all tasks in epics
    @GetMapping("/projects/{project_id}/epics/{epic_id}/stories/{story_id}/tasks")
    CollectionModel<EntityModel<Task>> getAllTasksInEpic(@PathVariable Long project_id,@PathVariable Long epic_id,@PathVariable Long story_id){
        List<EntityModel<Task>> task = repository.findAllByProjectAndEpicAndStoryId(project_id,epic_id,story_id).stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(task,
                linkTo(methodOn(TaskController.class).getAllTasksInEpic(project_id,epic_id,story_id)).withSelfRel());
    }

    //Endpoint to create a new task
    @PostMapping("/projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{story_id}/tasks/create")
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
    public EntityModel<Task> one(@PathVariable Long id, @PathVariable Long project_id, @PathVariable Long sprint_id, @PathVariable Long epic_id, @PathVariable Long story_id){
        Task task = repository.findById(new TaskId(id,epic_id,sprint_id,project_id,story_id)) //
                .orElseThrow(()-> new TaskNotFoundException(new TaskId(id,epic_id,sprint_id,project_id,story_id)));

        return assembler.toModel(task);
    }

    @PutMapping("/projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{story_id}/tasks/{id}/archive")
    ResponseEntity<?> archiveTask(@PathVariable Long id, @PathVariable Long project_id, @PathVariable Long sprint_id, @PathVariable Long epic_id, @PathVariable Long story_id){
        repository.archiveTask(id,story_id,project_id,sprint_id,epic_id);
        return ResponseEntity.ok(new MessageResponse("OK"));
    }

    @DeleteMapping("/projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{story_id}/tasks/{id}/delete")
    ResponseEntity<?> deleteTask(@PathVariable Long id,@PathVariable Long project_id,@PathVariable Long sprint_id,@PathVariable Long epic_id,@PathVariable Long story_id){
        repository.deleteAllAssigneesInTask(id,story_id,project_id,sprint_id,epic_id);
        repository.deleteById(new TaskId(id,epic_id,sprint_id,project_id,story_id));
        return ResponseEntity.ok(new MessageResponse("OK"));
    }
}
