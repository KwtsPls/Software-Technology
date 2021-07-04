package gr.uoa.di.jete.controllers;

import gr.uoa.di.jete.exceptions.PaymentsNotFoundException;
import gr.uoa.di.jete.exceptions.UserNotFoundException;
import gr.uoa.di.jete.models.Payments;
import gr.uoa.di.jete.models.User;
import gr.uoa.di.jete.repositories.PaymentsRepository;
import gr.uoa.di.jete.repositories.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
public class PaymentsController {
    private final PaymentsRepository repository;
    private final PaymentsModelAssembler assembler;
    private final UserRepository userRepository;



    public PaymentsController(PaymentsRepository repository, PaymentsModelAssembler assembler, UserRepository userRepository) {
        this.repository = repository;
        this.assembler = assembler;
        this.userRepository = userRepository;
    }

    public PaymentsController(PaymentsRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.assembler = new PaymentsModelAssembler();
        this.userRepository = userRepository;
    }

    @GetMapping("/payments/{id}/{userId}")
    public EntityModel<Payments> one(@PathVariable Long id, @PathVariable Long userId) {
        Payments payments = repository.findById(id,userId)
                .orElseThrow(()-> new PaymentsNotFoundException(id,userId));
        return assembler.toModel(payments);
    }


    @GetMapping("/payments")
    CollectionModel<EntityModel<Payments>> all() {
        List<EntityModel<Payments>> paymentsList = repository.findAll().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(paymentsList,
                linkTo(methodOn(PaymentsController.class).all()).withSelfRel());
    }

    @GetMapping("/users/{username}/payments")
    List<Payments> getAllUsersPayments(@PathVariable String username){
        return repository.findAllByUsername(username)
                .orElseThrow(()->new UserNotFoundException(username));

    }

    @PostMapping("/payments/{username}")
    Payments newPayment(@PathVariable String username, @RequestBody Payments payments){
        User user = userRepository.findByUsername(username)
            .orElseThrow(()->new UserNotFoundException(username));
        payments.setUserId(user.getId());
        return repository.save(payments);
    }
}
