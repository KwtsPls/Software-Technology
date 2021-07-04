package gr.uoa.di.jete.Assemblers;

import gr.uoa.di.jete.controllers.ProjectController;
import gr.uoa.di.jete.models.Project;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProjectModelAssembler implements RepresentationModelAssembler<Project, EntityModel<Project>> {

    @NotNull
    @Override
    public EntityModel<Project> toModel(@NotNull Project project) {
        return EntityModel.of(project, //
                WebMvcLinkBuilder.linkTo(methodOn(ProjectController.class).one(project.getId())).withSelfRel(),
                linkTo(methodOn(ProjectController.class).all()).withRel("projects"));
    }
}
