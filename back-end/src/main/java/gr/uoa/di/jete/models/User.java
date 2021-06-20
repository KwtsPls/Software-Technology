package gr.uoa.di.jete.models;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "user")
public class User {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String username;
    private String password;
    private String email;
    private String bio;
    private String location;
    private Long status;
    private String pronouns;
    @SerializedName(value = "firstName")
    private String firstname;
    @SerializedName(value = "lastName")
    private String lastname;

    public User(){}
    public User(String username, String password, String email, String bio, String location,
                Long status, String pronouns, String firstname, String lastname){
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.location = location;
        this.status = status;
        this.pronouns = pronouns;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }



    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof User))
            return false;
        User user = (User) o;
        return Objects.equals(this.id, user.id) && Objects.equals(this.username, user.username)
                && Objects.equals(this.email, user.email) && Objects.equals(this.bio, user.bio)
                && Objects.equals(this.firstname, user.firstname) && Objects.equals(this.lastname, user.lastname)
                && Objects.equals(this.location, user.location) && Objects.equals(this.status, user.status)
                && Objects.equals(this.pronouns, user.pronouns) && Objects.equals(this.password, user.password) ;

    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id,this.username,this.email);
    }

    @Override
    public String toString(){
        return "User{" + "id=" + this.id + ", username='" + this.username
                + '\'' + ", email='" + this.email + '\'' + ", bio='" + this.bio + '\'' +
                ", location='" + this.location + '\'' + ", status='" + this.status + '\'' +
                ", pronouns='" + this.pronouns + '\'' + ", firstname='" + this.firstname + '\'' +
                ", lastname='" + this.lastname + '\'' + '}';
    }


    public void setStatus(Long status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) { this.password = password; }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPronouns() {
        return pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }


}

