import gr.uoa.di.jete.Assemblers.EpicModelAssembler
import gr.uoa.di.jete.controllers.EpicController
import gr.uoa.di.jete.models.Developer
import gr.uoa.di.jete.models.DeveloperId
import gr.uoa.di.jete.models.Epic
import gr.uoa.di.jete.models.EpicId
import gr.uoa.di.jete.models.Project
import gr.uoa.di.jete.repositories.DeveloperRepository
import gr.uoa.di.jete.repositories.EpicRepository
import gr.uoa.di.jete.repositories.ProjectRepository
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

@SpringBootTest
@WebMvcTest(EpicController.class)
class EpicControllerTest extends Specification{

    def mvc
    EpicRepository repository
    ProjectRepository projectRep
    DeveloperRepository devRep
    EpicModelAssembler assembler
    def controller

    void setup(){
        repository = Stub(EpicRepository.class)
        projectRep = Stub(ProjectRepository.class)
        devRep = Stub(DeveloperRepository.class)
        assembler = new EpicModelAssembler()
        controller = new EpicController(repository,assembler,projectRep,devRep)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build()
    }

    def "Get All epics"(){
        when: "Repository find all returns a list of epics"
            def epic = new Epic(1L,1L,"description","title")
            def epic2 = new Epic(2L,1L,"description","title")
            repository.findAll() >> [epic,epic2]
        and: "Get @ /epics"
            def url = "/epics"
            MockHttpServletResponse response =  mvc.perform(
                get(url)
            ).andReturn().getResponse()
        then: "Ok is Returned and project id with 1 and 2 should exist"
            response.getStatus() == 200
            response.getContentAsString().contains("\"project_id\":1")
            response.getContentAsString().contains("\"project_id\":2")
    }

    def "Check if epic is created"(){
        when: "Epic to be created"
            def cnt = 0
            def epicString = new String("{\n" +
                    "\"project_id\":\"1\",\n" +
                    "\"status\":\"1\",\n" +
                    "\"description\":\"kati\",\n" +
                    "\"title\":\"titlos\"\n}")
            projectRep.findById(_ as Long) >> Optional.of(new Project())
            repository.findMaxId() >> Optional.of(1L)
            repository.save(_ as Epic) >> {
                cnt++
                def epic = new Epic(1,1,"description","titlos")
                epic.setId(2)
                return epic
            }
        and: "Post @ /projects/epics/create"
            def url = "/projects/epics/create"
            MockHttpServletResponse response =  mvc.perform(
                post(url)
                .content(epicString).contentType(MediaType.APPLICATION_JSON)
            ).andReturn().getResponse()
        then: "Epic with id = 1 should be returned and ok"
            response.getStatus() == 200
            response.getContentAsString().contains("\"id\":2")

    }

    def "Get certain epic"(){
        when: "Get @ /projects/1/epics/1"
            def url = "/projects/1/epics/1"
            def epId = new EpicId(1,1)
            new EpicId(1,1) >> epId
            Epic epic = new Epic(1,1,"description","titlos")
            epic.setId(2)
            repository.findById(epId) >>  Optional.of(epic)

            MockHttpServletResponse response =  mvc.perform(
                    get(url)
            ).andReturn().getResponse()

        then: "Ok is Returned and project id with 1 and 2 should exist"
            response.getStatus() == 200
            response.getContentAsString().contains("\"id\":2")
    }

    def "Get all epics from a project"(){
        when: "When epics exist certain project"
            repository.findAllByProjectId(1L) >> {
                def epic1 = new Epic(1L,1L,"description","titlos")
                epic1.setId(1L)
                def epic2 = new Epic(1L,1L,"description","titlos2")
                epic2.setId(2L)
                return [epic1,epic2]
            }
        and: "Get @ /projects/1/epics"
            def url = "/projects/1/epics"
            MockHttpServletResponse response =  mvc.perform(
                get(url)
            ).andReturn().getResponse()
        then: "Ok is Returned and project id with 1 and 2 should exist"
            response.getStatus() == 200
            response.getContentAsString().contains("\"id\":1")
            response.getContentAsString().contains("\"id\":2")
            response.getContentAsString().contains("\"title\":\"titlos\"")
            response.getContentAsString().contains("\"title\":\"titlos2\"")
    }

    def "Archives a certain epic"(){
        when:"Archiving is being called"
            def cnt = 0
            repository.archiveEpic(_ as Long,_ as Long) >> {
                cnt++
            }
            repository.archiveAllStoriesInEpic(_ as Long ,_ as Long) >> {
                cnt++
            }
            repository.archiveAllTasksInEpic(_ as Long,_ as Long) >> {
                cnt++
            }
            devRep.findById(_ as DeveloperId) >> {
                def dev = new Developer(user_id,project_id,1L,1L)
                return Optional.of(dev)
            }
        and:"Put @ /projects/{project_id}/epics/{id}/archive/{user_id}"
            def url = new String("/projects/" +
                    project_id + "/epics/" + id + "/archive/" + user_id )
            MockHttpServletResponse response =  mvc.perform(
                put(url)
            ).andReturn().getResponse()
        then:"Response should be Ok and cnt == 3"
            cnt == 3
            response.getStatus() == 200
            response.getContentAsString().contains("OK")
        where:
            project_id |  id    |   user_id
                    1  |   1    |   1
                    2  |   2    |   2
    }

    def "Delete epic"(){
        when:"Delete is being called in Repo"
        def cnt = 0
        repository.deleteAllAssigneesInEpic(_ as Long,_ as Long) >> {
            cnt++
        }
        repository.deleteAllTasksInEpic(_ as Long ,_ as Long) >> {
            cnt++
        }
        repository.deleteAllStoriesInEpic(_ as Long,_ as Long) >> {
            cnt++
        }
        repository.deleteById(_ as EpicId) >> {
            cnt++
        }
        devRep.findById(_ as DeveloperId) >> {
            def dev = new Developer(user_id,project_id,1L,1L)
            return Optional.of(dev)
        }
        and:"Delete @ /projects/{project_id}/epics/{id}/delete/{user_id}"
        def url = new String("/projects/" +
                project_id + "/epics/" + id + "/delete/" + user_id )
        MockHttpServletResponse response =  mvc.perform(
                delete(url)
        ).andReturn().getResponse()
        then:"Response should be Ok and cnt == 4"
        cnt == 4
        response.getStatus() == 200
        where:
        project_id |  id    |   user_id
        1  |   1    |   1
        2  |   2    |   2
    }
}
