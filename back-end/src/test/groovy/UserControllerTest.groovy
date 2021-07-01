import gr.uoa.di.jete.controllers.UserController
import gr.uoa.di.jete.controllers.UserService
import gr.uoa.di.jete.models.User

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete

@SpringBootTest(classes = [UserController.class])
@WebMvcTest(UserController.class)
class UserControllerTest extends Specification{

    MockMvc mvc

    UserService service

    UserController controller

    def setup(){
        service = Stub(UserService.class)
        controller = new UserController(service)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build()

    }

    def "GET to '/users should return a Collection Model of Users"() {
        when: "User Service returns a list of users"
            User user1 = new User("user1","pass","email","bio"
                    ,null,null,null,null,null)
           User user2 = new User("user2","pass","email","bio"
                   ,null,null,null,null,null)

            service.getUserCollectionList()>> [user1,user2]
        and: "Geting from /users"
            String url = "/users"
            MockHttpServletResponse response =  mvc.perform(
                get(url)
            ).andReturn().getResponse()
        then: "Ok response should be returned and a colection model of users"
            response.getStatus() == 200
            response.getContentAsString().contains("user1")
            response.getContentAsString().contains("user2")
    }

    def "Get User by username"(){
        when: "User Service returns a certain user"
            User user1 = new User("user1","pass","email","bio"
                ,"location",1,"he/him","bada","bada")
            user1.setId(1L)
            user1.setIs_enabled(true)
            service.getUserByUsername("user1") >> [user1]
        and: "Getting from /users/name=user1"
            String url = "/users/name=user1"
            MockHttpServletResponse response = mvc.perform(
                    get(url)
            ).andReturn().getResponse()
        then:"Ok response should be returned and user1"
            response.getStatus() == 200
            response.getContentAsString().contains("user1")
            response.getContentAsString().contains("email")

    }

    def "Post new User"(){
        when:"Posting new user to /users"
            String path = "/users"

            String name = "otinani"
            String pass = "1234"
            String mail = "mail@gmail.com"
            String bioo = "bio"
            String loc = "lcoatio"
            String pron = "dajwd"
            int stat = 1
            String first = "kati"
            String last  = "last"

            String body = new String("{\n" +
                "\"username\" :" + "\"" + name + "\",\n" +
                "\"password\" : \"" + pass + "\",\n" +
                "\"email\" : \"" + mail + "\",\n" +
                "\"bio\" :\"" + bioo + "\",\n" +
                "\"location\" : \"" + loc + "\",\n" +
                "\"status\" : \"" + stat + "\",\n" +
                "\"pronouns\" : \"" + pron +"\",\n" +
                "\"firstname\" : \"" + first + "\",\n" +
                "\"lastname\" :\"" + last + "\"}"
            )


            User user = new User(name,pass,mail,bioo,loc,stat,pron,first,last)
            user.setId(1L)
            user.setIs_enabled(true)
            service.addNewUser(_ as User) >> user
            MockHttpServletResponse response = mvc.perform(
                    post(path)
                    .content(body)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andReturn().getResponse()
        then:"Ok response should be returned and user1"
            response.getStatus() == 200
            response.getContentAsString().contains(name)
            response.getContentAsString().contains(mail)
    }

    def "Get User with user id"() {
        when: "Create user with user id = 1"
            String path = "/users/1"
            String name = "otinani"
            String pass = "1234"
            String mail = "mail@gmail.com"
            String bioo = "bio"
            String loc = "lcoatio"
            String pron = "dajwd"
            int stat = 1
            String first = "kati"
            String last  = "last"
            User user = new User(name,pass,mail,bioo,loc,stat,pron,first,last)
            user.setId(1L)
            user.setIs_enabled(true)
            service.getUserById(1L) >> [user]
        and: "Getting user with id = 1"
            MockHttpServletResponse response = mvc.perform(
                get(path)
        ).andReturn().getResponse()
        then:"Ok response should be returned and user1"
            response.getStatus() == 200
            response.getContentAsString().contains(name)
            response.getContentAsString().contains(mail)
    }

    def "Get users working on a certain project"(){
        when: "User Service returns a list of users"
            User user1 = new User("user1","pass","email","bio"
                ,null,null,null,null,null)
            user1.setId(1L)
            User user2 = new User("user2","pass","email","bio"
                ,null,null,null,null,null)
            user2.setId(2L)

            service.getUsersByProjectId(1L)>> [user1,user2]
        and: "Getting project with id = 1"
            String path = "/projects/1/users"
            MockHttpServletResponse response = mvc.perform(
                get(path)
            ).andReturn().getResponse()
        then:"Ok response should be returned and user1"
           response.getStatus() == 200
            response.getContentAsString().contains("user1")
            response.getContentAsString().contains("user2")
    }

    def "Update or replace certain user with put"(){
        when: "User service returns new user"
        User user1 = new User("user1","pass","email","bio"
                ,null,null,null,null,null)
            user1.setId(1L)

        and: "Changing username to user2"
        String body = "{ \"username\" : \"user2\"}"

        service.updateUser(_ as User,1L) >> {
            user1.setUsername("user2")
            user1
        }
        and: " Putting user with id = 1"
        String path = "/users/1"
        MockHttpServletResponse response = mvc.perform(
                put(path)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse()
        then: "Ok should be returned and user should be returned with username user2"
        response.getStatus() == 200
        !response.getContentAsString().contains("user1")
        response.getContentAsString().contains("user2")
    }

     def "Deleting user" (){
        when: "Having counter"
        def cnt = 0
        and: "UserService delete by id is called cnt + 1"
        service.deleteById(1) >> {
            cnt++
        }
        and: "Delete is called to users/1"
        String path = "/users/1"
        MockHttpServletResponse response = mvc.perform(
                delete(path)
        ).andReturn().getResponse()
         then:
         cnt == 1
         response.getStatus() == 200
     }

}
