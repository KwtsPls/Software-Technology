import gr.uoa.di.jete.Assemblers.ProjectModelAssembler
import gr.uoa.di.jete.controllers.ProjectController
import gr.uoa.di.jete.models.Developer
import gr.uoa.di.jete.models.DeveloperId
import gr.uoa.di.jete.models.Project
import gr.uoa.di.jete.models.Sprint
import gr.uoa.di.jete.models.SprintId
import gr.uoa.di.jete.models.User
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

@SpringBootTest
@WebMvcTest(ProjectController.class)
class ProjectControllerTest extends Specification{

    def mvc
    ProjectRepository repository
    UserRepository userRepository
    DeveloperRepository developerRepository
    SprintRepository sprintRepository
    ProjectModelAssembler assembler
    ProjectController controller

    def setup(){
        repository = Stub()
        userRepository = Stub()
        developerRepository = Stub()
        sprintRepository = Stub()
        assembler = Stub()
        controller = new ProjectController(repository,assembler,userRepository,developerRepository,sprintRepository)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build()
    }

    def "Get All projects inside Repo"(){
        when: "Projects exists"
            repository.findAll() >> {
                def proj = new Project("title","description",1L,Date.valueOf(LocalDate.now()))
                def proj2 = new Project("title2","description2",1L,Date.valueOf(LocalDate.now()))
                proj.setId(1L)
                proj2.setId(2L)
                return [proj,proj2]
            }
        and: "Get @ /projects"
            def url = "/projects"
            MockHttpServletResponse response =  mvc.perform(
                get(url)
            ).andReturn().getResponse()
        then: "Response status is Ok AND projects with id 1 and 2 returned"
            response.getStatus() == 200
            response.getContentAsString().contains("\"id\":1")
            response.getContentAsString().contains("\"id\":2")

    }


    def "Post New Project"(){
        when: "Create Project"
            def cnt = 0
            def body = new String("{\n" +
                    "\"title\":\"titlos\",\n" +
                    "\"description\":\"desc\",\n" +
                    "\"status\":1,\n" +
                    "\"date_finished\":\"2021-06-21\"\n}")
            userRepository.findById(_ as Long) >> {
                cnt++
                def user = new User()
                return Optional.of(user)
            }
            sprintRepository.findMaxId() >> Optional.of(1L)
            repository.save(_ as Project) >>{
                def proj = new Project("titlos","desc",1L,Date.valueOf("2021-06-21"))
                proj.setId(1L)
                cnt++
                return proj
            }
            developerRepository.save(_ as Developer) >>{
                cnt++
                return  Optional.of(new Developer())
            }
            sprintRepository.save(_ as Sprint) >> {
                cnt++
                return Optional.of(new Sprint())
            }
        and: "Post @ /projects/create/1"
            def url = "/projects/create/1"
            MockHttpServletResponse response =  mvc.perform(
                post(url).content(body).contentType(MediaType.APPLICATION_JSON)
            ).andReturn().getResponse()
        then: "Response status is Ok and project with id is returned and cnt == 6"
            response.getStatus() == 200
            response.getContentAsString().contains("\"id\":1")
            cnt == 6

    }

    def "Archive certain sprint"(){
        when: "Sprint exists and is archived"
        def cnt = 0
        sprintRepository.findById(_ as SprintId) >> {
            def date = Mock(Date.class)
            def date2 = Mock(Date.class)
            def sprint = new Sprint(1L,1L,"Sprint#2",1L,date,date2)
            cnt++
            return Optional.of(sprint)
        }
        developerRepository.findById(_ as DeveloperId) >> {
            def dev = new Developer(1L,1L,1L,1L)
            cnt++
            return Optional.of(dev)
        }
        sprintRepository.updateActiveSprintsInProject(_ as Long) >> {
            cnt++
        }
        sprintRepository.findSprintByStatusInProject(_ as Long, _ as Long) >>{
            cnt++
            def date = Mock(Date.class)
            def date2 = Mock(Date.class)
            def sprint = new Sprint(1L,1L,"Sprint#1",1L,date,date2)
            return Optional.of(sprint)
        }
        sprintRepository.transferAssignees(_ as Long,_ as Long) >> {cnt++}
        sprintRepository.transferStories(_ as Long,_ as Long) >> {
            cnt++
        }
        sprintRepository.transferTasks(_ as Long,_ as Long) >> {
            cnt++
        }
        sprintRepository.findMaxId() >> Optional.of(1L)
        sprintRepository.save(_ as Sprint) >> {
            cnt++
            def date = Mock(Date.class)
            def date2 = Mock(Date.class)
            def sprint = new Sprint(1L,1L,"Sprint#1",1L,date,date2)
            return sprint
        }
        and: "Put @ /projects/1/sprints/archive/1"
        def url = "/projects/1/sprints/archive/1"
        MockHttpServletResponse response =  mvc.perform(
                put(url)
        ).andReturn().getResponse()
        then: "Response status is Ok and project with id is returned and cnt == 9"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        cnt == 9

    }

    def "Get Certain project inside Repo"(){
        when: "Projects exists"
        repository.findById(_ as Long) >> {
            def proj = new Project("title","description",1L,Date.valueOf(LocalDate.now()))
            proj.setId(1L)
            return Optional.of(proj)
        }
        and: "Get @ /projects/1"
        def url = "/projects/1"
        MockHttpServletResponse response =  mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok and project with id : 1 is returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
    }

