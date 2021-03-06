import gr.uoa.di.jete.assemblers.DeveloperModelAssembler
import gr.uoa.di.jete.controllers.DeveloperController
import gr.uoa.di.jete.models.*
import gr.uoa.di.jete.repositories.DeveloperRepository
import gr.uoa.di.jete.repositories.ProjectRepository
import gr.uoa.di.jete.repositories.UserRepository
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import javax.persistence.Tuple

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@SpringBootTest
@WebMvcTest(DeveloperController.class)
class DeveloperControllerTest extends Specification {

    def mvc
    DeveloperRepository repository
    UserRepository userRepository
    ProjectRepository projectRep
    DeveloperController controller
    DeveloperModelAssembler assembler

    void setup(){
        repository = Stub(DeveloperRepository.class)
        userRepository = Stub(UserRepository.class)
        projectRep = Stub(ProjectRepository.class)
        assembler = new DeveloperModelAssembler()
        controller = new DeveloperController(repository,assembler,userRepository,projectRep)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build()
    }

    def "Get All developers"(){
        when: "Having Developers"
        def dev1 = new Developer(1L,1L,1L,1)
        def dev2 = new Developer(2L,1L,1L,1)
        and: "Stubbing Repository"
        repository.findAll() >> [dev1,dev2]
        and: "Getting /developers/"
        def url = "/developers/"
        MockHttpServletResponse response =  mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Ok is returned and 1 , 2 should exist"
        response.getStatus() == 200
        response.getContentAsString().contains("\"user_id\":1")
        response.getContentAsString().contains("\"user_id\":2")

    }

    def "Post to Developers"(){
        when: "User And Project exists and stubing repository save"
        userRepository.findById(1) >>Optional.of(new User())
        projectRep.findById(1) >> Optional.of(new Project())
        def cnt = 0
        repository.save(_ as Developer) >>{
            cnt = cnt+1
            return new Developer(1,1,1,1)
        }
        and: "Creating new Developer"
        def body = new String("{\n" +
            "\"user_id\": \"1\",\n" +
            "\"project_id\": \"1\",\n" +
            "\"role\": \"1\"}")
        and: "Posting new Developer and Getting /developers/"
        def url = "/developers/"
        MockHttpServletResponse response =  mvc.perform(
                post(url)
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse()
        then:"Ok is returned and cnt is 1"
        cnt == 1
        response.getStatus() == 200
    }

    def "Get Developer with given user id and project id"(){
        when: "Developer with given ids exists"
        DeveloperId devId = new DeveloperId(1,1)
        new DeveloperId(1,1) >> devId
        Developer dev = new Developer(1,1,1,1)
        repository.findById(devId) >> Optional.of(dev)
        and: "Getting this developer"
        def url = "/developers/users/1/projects/1"
        MockHttpServletResponse response =  mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Ok is returned and 1"
        response.getStatus() == 200
        response.getContentAsString().contains("\"user_id\":1")
        response.getContentAsString().contains("\"project_id\":1")
    }

    def "Replace developer with put"(){
        when:"Developer with given ids exists"
        DeveloperId devId = new DeveloperId(1,1)
        new DeveloperId(1,1) >> devId
        Developer dev = new Developer(1,1,1,1)
        repository.findById(devId) >> Optional.of(dev)
        def body = new String("{\n" +
                "\"user_id\": \"1\",\n" +
                "\"project_id\": \"1\",\n" +
                "\"role\": \"1\"}")
        and:"When found then it should be save to repo"
        def cnt = 0
        repository.save(_ as Developer) >> {
            cnt++
            return dev
        }
        and:"Post this developer"
        def url = "/developers/users/1/projects/1"
        MockHttpServletResponse response =  mvc.perform(
                put(url)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse()
        then: "Ok is returned and 1"
        response.getStatus() == 200
        cnt == 1
    }

    def "Accept Project Invitation"(){
        when: "Developer accepted true returns 1 for user 1 and project 1"
            repository.setDeveloperAcceptedTrue(1,1) >> 1
        and: "Developer accepted true return 0 for user 2 and project 1"
            repository.setDeveloperAcceptedTrue(2,1) >> 0
        and: "Put to url for accepting project invitations"
            def url = "/developers/users/1/projects/1/accept"
            MockHttpServletResponse response =  mvc.perform(
                put(url)
        ).andReturn().getResponse()
            def urlNotAccepted = "/developers/users/2/projects/1/accept"
            MockHttpServletResponse responseNotAccepted =  mvc.perform(
                put(urlNotAccepted)
        ).andReturn().getResponse()
        then: "Http ok is returned and response should be OK and ERROR"
            response.getStatus() == 200
            responseNotAccepted.getStatus() == 200
            response.getContentAsString().contains("OK")
            responseNotAccepted.getContentAsString().contains("ERROR")
    }

    def "Get All devs on working on a certain project"() {
        when: "Developer infos exist on certain project"
            def devInfo = new DeveloperInfo(1L,"test1",1,1)
            def devInfo2 = new DeveloperInfo(2L,"test2",1,1)
            repository.findDeveloperInfoInProject(1L) >> [devInfo,devInfo2]
        and: "Getting @ /developers/projects/1"
            def url = "/developers/projects/1"
            MockHttpServletResponse response =  mvc.perform(
                get(url)
            ).andReturn().getResponse()
        then: "Ok should be returned and test1 and test2 should be contained in the response message"
            response.getStatus() == 200
            response.getContentAsString().contains("test1")
            response.getContentAsString().contains("test2")

    }

    def "Get Notifications Number"() {
        when: "Stubbing Repository"

        repository.getProjectsNotificationNumber(_ as Long)>>
                {
                    return [1L,2L]
                }
        and: "Getting @ /developers/users/1/notifications"
        def url = "/developers/users/1/notifications"
        MockHttpServletResponse response =  mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Ok should be returned and test1 and test2 should be contained in the response message"
            response.getStatus() == 200
            response.getContentAsString().contains("1")
            response.getContentAsString().contains("2")

    }

    def "Get Notifications"() {
        when: "Stubbing Repository"
        Tuple tu = Stub()
        Tuple tu2 = Stub()
        repository.findProjectInvitations(_ as Long) >> {
            return [tu,tu2]
        }
        tu.get(0) >> 1L
        tu.get(1) >> "kati"
        tu.get(2) >> "kati"

        tu2.get(0) >> 2L
        tu2.get(1) >> "kati2"
        tu2.get(2) >> "kati2"

        and: "Getting @ /developers/users/1/invitations"
        def url = "/developers/users/1/invitations"
        MockHttpServletResponse response =  mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Ok should be returned and id 1 and 2 shoulb be contained"
        response.getStatus() == 200
        response.getContentAsString().contains("\"project_id\":1")
        response.getContentAsString().contains("\"project_id\":2")
        response.getContentAsString().contains("\"title\":\"kati\"")
        response.getContentAsString().contains("\"title\":\"kati2\"")

    }

    def "Delete Developers from a certain project"(){
        when:"Delete @ /developers/users/{user_id}/projects/{project_id}"
        def dev = new DeveloperId(user_id, project_id)
        new DeveloperId(_ as Long, _ as Long) >>  dev
        def cnt = 0
        repository.deleteById(dev) >> {
            cnt++
        }
        def url = "/developers/users/" + user_id + "/projects/" + project_id
        MockHttpServletResponse response =  mvc.perform(
                delete(url)
        ).andReturn().getResponse()

        then: "Check if developer was deleted"
            cnt == 1
            response.getStatus() == 200

        where:
            user_id | project_id
            1       |   1
            2       |   2
    }


}
