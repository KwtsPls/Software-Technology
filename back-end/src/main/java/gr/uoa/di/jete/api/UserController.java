package gr.uoa.di.jete.api;


import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

=======
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.*;

import java.security.Principal;
>>>>>>> aad641a273fb909e3de4e81457b1cf8833ae0b04
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("/api")
class UserController {

    private final UserService userService;


    UserController(UserService userService){
        this.userService = userService;
    }

    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @CrossOrigin
    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all(){
        List<EntityModel<User>> users = userService.getUserCollectionList();
        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    //Single item
    @CrossOrigin
    @PostMapping("/users/login")
    EntityModel<User> login(@NotNull @RequestBody LoginCredentials loginCredentials){
        return userService.getUserByLogin(loginCredentials.getUsername(),loginCredentials.getPassword());
    }

    @RequestMapping(value = "/users/login/success", method = RequestMethod.GET)
    @ResponseBody
    public EntityModel<User> currentUserName(Principal principal) {
        return userService.getUserByUsername(principal.getName());
    }

    //Method for getting User By Username
    @GetMapping("/users/name={username}")
    EntityModel<User> getByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }
    //Method for user registration
    @CrossOrigin
    @PostMapping("/users/register")
    User registerUser(@RequestBody UserDataTransferObject newUser){
        //Create a validation object and check email and password validity
        RegistrationValidation validation = new RegistrationValidation();
        if(!validation.isValid(newUser.getEmail(), newUser.getPassword(), newUser.getMatchingPassword()))
            throw new InvalidUserRegistration();
        User user = newUser.createUser();
        PasswordEncoder passwordEncoder = encoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.addNewUser(user);
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


    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id){
        return userService.updateUser(newUser,id);
    }

    @DeleteMapping("/users/{id}")
    void deleteEmployee(@PathVariable Long id){
        userService.deleteById(id);
    }

}
