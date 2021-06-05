package gr.uoa.di.jete.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final UserModelAssembler userModelAssembler;


    //Constructor
    public UserService(UserRepository userRepository, UserModelAssembler userModelAssembler) {
        this.userRepository = userRepository;
        this.userModelAssembler = userModelAssembler;
    }

    //Returns all the User .json entities
    public List<EntityModel<User>> getUserCollectionList() {
        return userRepository.findAll().stream() //
                .map(userModelAssembler::toModel) //
                .collect(Collectors.toList());
    }

    //Returns the User with the given id
    public EntityModel<User> getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userModelAssembler.toModel(user);
    }

    //Returns the User with the given username and password
    public EntityModel<User> getUserByLogin(String username,String password) {
        User user = userRepository.findByLogin(username,password).orElse(new User());
        return userModelAssembler.toModel(user);
    }


    //Inserts new user to Table
    public User addNewUser(User newUser) {

        if(!(newUser.getUsername()!=null
                && newUser.getUsername().length()>0))
            throw new IllegalStateException("Invalid Input");
        if(!(newUser.getPassword()!=null
                && newUser.getPassword().length()>0))
            throw new IllegalStateException("Invalid Input");
        if(!(newUser.getEmail()!=null
                && newUser.getEmail().length()>0))
            throw new IllegalStateException("Invalid Input");
        if(newUser.getStatus() == null)
            throw new IllegalStateException("Invalid Input");
        if(!(newUser.getFirstName()!=null
                && newUser.getFirstName().length()>0))
            throw new IllegalStateException("Invalid Input");
        if(!(newUser.getLastName()!=null
                && newUser.getLastName().length()>0))
            throw new IllegalStateException("Invalid Input");


        Optional<User> userByEmail = userRepository.findUserByEmail(newUser.getEmail());
        if (userByEmail.isPresent())
            throw new EmailInUseException(newUser.getEmail());

        Optional<User> userByUsername = userRepository.findByUsername(newUser.getEmail());
        if (userByUsername.isPresent())
            throw new UserInUseException(newUser.getUsername());

        return userRepository.save(newUser);
    }

    //Updates all or certain parts of User
    public User updateUser(User newUser, Long id) {

        return userRepository.findById(id)
        .map(user -> {
            this.checkSetUsername(user, newUser.getUsername());
            this.checkSetEmail(user, newUser.getEmail());
            this.checkSetFirstName(user, newUser.getFirstName());
            this.checkSetLastName(user, newUser.getLastName());
            this.checkSetPronouns(user, newUser.getPronouns());
            this.checkSetLocation(user, newUser.getLocation());
            this.checkSetBio(user, newUser.getBio());
            this.checkSetStatus(user, newUser.getStatus());
            return userRepository.save(user);
        }).orElseGet(() -> {
            newUser.setId(id);
            return userRepository.save(newUser);
        });
    }
    //CHECK SETTERS, checks if something exists already if it was given null, so to update only certain parts

    private void checkSetStatus(User user, Long status) {
        if(status!=null && !Objects.equals(user.getStatus(),status))
            user.setStatus(status);
    }

    private void checkSetBio(User user, String bio) {
        if (bio != null && bio.length() > 0 && !Objects.equals(user.getBio(), bio))
            user.setBio(bio);
    }

    private void checkSetLocation(User user, String location) {
        if(location!=null && location.length()>0 && !Objects.equals(user.getLocation(),location))
            user.setLocation(location);
    }

    private void checkSetPronouns(User user, String pronouns) {
        if(pronouns!=null && pronouns.length()>0 && !Objects.equals(user.getPronouns(),pronouns))
            user.setPronouns(pronouns);
    }

    private void checkSetLastName(User user, String lastname) {
        if(lastname!=null && lastname.length()>0 && !Objects.equals(user.getLastName(),lastname))
            user.setLastName(lastname);
    }

    private void checkSetFirstName(User user, String firstname) {
        if(firstname!=null && firstname.length()>0 && !Objects.equals(user.getFirstName(),firstname))
            user.setFirstName(firstname);
    }

    private void checkSetEmail(User user, String email) {
        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {
            Optional<User> opt_user = userRepository.findUserByEmail(email);
            if (opt_user.isPresent()) throw new EmailInUseException(email);
            user.setEmail(email);
        }
    }

    private void checkSetUsername(User user, String username) {
        if (username != null && username.length() > 0 && !Objects.equals(user.getUsername(), username)) {
            Optional<User> opt_user = userRepository.findByUsername(username);
            if (opt_user.isPresent()) throw new UserInUseException(username);
            user.setUsername(username);
        }
    }

    /*
    @Transactional
    public void updateUser(Long id, String username, String email,
                           String bio, String location, Long status,
                           String pronouns, String firstname, String lastname) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        if (username != null && username.length() > 0 && !Objects.equals(user.getUsername(), username)) {
            Optional<User> opt_user = userRepository.findByUsername(username);
            if (opt_user.isPresent()) throw new IllegalStateException("USERNAME: " + username + "ALREADY IN USE!");
            user.setUsername(username);
        }
        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {
            Optional<User> opt_user = userRepository.findUserByEmail(email);
            if (opt_user.isPresent()) throw new IllegalStateException("EMAIL: " + email + "ALREADY EXISTS!");
            user.setEmail(email);
        }
        if (bio != null && bio.length() > 0 && !Objects.equals(user.getBio(), bio))
            user.setBio(bio);
        if(location!=null && location.length()>0 && !Objects.equals(user.getLocation(),location))
            user.setLocation(location);
        if(status!=null && !Objects.equals(user.getStatus(),status))
            user.setStatus(status);
        if(pronouns!=null && pronouns.length()>0 && !Objects.equals(user.getPronouns(),pronouns))
            user.setPronouns(pronouns);
        if(firstname!=null && firstname.length()>0 && !Objects.equals(user.getFirstName(),firstname))
            user.setFirstName(firstname);
        if(lastname!=null && lastname.length()>0 && !Objects.equals(user.getLastName(),lastname))
            user.setLastName(lastname);
    }

     */

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}