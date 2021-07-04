package gr.uoa.di.jete.Assemblers;

import gr.uoa.di.jete.controllers.SprintController;
import gr.uoa.di.jete.models.Sprint;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SprintModelAssembler implements RepresentationModelAssembler<Sprint, EntityModel<Sprint>> {

    @NotNull
    @Override
    public EntityModel<Sprint> toModel(@NotNull Sprint sprint) {
        return EntityModel.of(sprint, //
                WebMvcLinkBuilder.linkTo(methodOn(SprintController.class).one(sprint.getId(), sprint.getProject_id())).withSelfRel(),
                linkTo(methodOn(SprintController.class).all()).withRel("sprints"));
    }
}
