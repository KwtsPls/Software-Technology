package gr.uoa.di.jete.controllers;


import gr.uoa.di.jete.auth.MessageResponse;
import gr.uoa.di.jete.exceptions.*;
import gr.uoa.di.jete.models.*;
import gr.uoa.di.jete.repositories.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    //Endpoint to get all stories in active sprints
    @GetMapping("/projects/{project_id}/sprints/active/stories")
    CollectionModel<EntityModel<Story>> getAllActiveStoriesInProject(@PathVariable Long project_id){
        List<EntityModel<Story>> story = repository.findAllByProjectAndActiveSprints(project_id).stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(story,
                linkTo(methodOn(StoryController.class).getAllActiveStoriesInProject(project_id)).withSelfRel());
    }

    //Endpoint to get all stories in a sprint
    @GetMapping("/projects/{project_id}/sprints/active/storiesInList")
    List<CollectionModel<EntityModel<Story>>> getAllActiveStoriesInProjectListFormat(@PathVariable Long project_id){
        List<CollectionModel<EntityModel<Story>>> list = new ArrayList<>();

        List<EntityModel<Story>> story_1 = repository.findAllByProjectAndSprintStatus(project_id,1L).stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        CollectionModel<EntityModel<Story>> stories_1 = CollectionModel.of(story_1,
                linkTo(methodOn(StoryController.class).getAllActiveStoriesInProjectListFormat(project_id)).withSelfRel());
        list.add(stories_1);

        List<EntityModel<Story>> story_2 = repository.findAllByProjectAndSprintStatus(project_id,2L).stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        CollectionModel<EntityModel<Story>> stories_2 = CollectionModel.of(story_2,
                linkTo(methodOn(StoryController.class).getAllActiveStoriesInProjectListFormat(project_id)).withSelfRel());
        list.add(stories_2);

        List<EntityModel<Story>> story_3 = repository.findAllByProjectAndSprintStatus(project_id,3L).stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        CollectionModel<EntityModel<Story>> stories_3 = CollectionModel.of(story_3,
                linkTo(methodOn(StoryController.class).getAllActiveStoriesInProjectListFormat(project_id)).withSelfRel());
        list.add(stories_3);

         return list;
    }

    //Endpoint to get all stories in archive sprints
    @GetMapping("/projects/{project_id}/sprints/archived/stories")
    CollectionModel<EntityModel<Story>> getAllArchivedStoriesInProject(@PathVariable Long project_id){
        List<EntityModel<Story>> story = repository.findAllByProjectAndArchivedSprints(project_id).stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(story,
                linkTo(methodOn(StoryController.class).getAllArchivedStoriesInProject(project_id)).withSelfRel());
    }

    //Endpoint to get all stories in archive sprints
    @GetMapping("/projects/{project_id}/sprints/archived/storiesInList")
    List<CollectionModel<EntityModel<Story>>> getAllArchivedStoriesInProjectListFormat(@PathVariable Long project_id){
        List<CollectionModel<EntityModel<Story>>> stories_list = new ArrayList<>();
        List<Sprint> archived_sprints = sprintRep.findArchivedSprintsInProject(project_id);

        for (Sprint archived_sprint : archived_sprints) {
            Long sprint_id = archived_sprint.getId();
            List<EntityModel<Story>> story = repository.findAllByProjectAndSprintId(project_id, sprint_id).stream() //
                    .map(assembler::toModel) //
                    .collect(Collectors.toList());
            CollectionModel<EntityModel<Story>> story_collection = CollectionModel.of(story,
                    linkTo(methodOn(StoryController.class).getAllArchivedStoriesInProjectListFormat(project_id)).withSelfRel());
            stories_list.add(story_collection);
        }

        return stories_list;
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