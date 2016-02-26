package Model;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Администратор on 22.07.2015.
 */
@Entity
@Table(name="CREDITS")
public class Credit {
    @Id @GeneratedValue
    @Column(name="ID_CREDIT")
    private int id;

    @Column(name="ID_DEBITOR")
    private int idDebitor;

    @Column(name="AMOUNT")
    private double amount;

    @Column(name="BALANCE")
    private double balance;

    @Column(name="OPENDATE")
    private Date openData;

    public Credit(int idDebitor, double amount, double balance, Date openData) {
        this.idDebitor = idDebitor;
        this.amount = amount;
        this.balance = balance;
        this.openData = openData;
    }

    public Credit() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDebitor() {
        return idDebitor;
    }
    public void setIdDebitor(int idDebitor) {
        this.idDebitor = idDebitor;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getOpenData() {
        return openData;
    }
    public void setOpenData(Date openData) {
        this.openData = openData;
    }
}
