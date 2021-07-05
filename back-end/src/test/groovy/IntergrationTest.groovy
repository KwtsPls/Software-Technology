import com.google.gson.JsonObject
import com.google.gson.JsonParser
import gr.uoa.di.jete.AuthenticationMock
import gr.uoa.di.jete.assemblers.EpicModelAssembler
import gr.uoa.di.jete.assemblers.ProjectModelAssembler
import gr.uoa.di.jete.assemblers.StoryModelAssembler
import gr.uoa.di.jete.assemblers.TaskModelAssembler
import gr.uoa.di.jete.auth.JwtResponse
import gr.uoa.di.jete.auth.JwtUtils
import gr.uoa.di.jete.auth.LoginCredentials
import gr.uoa.di.jete.auth.MessageResponse
import gr.uoa.di.jete.auth.RegistrationValidation
import gr.uoa.di.jete.controllers.*
import gr.uoa.di.jete.models.Developer
import gr.uoa.di.jete.models.Project
import gr.uoa.di.jete.models.Sprint
import gr.uoa.di.jete.models.User
import gr.uoa.di.jete.models.UserDataTransferObject
import gr.uoa.di.jete.repositories.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import java.sql.Date

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest
class IntergrationTest extends Specification {


    UserRepository userRepository
    UserService userService
    AuthenticationManager authenticationManager
    PasswordEncoder encoder
    JavaMailSender mailSender
    JwtUtils jwtUtils

    ProjectRepository projectRepository
    ProjectModelAssembler projectModelAssembler
    DeveloperRepository developerRepository
    SprintRepository sprintRepository

    EpicRepository epicRepository
    EpicModelAssembler epicModelAssembler

    StoryRepository storyRepository
    StoryModelAssembler storyModelAssembler

    TaskRepository taskRepository
    TaskModelAssembler taskModelAssembler


    AuthController authController
    UserController userController
    ProjectController projectController
    EpicController epicController
    StoryController storyController
    TaskController taskController

    def setup(){
        this.authenticationManager = Stub(AuthenticationManager.class)
        authenticationManager.authenticate(_ as UsernamePasswordAuthenticationToken) >> new AuthenticationMock()

        this.userRepository = Stub(UserRepository.class)
        this.userService = Stub(UserService.class)
        this.encoder = Mock(PasswordEncoder.class)
        this.mailSender = Stub(JavaMailSender.class)
        this.jwtUtils = Stub(JwtUtils.class)
        mailSender.createMimeMessage().setFrom() >> true


        projectRepository = Stub()
        projectModelAssembler = new ProjectModelAssembler()
        developerRepository = Stub()
        sprintRepository = Stub()

        epicRepository = Stub()
        epicModelAssembler = Stub()

        storyRepository = Stub()
        storyModelAssembler = new StoryModelAssembler()

        taskRepository = Stub()
        taskModelAssembler = new TaskModelAssembler()


        authController = new AuthController(authenticationManager,userRepository,userService,encoder,mailSender,jwtUtils)
        userController = new UserController(userService)
        projectController = new ProjectController(projectRepository,projectModelAssembler,userRepository,developerRepository,sprintRepository)
        epicController = new EpicController(epicRepository,epicModelAssembler,projectRepository,developerRepository)
        storyController = new StoryController(storyRepository,storyModelAssembler,epicRepository,sprintRepository,projectRepository,developerRepository)
        taskController = new TaskController(taskRepository,taskModelAssembler,storyRepository,epicRepository,sprintRepository)
    }

    def "User Signs up and activates his account and logins"(){
        when: "User signsup"
        def user = new User("test1","1234","email","bio","location",0,null,"first","last")
        user.setId(1L)
        def signup = new UserDataTransferObject("test1","first","last","1234","1234","email@gmail.com")
        ResponseEntity<MessageResponse> response = authController.registerUser(signup)
        and:"Verifying user"
        userService.setUserToEnabled(_ as String, _ as String) >> 1L
        ResponseEntity<MessageResponse> response2 = authController.verifyUser("code","test1")
        and:"User Logins"
        def login = new LoginCredentials("test1","1234")
        ResponseEntity<JwtResponse> response3 = authController.authenticateUser(login)
        then:
        response.getStatusCodeValue() == 200
        response.getBody().getMessage() == "User registered successfully!"
        response2.getStatusCodeValue() == 200
        response2.getBody().getMessage() == "User verified successfully!"
        response3.getStatusCodeValue() == 200
    }

    def "User Logins and creates a project"(){
        when: "User logins"
        def user = new User("test1","1234","email","bio","location",0,null,"first","last")
        user.setId(1L)
        def login = new LoginCredentials("test1","1234")
//        jwtUtils.generateJwtToken(_ as Authentication) >> "kati"
        ResponseEntity<JwtResponse> response = authController.authenticateUser(login)
        and:"Getting users/id"
        def UserId = response.getBody().getId()
        and: "Create  a project for user"
        def cnt = 0
        def proj
        proj = new Project("titlos","desc",0,Mock(Date.class))
        proj.setId(1L)
        userRepository.findById(_ as Long) >> Optional.of(user)
        projectRepository.save(_ as Project) >> {
            cnt++

            return proj
        }
        developerRepository.save(_ as Developer) >> {
            cnt++
        }
        sprintRepository.findMaxId() >> Optional.of(1L)
        sprintRepository.save(_ as Sprint) >> {
            cnt++
        }

        def response2 = projectController.newProject(proj,UserId)

        then:
            response.getStatusCodeValue() == 200
            response2 == proj
            cnt == 5


    }


}
