import gr.uoa.di.jete.Assemblers.TaskModelAssembler
import gr.uoa.di.jete.controllers.TaskController
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
@WebMvcTest(TaskController.class)
class TaskControllerTest extends Specification {
    def mvc
    TaskRepository repository
    StoryRepository storyRepository
    EpicRepository epicRepository
    SprintRepository sprintRepository
    TaskController controller
    TaskModelAssembler assembler

    def setup() {
        repository = Stub()
        epicRepository = Stub()
        storyRepository = Stub()
        sprintRepository = Stub()
        assembler = Stub()
        controller = new TaskController(repository,assembler,storyRepository, epicRepository, sprintRepository)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build()
    }


    def "Get All tasks inside Repo"() {
        when: "Tasks exists"
        repository.findAll() >> {
            def proj = new Task()
            def proj2 = new Task()
            proj.setId(1L)
            proj2.setId(2L)
            return [proj, proj2]
        }
        and: "Get @ /tasks"
        def url = "/tasks"
        MockHttpServletResponse response = mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND tasks with id 1 and 2 returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        response.getContentAsString().contains("\"id\":2")
    }

    def "Get All tasks inside Project"() {
        when: "Tasks exists"
        repository.findAllByProjectAndStoryId(_ as Long,_ as Long) >> {
            def proj = new Task()
            def proj2 = new Task()
            proj.setId(1L)
            proj2.setId(2L)
            return [proj, proj2]
        }
        and: "Get @ /projects/1/stories/1/tasks"
        def url = "/projects/1/stories/1/tasks"
        MockHttpServletResponse response = mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND tasks with id 1 and 2 returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        response.getContentAsString().contains("\"id\":2")
    }

    def "Get All tasks inside Sprints"() {
        when: "Tasks exists"
        repository.findAllByProjectAndSprintAndStoryId(_ as Long,_ as Long,_ as Long) >> {
            def proj = new Task()
            def proj2 = new Task()
            proj.setId(1L)
            proj2.setId(2L)
            return [proj, proj2]
        }
        and: "Get @ /projects/1/sprints/1/stories/1/tasks"
        def url = "/projects/1/sprints/1/stories/1/tasks"
        MockHttpServletResponse response = mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND tasks with id 1 and 2 returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        response.getContentAsString().contains("\"id\":2")
    }

    def "Get All tasks inside Epics"() {
        when: "Tasks exists"
        repository.findAllByProjectAndEpicAndStoryId(_ as Long,_ as Long,_ as Long) >> {
            def proj = new Task()
            def proj2 = new Task()
            proj.setId(1L)
            proj2.setId(2L)
            return [proj, proj2]
        }
        and: "Get @ /projects/1/epics/1/stories/1/tasks"
        def url = "/projects/1/epics/1/stories/1/tasks"
        MockHttpServletResponse response = mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND tasks with id 1 and 2 returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        response.getContentAsString().contains("\"id\":2")
    }

    def "Create and Post new Task"() {
        when: "Creating task"
        def cnt = 0
        def body = new String("{\n" +
                "\"epic_id\":1,\n" +
                "\"sprint_id\":1,\n" +
                "\"project_id\":1,\n" +
                "\"story_id\":1,\n" +
                "\"title\":\"titlos\",\n" +
                "\"description\":\"desc\",\n" +
                "\"status\":1\n}")
        epicRepository.findById(_ as EpicId) >> Optional.of(new Epic())
        sprintRepository.findById(_ as SprintId) >> Optional.of(new Sprint())
        storyRepository.findById(_ as StoryId) >> Optional.of(new Story())
        repository.findMaxId() >> Optional.of(1L)
        repository.save(_ as Task) >> {
            cnt++
            def task = new Task()
            task.setId(1)
            return task
        }
        and: "Post @ /projects/1/sprints&epics/1&1/stories/1/tasks/create"
        def url = "/projects/1/sprints&epics/1&1/stories/1/tasks/create"
        MockHttpServletResponse response = mvc.perform(
                post(url).content(body).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse()
        then: "Response status is Ok AND tasks with id 1 cnt == 1"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        cnt == 1
    }


    def "Get certain Task"() {
        when: "Tasks exists"
        repository.findById(_ as TaskId) >> {
            def proj = new Task()
            proj.setId(1L)
            return Optional.of(proj)
        }
        and: "Get @ /projects/1/sprints&epics/1&1/stories/1/tasks/1"
        def url = "/projects/1/sprints&epics/1&1/stories/1/tasks/1"
        MockHttpServletResponse response = mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND task with id 1 returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")

    }

    def "Archive certain Task"() {
        when: "Tasks exists"
        def cnt = 0
        repository.archiveTask(_ as Long, _ as Long, _ as Long, _ as Long, _ as Long) >> {
            cnt++
        }
        and: "Put @ /projects/1/sprints&epics/1&1/stories/1/tasks/1/archive"
        def url = "/projects/1/sprints&epics/1&1/stories/1/tasks/1/archive"
        MockHttpServletResponse response = mvc.perform(
                put(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND cnt==1"
        response.getStatus() == 200
        cnt == 1
    }


    def "Delete certain Task"() {
        when: "Tasks exists"
        def cnt = 0
        repository.deleteAllAssigneesInTask(_ as Long, _ as Long, _ as Long, _ as Long, _ as Long) >> {
            cnt++
        }
        repository.deleteById(_ as TaskId) >> {
            cnt++
        }
        and: "Delete @ /projects/1/sprints&epics/1&1/stories/1/tasks/1/delete"
        def url = "/projects/1/sprints&epics/1&1/stories/1/tasks/1/delete"
        MockHttpServletResponse response = mvc.perform(
                delete(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND cnt==2"
        response.getStatus() == 200
        cnt == 2
    }

}
