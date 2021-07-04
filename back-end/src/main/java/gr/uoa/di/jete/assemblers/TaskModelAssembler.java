package gr.uoa.di.jete.assemblers;

import gr.uoa.di.jete.controllers.TaskController;
import gr.uoa.di.jete.models.Task;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TaskModelAssembler implements RepresentationModelAssembler<Task, EntityModel<Task>> {

    @NotNull
    @Override
    public EntityModel<Task> toModel(@NotNull Task task) {
        return EntityModel.of(task, //
                linkTo(methodOn(TaskController.class).one(task.getId(), task.getProject_id(), task.getSprint_id(), task.getEpic_id(), task.getStory_id())).withSelfRel(),
                linkTo(methodOn(TaskController.class).all()).withRel("tasks"));
    }
}
