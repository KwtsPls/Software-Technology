package gr.uoa.di.jete.exceptions;

//import gr.uoa.di.jete.models.WalletId;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(Long id) {
        super("Could not find wallet with id: " + id);
    }
    public WalletNotFoundException(String username){
        super("Could not find wallet with username: " + username);
    }
}
