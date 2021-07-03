package gr.uoa.di.jete.controllers;


import gr.uoa.di.jete.auth.MessageResponse;
import gr.uoa.di.jete.models.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
public class UserController {

    private final UserService userService;


    public UserController(UserService userService){
        this.userService = userService;
    }

    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all(){
        List<EntityModel<User>> users = userService.getUserCollectionList();
        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    //Method for getting User By Username
    @GetMapping("/users/name={username}")
    EntityModel<User> getByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser){
        return userService.addNewUser(newUser);
    }

    //Single item
    @GetMapping("/users/{id}")
     EntityModel<User> one(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/projects/{project_id}/users")
    CollectionModel<EntityModel<User>> getUsersFromProject(@PathVariable Long project_id){
        List<EntityModel<User>> users = userService.getUsersByProjectId(project_id);
        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).getUsersFromProject(project_id)).withSelfRel());
    }


    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id){
        return userService.updateUser(newUser,id);
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("OK"));
    }

}
