import com.fasterxml.jackson.databind.JsonNode
import gr.uoa.di.jete.controllers.AssigneeController
import gr.uoa.di.jete.exceptions.AssigneeNotFoundException
import gr.uoa.di.jete.models.Assignee
import gr.uoa.di.jete.models.AssigneeId
import gr.uoa.di.jete.repositories.AssigneeRepository
import org.spockframework.mock.IDefaultResponse
import org.spockframework.mock.ZeroOrNullResponse
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@SpringBootTest
@WebMvcTest(AssigneeController.class)
class AsigneeControllerTest extends Specification {

    MockMvc mvc

    AssigneeRepository repository

    AssigneeController controller
    def setup(){
        repository = Stub(AssigneeRepository.class)
        controller = new AssigneeController(repository)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build()
    }

    def "Getting assignees"(){
        when: "When having assignees"
        def as1 = new Assignee(1L,1L,1L,1L,1L,1L)
        def as2 = new Assignee(2, 2, 2, 2, 2, 2)
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

/*

    def "Exception is thrown when assignee doesnt exist"(){
        when: "When having certain assignee to find and not to find"
        def asIDNotFound = new AssigneeId(2L,2L,2L,2L,2L,2L)
//        Optional<Assignee> as1 = new Optional()
        and: "Stubbing Repository"
        new AssigneeId(2L,2L,2L,2L,2L,2L) >> asIDNotFound
        repository.findById(asIDNotFound) >> [IDefaultResponse: ZeroOrNullResponse.INSTANCE]
        and: "Getting to path"
        def urlNotFound = "/assignees/users/2/projects/2/sprints&epics/2&2/stories/2/tasks/2"
        MockHttpServletResponse response2 =  mvc.perform(
                get(urlNotFound)
                .accept(MediaType.ALL)
        ).andReturn().getResponse()

        then: "Ok is returned and 1 , 2 should exist"
        final AssigneeNotFoundException exception = thrown()
//        exception.message.contains("Could not find Assignee with user_id2 in project 2 in sprint and epic2&2 in story 2 and in task 2")
        1==1
    }
    */
}


