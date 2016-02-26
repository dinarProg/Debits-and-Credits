package Model;

/**
 * Created by Администратор on 22.07.2015.
 */

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name="PAYMENTS")
public class Payment {
    @Id @GeneratedValue
    @Column(name="ID_PAYMENT")
    private int id;

    @Column(name="ID_CREDIT")
    private int idCredit;

    @Column(name="AMOUNT")
    private double amount;

    @Column(name="PAYMENTDATE")
    private Date paymentData;

    public Payment(int idCredit, double amount, Date paymentData) {
        this.idCredit = idCredit;
        this.amount = amount;
        this.paymentData = paymentData;
    }

    public Payment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(int idCredit) {
        this.idCredit = idCredit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(Date paymentData) {
        this.paymentData = paymentData;
    }
}
