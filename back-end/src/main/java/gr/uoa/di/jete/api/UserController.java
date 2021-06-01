package gr.uoa.di.jete.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("/api")
class UserController {

    private final UserService userService;



//    private final UserRepository repository;
//    private final UserModelAssembler assembler;

    UserController(UserService userService){
//        this.repository = repository;
        this.userService = userService;
//        this.assembler = assembler;
    }

    //Aggregate root
    //tag::get-aggregate-root[]
    @CrossOrigin
    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all(){
        List<EntityModel<User>> users = userService.getUserCollectionList();
        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/users")
    User newUser(@RequestBody User newUser){
        return userService.addNewUser(newUser);
    }

    //Single item

    @GetMapping("/users/{id}")
     EntityModel<User> one(@PathVariable Long id){
        return userService.getUserById(id);
    }


    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id){
        return userService.updateUser(newUser,id);
    }

    /*
    @PutMapping("/users/{id}")
    void updateUser(@PathVariable("id") Long id, @RequestParam(required = false) String username,
                    @RequestParam(required = false) String email,
                    @RequestParam(required = false) String bio,
                    @RequestParam(required = false) String location,
                    @RequestParam(required = false) Long status,
                    @RequestParam(required = false) String pronouns,
                    @RequestParam(required = false) String firstname,
                    @RequestParam(required = false) String lastname) {
        userService.updateUser(id, username, email, bio, location, status,
                pronouns, firstname, lastname);
    }
    */

    @DeleteMapping("/users/{id}")
    void deleteEmployee(@PathVariable Long id){
        userService.deleteById(id);
    }



}
