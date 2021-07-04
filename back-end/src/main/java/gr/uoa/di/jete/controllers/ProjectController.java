package gr.uoa.di.jete.controllers;

import gr.uoa.di.jete.auth.MessageResponse;
import gr.uoa.di.jete.exceptions.DeveloperNotFoundException;
import gr.uoa.di.jete.exceptions.ProjectNotFoundException;
import gr.uoa.di.jete.exceptions.SprintNotFoundException;
import gr.uoa.di.jete.exceptions.UserNotFoundException;
import gr.uoa.di.jete.models.*;
import gr.uoa.di.jete.repositories.DeveloperRepository;
import gr.uoa.di.jete.repositories.ProjectRepository;
import gr.uoa.di.jete.repositories.SprintRepository;
import gr.uoa.di.jete.repositories.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
public class ProjectController {

    private final ProjectRepository repository;
    private final ProjectModelAssembler assembler;
    private final UserRepository userRep;
    private final DeveloperRepository devRep;
    private final SprintRepository sprintRep;

    ProjectController(ProjectRepository repository, ProjectModelAssembler assembler,
                      UserRepository userRep, DeveloperRepository devRep, SprintRepository sprintRep){
        this.repository = repository;
        this.assembler = assembler;
        this.userRep = userRep;
        this.devRep = devRep;
        this.sprintRep = sprintRep;
    }
    ProjectController(ProjectRepository repository,
                      UserRepository userRep, DeveloperRepository devRep, SprintRepository sprintRep){
        this.repository = repository;
        this.assembler = new ProjectModelAssembler();
        this.userRep = userRep;
        this.devRep = devRep;
        this.sprintRep = sprintRep;
    }

    @GetMapping("/projects")
    CollectionModel<EntityModel<Project>> all(){
        List<EntityModel<Project>> projects = repository.findAll().stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(projects,
                linkTo(methodOn(ProjectController.class).all()).withSelfRel());
    }

    @PostMapping("/projects/create/{user_id}")
    Project newProject(@RequestBody Project newProject,@PathVariable Long user_id){
        //Search for User with given id
        userRep.findById(user_id) //
                .orElseThrow(()-> new UserNotFoundException(user_id));

        //Create an entry for a new developer on the db and save him as a product owner
        Project saved_project = repository.save(newProject);
        Developer dev = new Developer(user_id,newProject.getId(),1L,1L);
        this.devRep.save(dev);

        //Get the dates for the sprints
        long millis=System.currentTimeMillis();
        Date current_date=new java.sql.Date(millis);
        Date date_2_weeks= this.addDays(new java.sql.Date(millis),14);
        Date date_4_weeks= this.addDays(new java.sql.Date(millis),28);
        Date date_6_weeks= this.addDays(new java.sql.Date(millis),42);

        //Create the initial sprints of the project
        Long sprint_id = sprintRep.findMaxId().orElse(0L);

        Sprint sprint_1 = new Sprint(sprint_id+1L,saved_project.getId(),"Sprint#1",1L,current_date,date_2_weeks);
        Sprint sprint_2 = new Sprint(sprint_id+2L,saved_project.getId(),"Sprint#2",2L,date_2_weeks,date_4_weeks);
        Sprint sprint_3 = new Sprint(sprint_id+3L,saved_project.getId(),"Sprint#3",3L,date_4_weeks,date_6_weeks);

        //Save the new sprints
        sprintRep.save(sprint_1);
        sprintRep.save(sprint_2);
        sprintRep.save(sprint_3);

        return saved_project;
    }

