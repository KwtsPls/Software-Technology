package gr.uoa.di.jete.Assemblers;

import gr.uoa.di.jete.controllers.EpicController;
import gr.uoa.di.jete.models.Epic;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EpicModelAssembler implements RepresentationModelAssembler<Epic, EntityModel<Epic>> {

    @NotNull
    @Override
    public EntityModel<Epic> toModel(@NotNull Epic epic) {
        return EntityModel.of(epic, //
                WebMvcLinkBuilder.linkTo(methodOn(EpicController.class).one(epic.getId(), epic.getProject_id())).withSelfRel(),
                linkTo(methodOn(EpicController.class).all()).withRel("epics"));
    }
}
