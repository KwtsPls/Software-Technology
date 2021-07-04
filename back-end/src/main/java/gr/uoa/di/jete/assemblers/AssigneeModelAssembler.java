package gr.uoa.di.jete.assemblers;

import gr.uoa.di.jete.controllers.AssigneeController;
import gr.uoa.di.jete.models.Assignee;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public
class AssigneeModelAssembler implements RepresentationModelAssembler<Assignee, EntityModel<Assignee>> {

    @NotNull
    @Override
    public EntityModel<Assignee> toModel(@NotNull Assignee assignee) {
        return EntityModel.of(assignee, //
                linkTo(methodOn(AssigneeController.class).one(assignee.getUser_id(), assignee.getEpic_id(), assignee.getSprint_id(), assignee.getProject_id(), assignee.getStory_id(), assignee.getTask_id())).withSelfRel(),
                linkTo(methodOn(AssigneeController.class).all()).withRel("assignees"));
    }
}