    //Controller to finalize a given sprint
    @PutMapping("/projects/{project_id}/sprints/archive/{user_id}")
    Sprint archiveSprint(@PathVariable Long project_id,@PathVariable Long user_id){
        Sprint sprint = sprintRep.findSprintByStatusInProject(project_id,1L)
                .orElseThrow(SprintNotFoundException::new);

        //Check that the developer requesting to create this sprint is the product owner of the project
        Developer developer =  devRep.findById(new DeveloperId(user_id,project_id)).orElseThrow(()->new DeveloperNotFoundException(new DeveloperId(user_id,project_id)));
        if(developer.getRole()!=1L)
            throw new DeveloperNotFoundException(user_id,project_id);

        Long old_sprint_id = sprint.getId();

        //Update the status of all sprints in this project
        sprintRep.updateActiveSprintsInProject(project_id);
        //Get the id of the current sprint
        Sprint current_sprint = sprintRep.findSprintByStatusInProject(project_id,1L).orElseThrow(SprintNotFoundException::new);
        Sprint old_final_sprint = sprintRep.findSprintByStatusInProject(project_id,2L).orElseThrow(SprintNotFoundException::new);

        //Transfer all unfinished tasks and stories to the new current sprint
        Long current_sprint_id = current_sprint.getId();
        sprintRep.transferAssignees(old_sprint_id,current_sprint_id);
        sprintRep.transferStories(old_sprint_id,current_sprint_id);
        sprintRep.transferTasks(old_sprint_id,current_sprint_id);

        //Create a new sprint to replace the archived one
        Long maxId = sprintRep.findMaxId().orElse(0L);
        //Create the title for the new sprint
        String[] arrOfStr = old_final_sprint.getTitle().split("#",2);
        int title_number = Integer.parseInt(arrOfStr[1])+1;
        String new_title = "Sprint#" + title_number;
        Sprint final_sprint = new Sprint(maxId+1L,project_id,new_title,3L,
                old_final_sprint.getDate_to(),this.addDays(old_final_sprint.getDate_to(),14));

        return sprintRep.save(final_sprint);
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

    @GetMapping("/users/{user_id}/projects")
    CollectionModel<EntityModel<Project>> getUserProjects(@PathVariable Long user_id){
        List<EntityModel<Project>> projects =  repository.findAllByUserId(user_id).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(projects,
                linkTo(methodOn(ProjectController.class).getUserProjects(user_id)).withSelfRel());
    }

    //Method to check if a user with a given username exists in a given project
    @GetMapping("/projects/{id}/user={username}")
    public ResponseEntity<?> checkUserInProject(@PathVariable Long id, @PathVariable String username){
        User user = repository.findUserByUsernameInProject(id,username).orElse(new User());
        if(user.getId()!=null)
            return ResponseEntity.ok(new MessageResponse("YES"));
        else
            return ResponseEntity.ok(new MessageResponse("NO"));
    }

    @PutMapping("/projects/{id}/archive/{user_id}")
    public ResponseEntity<?> archiveProject(@PathVariable Long id,@PathVariable Long user_id){

        //Check that the developer requesting to archive this project is the product owner of the project
        Developer developer =  devRep.findById(new DeveloperId(user_id,id)).orElseThrow(()->new DeveloperNotFoundException(new DeveloperId(user_id,id)));
        if(developer.getRole()!=1L)
            throw new DeveloperNotFoundException(user_id,id);

        repository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));

        //Save the date the project was finished
        long millis=System.currentTimeMillis();
        Date date_finished=new java.sql.Date(millis);

        //Archive all tasks in this project
        repository.archiveAllTasksInProject(id);
        //Archive all stories in this project
        repository.archiveAllStoriesInProject(id);
        //Archive all sprints in this project
        repository.archiveAllSprintsInProject(id);
        //Archive all epics in this project
        repository.archiveAllEpicsInProject(id);
        repository.setStatusToArchived(id,date_finished);

        return ResponseEntity.ok(new MessageResponse("OK"));
    }

    @DeleteMapping("/projects/{id}/delete/{user_id}")
    ResponseEntity<?> deleteProject(@PathVariable Long id,@PathVariable Long user_id){

        //Check that the developer requesting to archive this project is the product owner of the project
        Developer developer =  devRep.findById(new DeveloperId(user_id,id)).orElseThrow(()->new DeveloperNotFoundException(new DeveloperId(user_id,id)));
        if(developer.getRole()!=1L)
            throw new DeveloperNotFoundException(user_id,id);

        repository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        //Delete all developers in this project
        repository.deleteAllDevelopersInProject(id);
        //Delete all assignees in tasks in this project
        repository.deleteAllAssigneesInProject(id);
        //Delete all tasks in this project
        repository.deleteAllTasksInProject(id);
        //Delete all stories in this project
        repository.deleteAllStoriesInProject(id);
        //Delete all sprints in this project
        repository.deleteAllSprintsInProject(id);
        //Delete all epics in this project
        repository.deleteAllEpicsInProject(id);
        repository.deleteById(id);

        return ResponseEntity.ok(new MessageResponse("OK"));
    }

    public Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());
    }

}
