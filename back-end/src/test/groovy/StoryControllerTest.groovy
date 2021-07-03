import gr.uoa.di.jete.controllers.StoryController
import gr.uoa.di.jete.models.*
import gr.uoa.di.jete.repositories.*
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@SpringBootTest
@WebMvcTest(StoryController.class)
class StoryControllerTest extends Specification{

    def mvc
    StoryRepository repository
    EpicRepository epicRepository
    SprintRepository sprintRepository
    ProjectRepository projectRepository
    DeveloperRepository developerRepository

    StoryController controller

    def setup(){
        repository = Stub()
        epicRepository = Stub()
        developerRepository = Stub()
        sprintRepository = Stub()
        projectRepository = Stub()
        controller = new StoryController(repository,epicRepository,sprintRepository,projectRepository,developerRepository)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build()
    }

    def "Get All stories inside Repo"(){
        when: "Stories exists"
        repository.findAll() >> {
            def proj = new Story()
            def proj2 = new Story()
            proj.setId(1L)
            proj2.setId(2L)
            return [proj,proj2]
        }
        and: "Get @ /stories"
        def url = "/stories"
        MockHttpServletResponse response =  mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND stories with id 1 and 2 returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        response.getContentAsString().contains("\"id\":2")

    }

    def "Get All stories inside a Sprint"(){
        when: "Stories exists"
        repository.findAllByProjectAndSprintId(_ as Long,_ as Long) >> {
            def proj = new Story()
            def proj2 = new Story()
            proj.setId(1L)
            proj2.setId(2L)
            return [proj,proj2]
        }
        and: "Get @ /projects/1/sprints/1/stories"
        def url = "/projects/1/sprints/1/stories"
        MockHttpServletResponse response =  mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND stories with id 1 and 2 returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        response.getContentAsString().contains("\"id\":2")

    }

    def "Get All stories inside an Epic"(){
        when: "Stories exists"
        repository.findAllByProjectAndEpicId(_ as Long,_ as Long) >> {
            def proj = new Story()
            def proj2 = new Story()
            proj.setId(1L)
            proj2.setId(2L)
            return [proj,proj2]
        }
        and: "Get @ /projects/1/epics/1/stories"
        def url = "/projects/1/epics/1/stories"
        MockHttpServletResponse response =  mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND stories with id 1 and 2 returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        response.getContentAsString().contains("\"id\":2")
    }


    def "Post New Story"(){
        when: "Create Story"
            def cnt = 0
            def body = new String("{\n" +
                    "\"id\":1,\n" +
                    "\"project_id\":1,\n" +
                    "\"title\":\"titlos\",\n" +
                    "\"status\":1,\n" +
                    "\"date_from\":\"2021-06-12\",\n" +
                    "\"date_to\":\"2021-07-12\"\n}")
            projectRepository.findById(_ as Long) >> Optional.of(new Project())
            epicRepository.findById(_ as EpicId) >> Optional.of(new Epic())
            sprintRepository.findById(_ as SprintId) >> Optional.of(new Sprint())
            repository.save(_ as Story) >> {
                cnt++
                def story = new Story()
                story.setId(1)
                return story
            }
        and: "Post @ /projects/1/sprints&epics/1&1/stories/create"
            def url = "/projects/1/sprints&epics/1&1/stories/create"
            MockHttpServletResponse response =  mvc.perform(
                post(url).content(body).contentType(MediaType.APPLICATION_JSON)
            ).andReturn().getResponse()
        then: "Response status is Ok and project with id is returned and cnt == 6"
            response.getStatus() == 200
            response.getContentAsString().contains("\"id\":1")
            cnt == 1

    }

    def "Get a certain Sprint"(){
        when: "Story exists"
            repository.findById(_ as StoryId) >> {
                def proj = new Story()
                proj.setId(1L)
                return Optional.of(proj)
            }
        and: "Get @ /projects/1/sprints&epics/1&1/stories/1"
            def url = "/projects/1/sprints&epics/1&1/stories/1"
            MockHttpServletResponse response =  mvc.perform(
                get(url)
            ).andReturn().getResponse()
        then: "Response status is Ok AND stories with id 1 and 2 returned"
            response.getStatus() == 200
            response.getContentAsString().contains("\"id\":1")
    }

    def "Archive a certain Sprint"(){
        when: "Story exists"
        def cnt = 0
        developerRepository.findById(_ as DeveloperId) >> {
            def proj = new Developer()
            proj.setRole(1L)
            return Optional.of(proj)
        }
        repository.archiveAllTasksInStory(_ as Long,_ as Long,_ as Long,_ as Long) >> {
            cnt++
        }
        repository.archiveStory(_ as Long,_ as Long,_ as Long,_ as Long) >> {
            cnt++
        }
        and: "Put @ /projects/1/sprints&epics/1&1/stories/1/archive/1"
        def url = "/projects/1/sprints&epics/1&1/stories/1/archive/1"
        MockHttpServletResponse response =  mvc.perform(
                put(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND stories with id 1 and 2 returned"
        response.getStatus() == 200
        cnt == 2
    }


    def "Delete a certain Sprint"(){
        when: "Story exists"
        def cnt = 0
        developerRepository.findById(_ as DeveloperId) >> {
            def proj = new Developer()
            proj.setRole(1L)
            return Optional.of(proj)
        }
        repository.deleteAllAssigneesInStory(_ as Long,_ as Long,_ as Long,_ as Long) >> {
            cnt++
        }
        repository.deleteAllTasksInStory(_ as Long,_ as Long,_ as Long,_ as Long) >> {
            cnt++
        }
        repository.deleteById(_ as StoryId) >> {
            cnt++
        }
        and: "Delete @ /projects/1/sprints&epics/1&1/stories/1/delete/1"
        def url = "/projects/1/sprints&epics/1&1/stories/1/delete/1"
        MockHttpServletResponse response =  mvc.perform(
                delete(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND stories with id 1 and 2 returned"
        response.getStatus() == 200
        cnt == 3
    }


}
