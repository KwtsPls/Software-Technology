import gr.uoa.di.jete.controllers.UserController
import gr.uoa.di.jete.controllers.UserService
import gr.uoa.di.jete.models.User
import org.jetbrains.annotations.NotNull
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.EntityModel
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

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


}
