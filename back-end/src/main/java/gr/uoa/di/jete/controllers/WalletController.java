package gr.uoa.di.jete.controllers;


import gr.uoa.di.jete.assemblers.WalletModelAssembler;
import gr.uoa.di.jete.exceptions.UserNotFoundException;
import gr.uoa.di.jete.exceptions.WalletNotFoundException;
import gr.uoa.di.jete.models.User;
import gr.uoa.di.jete.models.Wallet;
import gr.uoa.di.jete.repositories.UserRepository;
import gr.uoa.di.jete.repositories.WalletRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

//import gr.uoa.di.jete.models.WalletId;

@CrossOrigin
@RestController
public class WalletController {

    private final WalletRepository repository;
    private final WalletModelAssembler assembler;
    private final UserRepository userRepository;

    public WalletController(WalletRepository repository, WalletModelAssembler assembler, UserRepository userRepository) {
        this.repository = repository;
        this.assembler = assembler;
        this.userRepository = userRepository;
    }

    @GetMapping("users/{username}/wallet")
    public EntityModel<Wallet> one(@PathVariable String username) {
        Wallet wallet = repository.findByUsername(username)
                .orElseThrow(()->new WalletNotFoundException(username));
        return assembler.toModel(wallet);
    }

    @PostMapping("users/{username}/wallet")
    Wallet newWallet(@PathVariable String username, @RequestBody Wallet wallet){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException(username));
        wallet.setId(user.getId());
        return repository.save(wallet);
    }

    @PostMapping("/wallets")
    Wallet updateWallet(@RequestBody Wallet wallet){
        User user = userRepository.findById(wallet.getId())
                .orElseThrow(()->new UserNotFoundException(wallet.getId()));

        return repository.save(wallet);

    }

    @GetMapping("/wallets/{id}")
    public EntityModel<Wallet> one(@PathVariable Long id){
        Wallet wallet = repository.findById((id))
                .orElseThrow(()->new WalletNotFoundException(id));
        return assembler.toModel(wallet);
    }
}

