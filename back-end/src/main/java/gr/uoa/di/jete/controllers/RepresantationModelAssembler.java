package gr.uoa.di.jete.controllers;
import gr.uoa.di.jete.models.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@Component
class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @NotNull
    @Override
    public EntityModel<User> toModel(@NotNull User user){
        return EntityModel.of(user, //
                linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }
}

@Component
class ProjectModelAssembler implements RepresentationModelAssembler<Project, EntityModel<Project>> {

    @NotNull
    @Override
    public EntityModel<Project> toModel(@NotNull Project project){
        return EntityModel.of(project, //
                linkTo(methodOn(ProjectController.class).one(project.getId())).withSelfRel(),
                linkTo(methodOn(ProjectController.class).all()).withRel("projects"));
    }
}

@Component
class DeveloperModelAssembler implements RepresentationModelAssembler<Developer, EntityModel<Developer>> {

    @NotNull
    @Override
    public EntityModel<Developer> toModel(@NotNull Developer developer){
        return EntityModel.of(developer, //
                linkTo(methodOn(DeveloperController.class).one(developer.getUser_id(),developer.getProject_id())).withSelfRel(),
                linkTo(methodOn(DeveloperController.class).all()).withRel("developers"));
    }
}

@Component
class EpicModelAssembler implements RepresentationModelAssembler<Epic, EntityModel<Epic>> {

    @NotNull
    @Override
    public EntityModel<Epic> toModel(@NotNull Epic epic){
        return EntityModel.of(epic, //
                linkTo(methodOn(EpicController.class).one(epic.getId(),epic.getProject_id())).withSelfRel(),
                linkTo(methodOn(EpicController.class).all()).withRel("epics"));
    }
}

@Component
class SprintModelAssembler implements RepresentationModelAssembler<Sprint, EntityModel<Sprint>> {

    @NotNull
    @Override
    public EntityModel<Sprint> toModel(@NotNull Sprint sprint) {
        return EntityModel.of(sprint, //
                linkTo(methodOn(SprintController.class).one(sprint.getId(), sprint.getProjectId())).withSelfRel(),
                linkTo(methodOn(SprintController.class).all()).withRel("sprints"));
    }
}

@Component
class StoryModelAssembler implements RepresentationModelAssembler<Story, EntityModel<Story>> {

    @NotNull
    @Override
    public EntityModel<Story> toModel(@NotNull Story story){
        return EntityModel.of(story, //
                linkTo(methodOn(StoryController.class).one(story.getId(),story.getProject_id(),story.getSprint_id(),story.getEpic_id())).withSelfRel(),
                linkTo(methodOn(StoryController.class).all()).withRel("stories"));
    }
}

@Component
class TaskModelAssembler implements RepresentationModelAssembler<Task, EntityModel<Task>> {

    @NotNull
    @Override
    public EntityModel<Task> toModel(@NotNull Task task){
        return EntityModel.of(task, //
                linkTo(methodOn(TaskController.class).one(task.getId(),task.getProject_id(),task.getSprint_id(),task.getEpic_id(),task.getStory_id())).withSelfRel(),
                linkTo(methodOn(TaskController.class).all()).withRel("tasks"));
    }
}

@Component
class WalletModelAssembler implements RepresentationModelAssembler<Wallet, EntityModel<Wallet>> {

    @NotNull
    @Override
    public EntityModel<Wallet> toModel(@NotNull Wallet wallet) {
        return EntityModel.of(wallet, //
                linkTo(methodOn(WalletController.class).one(wallet.getId())).withSelfRel());
    }
}

@Component
class PaymentsModelAssembler implements RepresentationModelAssembler<Payments, EntityModel<Payments>> {

    @NotNull
    @Override
    public EntityModel<Payments> toModel(@NotNull Payments payments) {
        return EntityModel.of(payments, //
                linkTo(methodOn(PaymentsController.class).one(payments.getId(),payments.getUserId())).withSelfRel(),
                linkTo(methodOn(PaymentsController.class).all()).withRel("payments"));
    }
}
