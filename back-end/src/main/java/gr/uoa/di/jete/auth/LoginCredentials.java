package gr.uoa.di.jete.auth;

import java.util.Objects;

public class LoginCredentials {
    private String username;
    private String password;

    public LoginCredentials(String username,String password){
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof LoginCredentials))
            return false;
        LoginCredentials loginCredentials = (LoginCredentials) o;
        return Objects.equals(this.username, loginCredentials.username) && Objects.equals(this.password,loginCredentials.password) ;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.username,this.username,this.password);
    }

    @Override
    public String toString(){
        return "LoginCredentials{" + ", username='" + this.username
                + '\'' + ", password='" + this.password + '\'' + '}';
    }

}
