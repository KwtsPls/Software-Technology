package gr.uoa.di.jete.models;

import org.jetbrains.annotations.NotNull;

public class DeveloperInfo {

    @NotNull
    private Long user_id;
    @NotNull
    private String username;
    @NotNull
    private Long accepted;
    @NotNull
    private Long role;

    public DeveloperInfo(){}
    public DeveloperInfo(Long user_id,String username,Long accepted,Long role){
        this.user_id = user_id;
        this.username = username;
        this.accepted = accepted;
        this.role = role;
    }

    @Override
    public String toString() {
        return "DeveloperInfo{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", accepted=" + accepted +
                ", role=" + role +
                '}';
    }

    @NotNull
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(@NotNull Long user_id) {
        this.user_id = user_id;
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    @NotNull
    public Long getAccepted() {
        return accepted;
    }

    public void setAccepted(@NotNull Long accepted) {
        this.accepted = accepted;
    }

    @NotNull
    public Long getRole() {
        return role;
    }

    public void setRole(@NotNull Long role) {
        this.role = role;
    }
}
