package Model;

import Util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Администратор on 22.07.2015.
 */
public class DAL {

    public ObservableList<Debitor> GetAllDebitors()
    {
        ObservableList<Debitor> debitors =FXCollections.observableArrayList();
        Session session = null;
        try {
            session= HibernateUtil.getSessionFactory().openSession();
            debitors.addAll(session.createCriteria(Debitor.class).list());
        } catch (Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Возникла ошибка при получении значений таблицы клиентов");
        alert.setContentText(e.getMessage());

        alert.showAndWait();
    }
        finally {
            if((session!=null) && (session.isOpen()))
                session.close();
        }
        return debitors;
    }

    public List SearchDebitors(String dabName, String dabAdress, String dabPhoneNam)
    {
        List<Debitor> debitors =null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria serchDebitors = session.createCriteria(Debitor.class);
            Criterion name = null, adress = null, phone = null;
            Conjunction conExp = null;
            LogicalExpression logExp = null;

            if (dabName != null && !dabName.isEmpty())
                name = Restrictions.like("NAME", "%" + dabName + "%");
            if (dabAdress != null && !dabAdress.isEmpty())
                adress = Restrictions.like("ADRESS", "%" + dabAdress + "%");
            if (dabPhoneNam != null && !dabPhoneNam.isEmpty())
                phone = Restrictions.like("PHONE", "%" + dabPhoneNam + "%");

            if(name != null && adress != null && phone != null) {
                conExp = Restrictions.and(name, adress, phone);
                serchDebitors.add(conExp);
            }
            else if(name != null && adress != null) {
                logExp = Restrictions.and(name, adress);
                serchDebitors.add(logExp);
            }
            else if(name != null && phone != null) {
                logExp = Restrictions.and(name, phone);
                serchDebitors.add(logExp);
            }
            else {
                serchDebitors.add(Restrictions.like("NAME", "%"+name+"%"));
            }
            debitors = serchDebitors.list();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Возникла ошибка при поиске информации");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
        finally {
            if((session!=null) && (session.isOpen()))
                session.close();
        }
        return debitors;
    }

    public ObservableList<Credit> GetAllCreditsForDebitor(int idDebitor)
    {
        ObservableList<Credit> results= FXCollections.observableArrayList();
        Session session =null;
        try {
            session= HibernateUtil.getSessionFactory().openSession();

            /*List<Credit> credits = (List<Credit>)session.createSQLQuery("SELECT ID_CREDIT,ID_DEBITOR,AMOUNT,BALANCE,OPENDATE FROM CREDITS WHERE ID_DEBITOR = " + idDebitor).addScalar("ID_CREDIT")
                    .addScalar("ID_DEBITOR").addScalar("AMOUNT").addScalar("BALANCE").addScalar("OPENDATE").setResultTransformer(Transformers.aliasToBean(Credit.class)).list();


            /*Criteria criteria = session.createCriteria(Credit.class);
            criteria.add(Restrictions.eq("ID_DEBITOR", idDebitor));
            List<Credit> credits = criteria.list();*/

            List<Object[]> tempObject = session.createSQLQuery("SELECT * FROM CREDITS WHERE ID_DEBITOR = " + idDebitor).list();
            List<Credit> credits = new ArrayList<Credit>();
            for(int i=0;i<tempObject.size();i++)
            {
                Object[] o = tempObject.get(i);

                long l = Long.parseLong(o[4].toString());
                Date date = new Date(l);
                Credit credit = new Credit(Integer.parseInt(o[1].toString()),Double.parseDouble(o[2].toString()),
                        Double.parseDouble(o[3].toString()), date);
                credit.setId(Integer.parseInt(o[0].toString()));
                credits.add(credit);
            }
            results.addAll(credits);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Возникла ошибка при получении значений таблицы кредитов");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
        finally {
            if((session!=null) && (session.isOpen()))
                session.close();
        }
        return results;
    }

    public List FetAllPaimentsForCredit(String creditID)
    {
        List<Payment> result=null;
        Session session =null;
        try {
            session= HibernateUtil.getSessionFactory().openSession();
            Criteria serchPayments = session.createCriteria(Payment.class);
            serchPayments.add(Restrictions.like("ID_CREDIT", creditID));
            result = serchPayments.list();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if((session!=null) && (session.isOpen()))
                session.close();
        }
        return result;
    }

    public boolean SaveNewDebitor(Debitor debitor)
    {
        Session session = null;
        try {
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(debitor);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if((session!=null) && (session.isOpen()))
                session.close();
        }
        return false;
    }

    public boolean SaveNewCredit(Credit credit)
    {
        Session session = null;
        try {
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(credit);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if((session!=null) && (session.isOpen()))
                session.close();
        }
        return false;
    }

    public boolean SaveNewPayment(Payment payment)
    {
        Session session = null;
        try {
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(payment);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if((session!=null) && (session.isOpen()))
                session.close();
        }
        return false;
    }

    public ObservableList<Credit> GetAllCredits()
    {
        ObservableList<Credit> credits =FXCollections.observableArrayList();
        Session session = null;
        try {
            session= HibernateUtil.getSessionFactory().openSession();
            credits.addAll(session.createCriteria(Credit.class).list());
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Возникла ошибка при получении значений таблицы клиентов");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
        finally {
            if((session!=null) && (session.isOpen()))
                session.close();
        }
        return credits;
    }
}
