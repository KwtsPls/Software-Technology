import gr.uoa.di.jete.Assemblers.AssigneeModelAssembler
import gr.uoa.di.jete.controllers.AssigneeController
import gr.uoa.di.jete.models.Assignee
import gr.uoa.di.jete.models.AssigneeId
import gr.uoa.di.jete.repositories.AssigneeRepository
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest
@WebMvcTest(AssigneeController.class)
class AssigneeControllerTest extends Specification {

    MockMvc mvc

    AssigneeRepository repository
    AssigneeModelAssembler assembler

    AssigneeController controller
    def setup(){
        repository = Stub(AssigneeRepository.class)
        assembler = new AssigneeModelAssembler()
        controller = new AssigneeController(repository,assembler)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build()
    }

    def "Getting assignees"(){
        when: "When having assignees"
        def as1 = new Assignee(1L,1L,1L,1L,1L,1L)
        def as2 = new Assignee(2L, 2L, 2L, 2L, 2L, 2L)
        and: "Stubbing  Rep and Model assembler"
        def  asList = [as1, as2]
        repository.findAll() >> asList
        and: "Getting assignees"
        def url = "/assignees/"
        MockHttpServletResponse response =  mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Ok is returned and 1 , 2 should exist"
        response.getStatus() == 200
        response.getContentAsString().contains("\"epic_id\":1")
        response.getContentAsString().contains("\"epic_id\":2")

    }

    def "Getting single item from assignees"(){
        when: "When having certain assignee to find and not to find"
        def asID = new AssigneeId(1L,1L,1L,1L,1L,1L)
        Optional<Assignee> as1 = Optional.of((new Assignee(1L,1L,1L,1L,1L,1L)))
        and: "Stubbing Repository"
        new AssigneeId(1L,1L,1L,1L,1L,1L) >> asID
        repository.findById(asID) >>  as1
        and: "Getting to path"
        def url = "/assignees/users/1/projects/1/sprints&epics/1&1/stories/1/tasks/1"
        MockHttpServletResponse response =  mvc.perform(
                get(url)
        ).andReturn().getResponse()

        then: "Ok is returned and 1 , 2 should exist"
        response.getStatus() == 200
    }

    def "Post Assignee" (){
        when: "Creating Assignee"
            def cnt = 0
            def body = new String("{\n" +
                    "\"id\":1,\n" +
                    "\"project_id\":1,\n" +
                    "\"story_id\":1,\n" +
                    "\"epic_id\":1,\n" +
                    "\"sprint_id\":1,\n" +
                    "\"task_id\":1\n}")
        and: "Stubbing Repository"
            repository.save(_ as Assignee) >> {
                cnt++
                def ass = new Assignee()
                ass.setUser_id(1L)
                return ass
            }
        and: "Post to path"
        def url = "/assignees/"
        MockHttpServletResponse response =  mvc.perform(
                post(url).content(body).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse()

        then: "Ok is returned assignee with user_id  1"
            response.getStatus() == 200
            response.getContentAsString().contains("\"user_id\":1")
            cnt==1
    }
}


