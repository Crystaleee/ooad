package com;

import com.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.hibernate.SynchronizeableQuery;
import org.hibernate.cfg.Configuration;

/**
 * Created by wsylvia on 12/29/16.
 */
public class Test {
    public static void main(String[] args) {
        Configuration config = new Configuration().configure();
        SessionFactory factory = config.buildSessionFactory();
        Session session = null;

        try {
            session = factory.openSession();
            session.beginTransaction();
            Employee employee = new Employee();
            employee.setEmployeeName("test");
            session.save(employee);

            System.out.println("Test: " + employee.getEmployeeId());
            session.getTransaction().commit();

        } catch (Exception exception) {
            exception.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
