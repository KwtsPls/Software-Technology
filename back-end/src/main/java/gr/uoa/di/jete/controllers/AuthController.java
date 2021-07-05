package gr.uoa.di.jete.controllers;

import gr.uoa.di.jete.auth.*;
import gr.uoa.di.jete.exceptions.InvalidUserRegistration;
import gr.uoa.di.jete.exceptions.UserNotFoundException;
import gr.uoa.di.jete.models.CustomUserDetails;
import gr.uoa.di.jete.models.User;
import gr.uoa.di.jete.models.UserDataTransferObject;
import gr.uoa.di.jete.repositories.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@CrossOrigin
@RestController
public class AuthController {
    final AuthenticationManager authenticationManager;

    final UserRepository userRepository;

    final UserService userService;

    final PasswordEncoder encoder;

    final JavaMailSender mailSender;

    final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, UserService userService, PasswordEncoder encoder, JavaMailSender mailSender, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
        this.encoder = encoder;
        this.mailSender = mailSender;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginCredentials loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDataTransferObject newUser) throws MessagingException, UnsupportedEncodingException {
        //Create a validation object and check email and password validity
        RegistrationValidation validation = new RegistrationValidation();
        if(!validation.isValid(newUser.getEmail(), newUser.getPassword(), newUser.getMatchingPassword()))
            throw new InvalidUserRegistration();
        User user = newUser.createUser();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String randomCode = RandomString.make(18);
        user.setVerification_code(randomCode);
        user.setIs_enabled(false);

        this.sendVerificationEmail(user,"http://localhost:8080");

        userService.addNewUser(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/users/{id}/updatePassword")
    public ResponseEntity<?> updateUserPassword(@RequestParam("password") String password, @RequestParam("old_password") String old_password,@PathVariable Long id){
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));

        //Check if the old password is the user's current password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), old_password));

        //update the password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password_hash = passwordEncoder.encode(password);
        userRepository.changeUserPassword(id,password_hash);

        return ResponseEntity.ok(new MessageResponse("Password updated successfully!"));
    }

    @GetMapping("/verify/code={code}&u={username}")
    public ResponseEntity<?> verifyUser(@PathVariable String code, @PathVariable String username){
        int status = userService.setUserToEnabled(code,username);
        if(status==1)
            return ResponseEntity.ok(new MessageResponse("User verified successfully!"));
        else
            return ResponseEntity.ok(new MessageResponse("User verification failed"));
    }

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "jeteappofficial@gmail.com";
        String senderName = "Jete - scrum app";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Jete.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName());
        String verifyURL = siteURL + "/verify/code=" + user.getVerification_code() + "&u=" + user.getUsername();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

}
