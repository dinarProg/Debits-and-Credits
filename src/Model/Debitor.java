package Model;

import javax.persistence.*;

import java.util.Date;

/**
 * Created by Администратор on 22.07.2015.
 */

@Entity
@Table(name="DEBITORS")
public class Debitor {
    @Id @GeneratedValue
    @Column(name="ID_DEBITOR")
    private int id;

    @Column(name="NAME")
    private String name;

    @Column(name="ADRESS")
    private String adress;

    @Column(name="PHONE")
    private String phone;

    @Column(name="DATA_REG")
    private Date data;

    public Debitor(String name, String adress, String phone, Date data) {
        this.name = name;
        this.adress = adress;
        this.phone = phone;
        this.data = data;
    }

    public Debitor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
