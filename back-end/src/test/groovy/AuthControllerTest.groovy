import com.fasterxml.jackson.databind.ObjectMapper
import gr.uoa.di.jete.AuthenticationMock
import gr.uoa.di.jete.auth.JwtUtils
import gr.uoa.di.jete.controllers.AuthController
import gr.uoa.di.jete.controllers.UserService
import gr.uoa.di.jete.models.User
import gr.uoa.di.jete.repositories.UserRepository
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import javax.mail.internet.MimeMessage

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = [MimeMessageHelper.class, AuthController.class,AuthenticationManager.class,UserRepository.class,UserService.class,PasswordEncoder.class,JavaMailSender.class,JwtUtils.class])
@WebMvcTest(AuthController.class)
class AuthControllerTest extends Specification {


    MockMvc mvc


    AuthenticationManager authenticationManager
    UserRepository userRepository

    UserService userService
    PasswordEncoder encoder


    JavaMailSender mailSender
    JwtUtils jwtUtils


    AuthController controller


    def setup(){
        this.authenticationManager = Stub(AuthenticationManager.class)
        authenticationManager.authenticate(_ as UsernamePasswordAuthenticationToken) >> new AuthenticationMock()
        this.userRepository = Mock(UserRepository.class)
        this.userService = Mock(UserService.class)
        this.encoder = Mock(PasswordEncoder.class)
        this.mailSender = Stub(JavaMailSender.class)
        mailSender.createMimeMessage().setFrom() >> true

        jwtUtils = Mock(JwtUtils.class)
        controller = new AuthController(authenticationManager,userRepository,userService,encoder,mailSender,jwtUtils)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build()

    }


    def 'user signing up should return ok' () {
        when: "JSON creation"
            String userJson = new String("{" +
                    "\"username\":\"test1\",\n" +
                    "\"password\":\"1234\",\n" +
                    "\"matchingPassword\":\"1234\",\n" +
                    "\"email\":\"lstetsikas3@gmail.com\",\n"+
                    "\"firstname\":\"nada\",\n" +
                    "\"lastname\":\"nada\"}")
        and: "Performing a post"
            String url = new String("/signup")
            MockHttpServletResponse response =  mvc.perform(
                        post(url)
                            .content(userJson)
                            .contentType(MediaType.APPLICATION_JSON)
            ).andReturn().getResponse()
        then: "Status is OK"
            response.getStatus() == 200
        and: "User was added to User Service"
            1 * userService.addNewUser(_ as User)
    }

    def 'user loging in should return ok'(){
        when: "Given Login Credentials"
            String userLogin = new String(
             "      { \n " +
                        "\"username\":\"test1\",\n" +
                        "\"password\": \"1234\"\n" +
                   " } "
            )
        and: "Performing a post"
            String url = new String("/signin")
            MockHttpServletResponse response =  mvc.perform(
                    post(url)
                            .content(userLogin)
                            .contentType(MediaType.APPLICATION_JSON)
            ).andReturn().getResponse()
        then: "Status is OK and returns user with username test1 and email lstetsikas3@gmail.com"
            response.getStatus() == 200
            response.getContentAsString().contains("\"id\":1")
            response.getContentAsString().contains("\"username\":\"test1\"")
            response.getContentAsString().contains("\"email\":\"email\"")
    }

    def 'Verifying user should return ok and User Verified'(){
        when: 'Setting user to enabled'
            userService.setUserToEnabled(_ as String, _ as String) >> 1
        and: 'Get Call'
            String url = new String("/verify/code=1&u=username")
            MockHttpServletResponse response = mvc.perform(
                    get(url)
            ).andReturn().getResponse()
        then:"Ok should be returned and Message User verified successfully!"
            response.getStatus() == 200
            response.getContentAsString().contains("User verified successfully!")

    }

    def 'Verifying user should return ok and User Verification failed'(){
        when: 'Setting user to enabled'
            userService.setUserToEnabled(_ as String, _ as String) >> 0
        and: 'Get Call'
            String url = new String("/verify/code=1&u=username")
            MockHttpServletResponse response = mvc.perform(
                    get(url)
            ).andReturn().getResponse()
        then:"Ok should be returned and Message User verified successfully!"
            response.getStatus() == 200
            response.getContentAsString().contains("User verification failed")

    }

}

