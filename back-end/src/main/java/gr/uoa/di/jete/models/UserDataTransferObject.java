package gr.uoa.di.jete.models;

import com.sun.istack.NotNull;

public class UserDataTransferObject {

    @NotNull
    private String username;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String password;
    @NotNull
    private String matchingPassword;
    @NotNull
    private String email;

    public UserDataTransferObject(){}
    public UserDataTransferObject(String username,String firstname,String lastname,String password,String matchingPassword,String email){
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
    }

    public User createUser(){
        return new User(this.username,this.password,this.email,null,null,
                0L,null,this.firstname,this.lastname);
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
