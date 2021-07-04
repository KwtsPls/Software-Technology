package gr.uoa.di.jete.assemblers;

import gr.uoa.di.jete.controllers.WalletController;
import gr.uoa.di.jete.models.Wallet;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class WalletModelAssembler implements RepresentationModelAssembler<Wallet, EntityModel<Wallet>> {

    @NotNull
    @Override
    public EntityModel<Wallet> toModel(@NotNull Wallet wallet) {
        return EntityModel.of(wallet, //
                linkTo(methodOn(WalletController.class).one(wallet.getId())).withSelfRel());
    }
}
