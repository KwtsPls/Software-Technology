package gr.uoa.di.jete.models;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Wallet {

    private @Id Long id;

    private String card1;
    private String card2;
    private String card3;

    private Date subscription_starts;
    private Date subscription_ends;

    public Wallet(){}

    public Wallet(String card1){
        this.card1 = card1;
    }

    public Wallet(Long id, String card1, Date subscription_starts, Date subscription_ends) {
        this.id = id;
//        this.user_id = user_id;
        this.card1 = card1;
        this.card2 = null;
        this.card3 = null;
        this.subscription_starts = subscription_starts;
        this.subscription_ends = subscription_ends;
    }

    public Wallet(Long id, Date subscription_starts, Date subscription_ends) {
        this.id = id;
//        this.user_id = user_id;
        this.card1 = null;
        this.card2 = null;
        this.card3 = null;
        this.subscription_starts = subscription_starts;
        this.subscription_ends = subscription_ends;
    }

//    public Long getUserId() {
//        return user_id;
//    }

    public Long getId() {
        return id;
    }

//    public void setUserId(Long id){
//        this.user_id = id;
//    }
    public String getCard1() {
        return card1;
    }

    public void setCard1(String card1) {
        this.card1 = card1;
    }

    public String getCard2() {
        return card2;
    }

    public void setCard2(String card2) {
        this.card2 = card2;
    }

    public String getCard3() {
        return card3;
    }

    public void setCard3(String card3) {
        this.card3 = card3;
    }

    public Date getSubscription_starts() {
        return subscription_starts;
    }

    public void setSubscription_starts(Date subscription_starts) {
        this.subscription_starts = subscription_starts;
    }

    public Date getSubscription_ends() {
        return subscription_ends;
    }

    public void setSubscription_ends(Date subscription_ends) {
        this.subscription_ends = subscription_ends;
    }

    public void insertCard(String cardName){
        if(card1 == null)
            card1 = cardName;
        else if(card2 == null)
            card2 = cardName;
        else if(card3 == null)
            card3 = cardName;
        else
            throw new CardCannotBeReplacedException();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(id, wallet.id) && Objects.equals(card1, wallet.card1) && Objects.equals(card2, wallet.card2) && Objects.equals(card3, wallet.card3) && Objects.equals(subscription_starts, wallet.subscription_starts) && Objects.equals(subscription_ends, wallet.subscription_ends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, card1, card2, card3, subscription_starts, subscription_ends);
    }


    public void setId(Long id) {
        this.id = id;
    }
}



class CardCannotBeReplacedException extends RuntimeException{
    public CardCannotBeReplacedException() {
        super("Cannot Replace card with pls delete a previous card!");
    }

}
@ControllerAdvice
class CardCannotBeReplacedAdvice{
    @ResponseBody
    @ExceptionHandler(CardCannotBeReplacedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String cardCannotBeReplacedAdvice(CardCannotBeReplacedException ex){
        return ex.getMessage();
    }
}