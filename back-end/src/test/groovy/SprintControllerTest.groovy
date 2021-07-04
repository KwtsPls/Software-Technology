import gr.uoa.di.jete.controllers.ProjectController
import gr.uoa.di.jete.controllers.SprintController
import gr.uoa.di.jete.exceptions.SprintNotFoundException
import gr.uoa.di.jete.models.Developer
import gr.uoa.di.jete.models.DeveloperId
import gr.uoa.di.jete.models.Project
import gr.uoa.di.jete.models.Sprint
import gr.uoa.di.jete.models.SprintId
import gr.uoa.di.jete.repositories.DeveloperRepository
import gr.uoa.di.jete.repositories.ProjectRepository
import gr.uoa.di.jete.repositories.SprintRepository
import gr.uoa.di.jete.repositories.UserRepository
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.sql.Date
import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest
@WebMvcTest(SprintController.class)
class SprintControllerTest extends Specification {
    def mvc
    SprintRepository repository
    ProjectRepository projectRepository
    DeveloperRepository developerRepository

    SprintController controller

    def setup() {
        repository = Stub()
        projectRepository = Stub()
        developerRepository = Stub()
        controller = new SprintController(repository, projectRepository, developerRepository)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build()
    }

    def "Get all sprints"(){
        when: "Sprints exists"
        repository.findAll() >> {
            def sprint = new Sprint()
            def sprint2 = new Sprint()
            sprint.setId(1L)
            sprint2.setId(2L)
            return [sprint,sprint2]
        }
        and: "Get @ /sprints"
        def url = "/sprints"
        MockHttpServletResponse response =  mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND sprints with id 1 and 2 returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        response.getContentAsString().contains("\"id\":2")
    }

    def "Post new sprint"(){
        when: "Creating new sprint"
        def sprint = new String("{\n" +
                "\"id\":1,\n" +
                "\"project_id\":1,\n" +
                "\"title\":\"sprint#1\",\n" +
                "\"status\":1,\n" +
                "\"date_from\":\"2021-06-21\",\n" +
                "\"date_to\":\"2021-07-21\"}")
            projectRepository.findById(1) >> Optional.of(new Project())
            repository.findMaxId() >> Optional.of(1L)
            repository.save(_ as Sprint) >> new Sprint(1L,1L,"sprint#1",1L,Mock(Date),Mock(Date))
        and: "Post @ /projects/sprints/create"
        def url = "/projects/sprints/create"
        MockHttpServletResponse response =  mvc.perform(
                post(url).content(sprint).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse()
        then: "Response status is Ok AND sprints with id 1 is returned"
            response.getStatus() == 200
            response.getContentAsString().contains("\"id\":1")

    }

    def "Get  sprints in project"(){
        when: "Sprints exists"
        repository.findSprintsInProject(_ as Long) >> {
            def sprint = new Sprint()
            def sprint2 = new Sprint()
            sprint.setId(1L)
            sprint2.setId(2L)
            return [sprint,sprint2]
        }
        and: "Get @ /projects/1/sprints"
            def url = "/projects/1/sprints"
            MockHttpServletResponse response =  mvc.perform(
                get(url)
            ).andReturn().getResponse()
        then: "Response status is Ok AND sprints with id 1 and 2 returned"
            response.getStatus() == 200
            response.getContentAsString().contains("\"id\":1")
            response.getContentAsString().contains("\"id\":2")
    }

    def "Get Active sprints in project"(){
        when: "Sprints exists"
        repository.findActiveSprintsInProject(_ as Long) >> {
            def sprint = new Sprint()
            def sprint2 = new Sprint()
            sprint.setId(1L)
            sprint2.setId(2L)
            return [sprint,sprint2]
        }
        and: "Get @ /projects/1/sprints/active"
        def url = "/projects/1/sprints/active"
        MockHttpServletResponse response =  mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND sprints with id 1 and 2 returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        response.getContentAsString().contains("\"id\":2")
    }

    def "Get Archived Sprints in Project"(){
        when: "Sprints exists"
        repository.findArchivedSprintsInProject(_ as Long) >> {
            def sprint = new Sprint()
            sprint.setId(1L)
            def sprint2 = new Sprint()
            sprint2.setId(2L)

            return [sprint,sprint2]
        }
        and: "Get @ /projects/1/sprints/archived"
        def url = "/projects/1/sprints/archived"
        MockHttpServletResponse response =  mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND sprints with id 1 and 2 returned"
            response.getStatus() == 200
            response.getContentAsString().contains("\"id\":1")
            response.getContentAsString().contains("\"id\":2")

    }

    def "Get certain Sprint"(){
        when: "Sprints exists"
            repository.findById(_ as SprintId) >> {
                def sprint = new Sprint()
                sprint.setId(1L)
                return Optional.of(sprint)
        }
        and: "Get @ /projects/1/sprints/1"
        def url = "/projects/1/sprints/1"
        MockHttpServletResponse response =  mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND sprints with id 1 and 2 returned"
            response.getStatus() == 200
            response.getContentAsString().contains("\"id\":1")
    }

    def "Delete certain Sprint"(){
        when: "Delete Sprint"
            def cnt = 0
            repository.findById(_ as SprintId) >> {
                def dev = new Sprint()
                dev.setStatus(0L)
                dev.setId(1L)
                cnt++
                return Optional.of(dev)
            }
            developerRepository.findById(_ as DeveloperId) >> {
                cnt++
                def dev = new Developer()
                dev.setRole(1L)
                return Optional.of(dev)
            }
            repository.deleteAllAssigneesInSprint(_ as Long,_ as Long) >> {
                cnt++
            }
            repository.deleteAllTasksInSprint(_ as Long,_ as Long) >> {
                cnt++
            }
            repository.deleteAllTasksInSprint(_ as Long,_ as Long) >> {
                cnt++
            }
            repository.deleteById(_ as SprintId) >> {
                cnt++
            }
        and: "Delete @ /projects/1/sprints/1/delete/1"
            def url = "/projects/1/sprints/1/delete/1"
            MockHttpServletResponse response =  mvc.perform(
                delete(url)
            ).andReturn().getResponse()
        then: "Response status is Ok and cnt == 9 "
            response.getStatus() == 200
            cnt == 5
    }


}

