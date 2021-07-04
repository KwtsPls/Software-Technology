import gr.uoa.di.jete.Assemblers.PaymentsModelAssembler
import gr.uoa.di.jete.controllers.PaymentsController
import gr.uoa.di.jete.models.Payments
import gr.uoa.di.jete.models.User
import gr.uoa.di.jete.repositories.PaymentsRepository
import gr.uoa.di.jete.repositories.UserRepository
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.sql.Date

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest
@WebMvcTest(PaymentsController.class)
class PaymentsControllerTest extends Specification{


    MockMvc mvc
    UserRepository userRepository
    PaymentsRepository repository
    PaymentsController controller
    PaymentsModelAssembler assembler

    void setup(){
        userRepository = Stub()
        repository = Stub()
        assembler = new PaymentsModelAssembler()
        controller = new PaymentsController(repository,assembler,userRepository)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build()
    }

    def "Get certain Payment from id and user_id"(){
        when:"Payments returns a certain payment"
            repository.findById(_ as Long, _ as Long) >> {
                def str = "2021-06-21"
                def date = Date.valueOf(str)
                def pay = new Payments(1L,1L,date,1.33)
                return Optional.of(pay)
            }
        and: "Get @ /payments/1/1"
            def url = "/payments/1/1"
            MockHttpServletResponse response =  mvc.perform(
                    get(url)
                    .accept(MediaType.APPLICATION_JSON)
            ).andReturn().getResponse()
        then: "Response status is Ok and payment with id is returned and received 1.33"
            response.getStatus() == 200
            response.getContentAsString().contains("\"id\":1")
            response.getContentAsString().contains("1.33")

    }

    def "Get all payments stored in db"(){
        when: "Payments exist in repository"
            repository.findAll() >> {
                def str = "2021-06-21"
                def date = Date.valueOf(str)
                def pay = new Payments(1L,1L,date,1.33)
                def pay2 = new Payments(2L,2L,date,1.22)
                return [pay,pay2]
            }
        and: "Get @ /payments"
            def url = "/payments"
            MockHttpServletResponse response =  mvc.perform(
                get(url)
            ).andReturn().getResponse()
        then: "Response status is Ok and payment with id is returned and received 1.33"
            response.getStatus() == 200
            response.getContentAsString().contains("\"id\":1")
            response.getContentAsString().contains("1.33")
            response.getContentAsString().contains("\"id\":2")
            response.getContentAsString().contains("1.22")
    }

    def "Get all payments from the same user" (){
        when: "Payments exist in repository"
           repository.findAllByUsername(_ as String) >> {
                def str = "2021-06-21"
                def date = Date.valueOf(str)
                def pay = new Payments(1L,1L,date,1.33)
                def pay2 = new Payments(2L,1L,date,1.22)
                return Optional.of([pay,pay2])
            }
        and: "Get @ /payments"
            def url = "/users/someone/payments"
            MockHttpServletResponse response =  mvc.perform(
                get(url)
            ).andReturn().getResponse()
        then: "Response status is Ok and payment with id is returned and received 1.33"
            response.getStatus() == 200
            !response.getContentAsString().contains("\"user_id\":2")
            response.getContentAsString().contains("\"id\":1")
            response.getContentAsString().contains("1.33")
            response.getContentAsString().contains("\"id\":2")
            response.getContentAsString().contains("1.22")

    }


    def "Post new payment of a user"(){
        when: "Posting a new payment"
            def body = new String("{\n" +
                    "\"id\" : 1,\n" +
                    "\"received_date\" : \"2021-06-21\",\n" +
                    "\"received\" : 1.33\n}")
            userRepository.findByUsername(_ as String) >> {
                def user = new User()
                return Optional.of(user)
            }
            repository.save(_ as Payments) >> {
                def str = "2021-06-21"
                def date = Date.valueOf(str)
                def pay = new Payments(1L,1L,date,1.33)
                return pay
            }
        and: "Post @ /payments/username"
            def url = "/payments/username"
            MockHttpServletResponse response =  mvc.perform(
                post(url)
                    .content(body)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andReturn().getResponse()
        then: "Response status is Ok and payment with id is returned and received 1.33"
            response.getStatus() == 200
            response.getContentAsString().contains("\"userId\":1")
            response.getContentAsString().contains("\"id\":1")
            response.getContentAsString().contains("1.33")
    }


}
