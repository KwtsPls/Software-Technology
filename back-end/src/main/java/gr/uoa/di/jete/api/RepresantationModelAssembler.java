package gr.uoa.di.jete.api;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;



@Component
class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user){
        return EntityModel.of(user, //
                linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }
}

@Component
class ProjectModelAssembler implements RepresentationModelAssembler<Project, EntityModel<Project>> {

    @Override
    public EntityModel<Project> toModel(Project project){
        return EntityModel.of(project, //
                linkTo(methodOn(ProjectController.class).one(project.getId())).withSelfRel(),
                linkTo(methodOn(ProjectController.class).all()).withRel("projects"));
    }
}

@Component
class DeveloperModelAssembler implements RepresentationModelAssembler<Developer, EntityModel<Developer>> {

    @Override
    public EntityModel<Developer> toModel(Developer developer){
        return EntityModel.of(developer, //
                linkTo(methodOn(DeveloperController.class).one(developer.getUserId(),developer.getProjectId())).withSelfRel(),
                linkTo(methodOn(DeveloperController.class).all()).withRel("developers"));
    }
}

@Component
class EpicModelAssembler implements RepresentationModelAssembler<Epic, EntityModel<Epic>> {

    @Override
    public EntityModel<Epic> toModel(Epic epic){
        return EntityModel.of(epic, //
                linkTo(methodOn(EpicController.class).one(epic.getId(),epic.getProjectId())).withSelfRel(),
                linkTo(methodOn(EpicController.class).all()).withRel("epics"));
    }
}

@Component
class SprintModelAssembler implements RepresentationModelAssembler<Sprint, EntityModel<Sprint>> {

    @Override
    public EntityModel<Sprint> toModel(Sprint sprint){
        return EntityModel.of(sprint, //
                linkTo(methodOn(SprintController.class).one(sprint.getId(),sprint.getProjectId())).withSelfRel(),
                linkTo(methodOn(SprintController.class).all()).withRel("sprints"));
    }
}