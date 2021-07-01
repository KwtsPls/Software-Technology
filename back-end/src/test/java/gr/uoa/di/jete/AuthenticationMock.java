package gr.uoa.di.jete;

import gr.uoa.di.jete.models.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationMock implements Authentication {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal(){
        CustomUserDetails user = new CustomUserDetails(1L,
                "test1",
                "1234",
                "email",
                "bio",
                "location",
                "pronouns",
                "firstname",
                "lastname",
                "1234",
                true,
                null);
        return (Object) user;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
