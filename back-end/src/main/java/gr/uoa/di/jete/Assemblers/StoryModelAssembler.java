package gr.uoa.di.jete.Assemblers;

import gr.uoa.di.jete.controllers.StoryController;
import gr.uoa.di.jete.models.Story;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StoryModelAssembler implements RepresentationModelAssembler<Story, EntityModel<Story>> {

    @NotNull
    @Override
    public EntityModel<Story> toModel(@NotNull Story story) {
        return EntityModel.of(story, //
                linkTo(methodOn(StoryController.class).one(story.getId(), story.getProject_id(), story.getSprint_id(), story.getEpic_id())).withSelfRel(),
                linkTo(methodOn(StoryController.class).all()).withRel("stories"));
    }
}
