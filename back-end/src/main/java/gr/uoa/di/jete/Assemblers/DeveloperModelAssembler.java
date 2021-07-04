package gr.uoa.di.jete.Assemblers;

import gr.uoa.di.jete.controllers.DeveloperController;
import gr.uoa.di.jete.models.Developer;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DeveloperModelAssembler implements RepresentationModelAssembler<Developer, EntityModel<Developer>> {

    @NotNull
    @Override
    public EntityModel<Developer> toModel(@NotNull Developer developer) {
        return EntityModel.of(developer, //
                WebMvcLinkBuilder.linkTo(methodOn(DeveloperController.class).one(developer.getUser_id(), developer.getProject_id())).withSelfRel(),
                linkTo(methodOn(DeveloperController.class).all()).withRel("developers"));
    }
}
