package gr.uoa.di.jete.controllers;


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
public class StoryController {

    private final StoryRepository repository;
    private final StoryModelAssembler assembler;
    private final EpicRepository epicRep;
    private final SprintRepository sprintRep;
    private final ProjectRepository projectRep;
    private final DeveloperRepository devRep;

    StoryController(StoryRepository repository, StoryModelAssembler assembler, EpicRepository epicRep, SprintRepository sprintRep, ProjectRepository projectRep, DeveloperRepository devRep){
        this.repository = repository;
        this.assembler = assembler;
        this.epicRep = epicRep;
        this.sprintRep = sprintRep;
        this.projectRep = projectRep;
        this.devRep = devRep;
    }

    //Aggregate root
    //tag::get-aggregate-root[]
    @GetMapping("/stories")
    CollectionModel<EntityModel<Story>> all(){
        List<EntityModel<Story>> story = repository.findAll().stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(story,
                linkTo(methodOn(StoryController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    //Endpoint to get all stories in a sprint
    @GetMapping("/projects/{project_id}/sprints/{sprint_id}/stories")
    CollectionModel<EntityModel<Story>> getAllStoriesInSprint(@PathVariable Long project_id,@PathVariable Long sprint_id){
        List<EntityModel<Story>> story = repository.findAllByProjectAndSprintId(project_id,sprint_id).stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(story,
                linkTo(methodOn(StoryController.class).getAllStoriesInSprint(project_id,sprint_id)).withSelfRel());
    }

    //Endpoint to get all stories in a sprint
    @GetMapping("/projects/{project_id}/epics/{epic_id}/stories")
    CollectionModel<EntityModel<Story>> getAllStoriesInEpic(@PathVariable Long project_id,@PathVariable Long epic_id){
        List<EntityModel<Story>> story = repository.findAllByProjectAndEpicId(project_id,epic_id).stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(story,
                linkTo(methodOn(StoryController.class).getAllStoriesInEpic(project_id,epic_id)).withSelfRel());
    }

    @PostMapping("/projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/create")
    Story newStory(@RequestBody Story newStory,@PathVariable Long project_id,@PathVariable Long sprint_id,@PathVariable Long epic_id){
        //Search for project with given id
        projectRep.findById(project_id).orElseThrow(()-> new ProjectNotFoundException(project_id));
        epicRep.findById(new EpicId(epic_id,project_id)).orElseThrow(()-> new EpicNotFoundException(new EpicId(epic_id,project_id)));
        sprintRep.findById(new SprintId(sprint_id,project_id)).orElseThrow(()-> new SprintNotFoundException(new SprintId(sprint_id,project_id)));

        Long newId = repository.findMaxId().orElse(0L);
        newStory.setId(newId+1L);

        return repository.save(newStory);
    }

    //Single item
    @GetMapping("/projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{id}")
    EntityModel<Story> one(@PathVariable Long id,@PathVariable Long project_id,@PathVariable Long sprint_id,@PathVariable Long epic_id){
        Story story = repository.findById(new StoryId(id,epic_id,sprint_id,project_id)) //
                .orElseThrow(()-> new StoryNotFoundException(new StoryId(id,epic_id,sprint_id,project_id)));

        return assembler.toModel(story);
    }

    @PutMapping("/projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{id}/archive/{user_id}")
    ResponseEntity<?> archiveStory(@PathVariable Long id, @PathVariable Long project_id, @PathVariable Long sprint_id, @PathVariable Long epic_id, @PathVariable Long user_id){
        //Check that the developer requesting to delete this epic is the product owner of the project
        Developer developer =  devRep.findById(new DeveloperId(user_id,project_id)).orElseThrow(()->new DeveloperNotFoundException(new DeveloperId(user_id,project_id)));
        if(developer.getRole()!=1L)
            throw new DeveloperNotFoundException(user_id,project_id);

        repository.archiveAllTasksInStory(id,project_id,sprint_id,epic_id);
        repository.archiveStory(id,project_id,sprint_id,epic_id);
        return ResponseEntity.ok(new MessageResponse("OK"));
    }

    @DeleteMapping("/projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{id}/delete/{user_id}")
    ResponseEntity<?> deleteStory(@PathVariable Long id,@PathVariable Long project_id,@PathVariable Long sprint_id,@PathVariable Long epic_id,@PathVariable Long user_id){
        //Check that the developer requesting to delete this epic is the product owner of the project
        Developer developer =  devRep.findById(new DeveloperId(user_id,project_id)).orElseThrow(()->new DeveloperNotFoundException(new DeveloperId(user_id,project_id)));
        if(developer.getRole()!=1L)
            throw new DeveloperNotFoundException(user_id,project_id);

        repository.deleteAllAssigneesInStory(id,project_id,sprint_id,epic_id);
        repository.deleteAllTasksInStory(id,project_id,sprint_id,epic_id);
        repository.deleteById(new StoryId(id,epic_id,sprint_id,project_id));
        return ResponseEntity.ok(new MessageResponse("OK"));
    }
}