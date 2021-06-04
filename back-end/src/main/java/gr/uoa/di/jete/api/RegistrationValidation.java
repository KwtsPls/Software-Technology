package gr.uoa.di.jete.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationValidation {

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    public boolean isValid(String email,String password,String matchingPassword){
        return (validateEmail(email) && validatePassword(password,matchingPassword));
    }

    private boolean validateEmail(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password,String matchingPassword){
        return password.equals(matchingPassword);
    }
}
