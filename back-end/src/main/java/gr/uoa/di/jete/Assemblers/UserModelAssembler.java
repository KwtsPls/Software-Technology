package gr.uoa.di.jete.Assemblers;
import gr.uoa.di.jete.controllers.*;
import gr.uoa.di.jete.models.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @NotNull
    @Override
    public EntityModel<User> toModel(@NotNull User user){
        return EntityModel.of(user, //
                WebMvcLinkBuilder.linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }
}

