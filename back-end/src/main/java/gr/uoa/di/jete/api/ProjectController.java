package gr.uoa.di.jete.api;


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

    ProjectController(ProjectRepository repository, ProjectModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
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

    @PostMapping("/projects")
    Project newProject(@RequestBody Project newProject){
        return repository.save(newProject);
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
