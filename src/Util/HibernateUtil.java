package Util;

import javafx.scene.control.Alert;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.Locale;

/**
 * Created by Администратор on 22.07.2015.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    private HibernateUtil(){}

    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null)
        {
            try {//sessionFactory=new Configuration().configure().buildSessionFactory();
                Configuration configuration = new Configuration();
                configuration.configure();
                Locale loc = Locale.getDefault();
                Locale.setDefault(Locale.ENGLISH);
                serviceRegistry = new ServiceRegistryBuilder().applySettings(
                        configuration.getProperties()).buildServiceRegistry();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                Locale.setDefault(loc);
            } catch (HibernateException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка при конфигурировании файлов");
                alert.setContentText(e.getMessage());

                e.printStackTrace();

                alert.showAndWait();
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());

                alert.showAndWait();
            }
        }
        return sessionFactory;
    }
}
