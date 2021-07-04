package gr.uoa.di.jete.Assemblers;

import gr.uoa.di.jete.controllers.PaymentsController;
import gr.uoa.di.jete.models.Payments;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaymentsModelAssembler implements RepresentationModelAssembler<Payments, EntityModel<Payments>> {

    @NotNull
    @Override
    public EntityModel<Payments> toModel(@NotNull Payments payments) {
        return EntityModel.of(payments, //
                linkTo(methodOn(PaymentsController.class).one(payments.getId(), payments.getUserId())).withSelfRel(),
                linkTo(methodOn(PaymentsController.class).all()).withRel("payments"));
    }
}