    def "Replace Project"(){
        when: "Create Project"
            def body = new String("{\n" +
                "\"title\":\"titlos\",\n" +
                "\"description\":\"desc\",\n" +
                "\"status\":1,\n" +
                "\"date_finished\":\"2021-06-21\"\n}")
            repository.findById(_ as Long) >> {
                def proj = new Project("title","description",1L,Date.valueOf(LocalDate.now()))

                proj.setId(1L)
                return Optional.of(proj)
            }
            repository.save(_ as Project) >> {
                def proj = new Project("titlos","desc",1L,Date.valueOf(LocalDate.now()))
                proj.setId(1L)
                return proj
            }
        and: "Put @ /projects/1"
            def url = "/projects/1"
            MockHttpServletResponse response =  mvc.perform(
                put(url).content(body).contentType(MediaType.APPLICATION_JSON)
            ).andReturn().getResponse()
        then: "Response status is Ok and project with id is returned and project is changed "
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        response.getContentAsString().contains("\"title\":\"titlos\"")
        response.getContentAsString().contains("\"description\":\"desc\"")
    }

    def "Get Users projects inside Repo"(){
        when: "Projects exists"
        repository.findAllByUserId(_ as Long) >> {
            def proj = new Project("title","description",1L,Date.valueOf(LocalDate.now()))
            def proj2 = new Project("title2","description2",1L,Date.valueOf(LocalDate.now()))
            proj.setId(1L)
            proj2.setId(2L)
            return [proj,proj2]
        }
        and: "Get @ /users/1/projects"
            def url = "/users/1/projects"
            MockHttpServletResponse response =  mvc.perform(
                get(url)
            ).andReturn().getResponse()
        then: "Response status is Ok and payment with id is returned and received 1.33"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        response.getContentAsString().contains("\"id\":2")

    }


    def "Check if user in project"(){
        when: "Projects exists"
            repository.findUserByUsernameInProject(1,"test1") >> {
                User user = new User()
                user.setId(1)
                return Optional.of(user)
            }
            repository.findUserByUsernameInProject(1,"test2") >> Optional.empty()

        and: "Get @ /projects/1/user=test1"
            def url = "/projects/1/user=test1"
            MockHttpServletResponse response =  mvc.perform(
                get(url)
            ).andReturn().getResponse()
        and: "Get @ /projects/1/user=test2"
            def urlNotFound = "/projects/1/user=test2"
            MockHttpServletResponse responseNotFound =  mvc.perform(
                get(urlNotFound)
            ).andReturn().getResponse()
        then: "Response status is Ok and YES returned"
            response.getStatus() == 200
            response.getContentAsString().contains("YES")
        then: "Response status is Ok and NO returned"
            responseNotFound.getStatus() == 200
            responseNotFound.getContentAsString().contains("NO")
    }


    def "Archive certain project"(){
        when: "Project exists and is archived"
        def cnt = 0
        developerRepository.findById(_ as DeveloperId) >> {
            def dev = new Developer(1L,1L,1L,1L)
            cnt++
            return Optional.of(dev)
        }
        repository.findById(_ as Long) >> {
            cnt++
            def proj = new Project("title","description",1L,Date.valueOf(LocalDate.now()))
            proj.setId(1L)
            return Optional.of(proj)
        }
        repository.archiveAllTasksInProject(_ as Long) >> {
            cnt++
        }
        repository.archiveAllStoriesInProject(_ as Long) >> {
            cnt++
        }
        repository.archiveAllSprintsInProject(_ as Long) >> {
            cnt++
        }
        repository.archiveAllEpicsInProject(_ as Long) >> {
            cnt++
        }
        repository.setStatusToArchived(_ as Long,_ as Date) >> {
            cnt++
        }
        and: "Put @ /projects/1/archive/1"
        def url = "/projects/1/archive/1"
            MockHttpServletResponse response =  mvc.perform(
                put(url)
            ).andReturn().getResponse()
        then: "Response status is Ok and cnt == 7"
        response.getStatus() == 200
        response.getContentAsString().contains("OK")
        cnt == 7
    }

    def "Delete certain project"(){
        when: "Delete Project"
        def cnt = 0
        developerRepository.findById(_ as DeveloperId) >> {
            def dev = new Developer(1L,1L,1L,1L)
            cnt++
            return Optional.of(dev)
        }
        repository.findById(_ as Long) >> {
            cnt++
            def proj = new Project("title","description",1L,Date.valueOf(LocalDate.now()))
            proj.setId(1L)
            return Optional.of(proj)
        }
        repository.deleteAllDevelopersInProject(_ as Long) >> {
            cnt++
        }
        repository.deleteAllAssigneesInProject(_ as Long) >> {
            cnt++
        }
        repository.deleteAllTasksInProject(_ as Long) >> {
            cnt++
        }
        repository.deleteAllStoriesInProject(_ as Long) >> {
            cnt++
        }
        repository.deleteAllSprintsInProject(_ as Long) >> {
            cnt++
        }
        repository.deleteAllEpicsInProject(_ as Long) >> {
            cnt++
        }
        repository.deleteById(_ as Long) >> {
            cnt++
        }
        and: "Delete @ /projects/1/delete/1"
            def url = "/projects/1/delete/1"
            MockHttpServletResponse response =  mvc.perform(
            delete(url)
            ).andReturn().getResponse()
        then: "Response status is Ok and cnt == 9 "
            response.getStatus() == 200
            cnt == 9
    }

}
