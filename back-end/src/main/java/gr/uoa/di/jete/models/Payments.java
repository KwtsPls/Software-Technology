package gr.uoa.di.jete.models;


import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;
    private Date received_date;
    private float received;

    public Payments() {
    }

    public Payments(Date received_date, float received) {
        this.received_date = received_date;
        this.received = received;
    }

    public Payments(Long id, Long user_id, Date received_date, float received) {
        this.id = id;
        this.user_id = user_id;
        this.received_date = received_date;
        this.received = received;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long id){
        this.user_id = id;
    }

    public Date getReceivedDate() {
        return received_date;
    }

    public void setReceivedDate(Date received_date) {
        this.received_date = received_date;
    }

    public float getReceived() {
        return received;
    }

    public void setReceived(float received) {
        this.received = received;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payments payments = (Payments) o;
        return Float.compare(payments.received, received) == 0 && Objects.equals(id, payments.id) && Objects.equals(user_id, payments.user_id) && Objects.equals(received_date, payments.received_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, received_date, received);
    }

    @Override
    public String toString() {
        return "Payments{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", received_date=" + received_date +
                ", received=" + received +
                '}';
    }
}
