package gr.uoa.di.jete.api;


import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class DeveloperController {

    private final DeveloperRepository repository;
    private final DeveloperModelAssembler assembler;

    DeveloperController(DeveloperRepository repository, DeveloperModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    //Aggregate root
    //tag::get-aggregate-root[]
    @GetMapping("/developers")
    CollectionModel<EntityModel<Developer>> all(){
        List<EntityModel<Developer>> developer = repository.findAll().stream() //
                .map(assembler :: toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(developer,
                linkTo(methodOn(DeveloperController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/developers")
    Developer newDeveloper(@RequestBody Developer newDeveloper){
        return repository.save(newDeveloper);
    }

    //Single item
    @GetMapping("/developers/{user_id}/{project_id}")
    EntityModel<Developer> one(@PathVariable Long user_id,@PathVariable Long project_id){
        Developer developer = repository.findById(new DeveloperId(user_id,project_id)) //
                .orElseThrow(()-> new DeveloperNotFoundException(new DeveloperId(user_id,project_id)));

        return assembler.toModel(developer);
    }

    @PutMapping("/developers/{user_id}/{project_id}")
    Developer replaceDeveloper(@RequestBody Developer newDeveloper, @PathVariable Long user_id,@PathVariable Long project_id){
        return repository.findById(new DeveloperId(user_id,project_id))
                .map(user -> {
                    newDeveloper.setRole(newDeveloper.getRole());
                    return repository.save(newDeveloper);
                })
                .orElseGet(()->{
                    newDeveloper.setUserId(user_id);
                    newDeveloper.setProjectId(project_id);
                    return repository.save(newDeveloper);
                });
    }

    @DeleteMapping("/developers/{user_id}/{project_id}")
    void deleteDeveloper( @PathVariable Long user_id,@PathVariable Long project_id){
        repository.deleteById(new DeveloperId(user_id,project_id));
    }
}
