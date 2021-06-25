package gr.uoa.di.jete.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CustomUserDetails implements UserDetails {

    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String bio;
    private String location;
    private Long status;
    private String pronouns;
    private String firstname;
    private String lastname;
    private String verification_code;
    private boolean is_enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id,String username,String password,String email,
                             String bio,String location,String pronouns,String firstname,String lastname,
                             String verification_code,boolean is_enabled,
                             Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.location = location;
        this.pronouns = pronouns;
        this.firstname = firstname;
        this.lastname = lastname;
        this.verification_code =verification_code;
        this.is_enabled = is_enabled;
        this.authorities = authorities;
    }

    public static CustomUserDetails build(User user) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        List<GrantedAuthority> authorities = Arrays.asList(authority);

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getBio(),
                user.getLocation(),
                user.getPronouns(),
                user.getFirstName(),
                user.getLastName(),
                user.getVerification_code(),
                user.isIs_enabled(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.is_enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustomUserDetails user = (CustomUserDetails) o;
        return Objects.equals(id, user.id);
    }

}
