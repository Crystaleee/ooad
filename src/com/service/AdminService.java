package com.service;

import com.entity.Backup;
import com.entity.Employee;
import com.entity.Equipment;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by wsylvia on 12/29/16.
 */
public class AdminService {
    private SessionFactory factory;
    private SimpleDateFormat dateFormat;

    public AdminService() {
        Configuration config = new Configuration().configure();
        factory = config.buildSessionFactory();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    /* Employee */
    public Employee viewEmployee(int employeeID) {
        Session session = null;
        Employee employee = null;

        try {
            session = factory.openSession();
            String hql = "FROM Employee E where E.employeeId = :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", employeeID);
            employee = (Employee) query.uniqueResult();
//            System.out.printf("ID: %d, Name: %s \n",employee.getEmployeeId(),employee.getEmployeeName());

//            List<Employee> result = query.list();
//
//            for(Employee employee : result) {
//                System.out.printf("ID: %d, Name: %s \n",employee.getEmployeeId(),employee.getEmployeeName());
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return employee;

    }


    public int addEmployee(String name) {
        if (name == null || (name = name.trim()).equals("")) {
            return -1;
        }

        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            Employee employee = new Employee();
            employee.setEmployeeName(name);
            session.save(employee);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }

            return -1;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return 0;
    }


    public int modifyEmployee(int employeeID, String name) {
        if (name == null || (name = name.trim()).equals("")) {
            return -1;
        }

        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "FROM Employee E where E.employeeId = :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", employeeID);
            Employee employee = (Employee) query.uniqueResult();

            if (employee == null) {
                return -1;
            }

            employee.setEmployeeName(name);
            session.update(employee);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();

            if (transaction != null) {
                transaction.rollback();
            }

            return -1;

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return 0;
    }


    public int removeEmployee(int employeeID) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            String hql = "DELETE Employee E WHERE E.employeeId = :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", employeeID);
            query.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }

            return -1;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return 0;
    }


    /* Equipment */
    public Equipment viewEquipment(int equipmentID) {
        Session session = null;
        Transaction transaction = null;
        Equipment equipment = null;

        try {
            session = factory.openSession();
            String hql = "FROM Equipment E WHERE E.equipmentId = :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", equipmentID);
            equipment = (Equipment) query.uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return equipment;
    }


    public int addEquipment(String equipmentName, Date datePurchased) {
        if (equipmentName == null || (equipmentName = equipmentName.trim()).equals("")) {
            return -1;
        }

        Session session = null;
        Transaction transaction = null;
        Date today = new Date();

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            Equipment equipment = new Equipment();
            equipment.setQuipmentName(equipmentName);
            if (datePurchased == null || datePurchased.after(today)) {
                datePurchased = today;
            }
            equipment.setDatePurchased(datePurchased);

            session.save(equipment);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }

            return -1;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return 0;
    }

    public int modifyEquipment(int equipmentID, String equipmentName, Date datePurchased, Date dateDiscarded, Byte isBorrowed) {
        Session session = null;
        Transaction transaction = null;
        Date today = new Date();

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            String hql = "FROM Equipment E WHERE E.equipmentId = :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", equipmentID);
            Equipment equipment = (Equipment) query.uniqueResult();

            if (equipmentName != null && !(equipmentName = equipmentName.trim()).equals("")) {
                equipment.setQuipmentName(equipmentName);
            }

            if (datePurchased != null) {
                datePurchased = datePurchased.after(today) ? today : datePurchased;
                equipment.setDatePurchased(datePurchased);
            }

            if (dateDiscarded != null) {
                dateDiscarded = dateDiscarded.after(today) ? today : dateDiscarded;
                equipment.setDateDiscarded(dateDiscarded);
            }

            if (isBorrowed != null && (isBorrowed == 0 || isBorrowed == 1)) {
                equipment.setIsBorrowed(isBorrowed);
            }

            session.update(equipment);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }

            return -1;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return 0;
    }


    public int removeEquipment(int equipmentID) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            String hql = "DELETE Equipment E WHERE E.equipmentId = :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", equipmentID);
            query.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }

            return -1;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return 0;
    }

    //TODO: 还差领用设备
    public void useEquipment(int equipmentID, int employeeID) {

    }

    //TODO: 还差归还设备
    public void returnEquipment(int equipmentID, Date dateReturned) {

    }

    public int discardEquipment(int equipmentID, Date dateDiscarded) {
        Session session = null;
        Transaction transaction = null;
        Date today = new Date();

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            String hql = "FROM Equipment E WHERE E.equipmentId = :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", equipmentID);
            Equipment equipment = (Equipment) query.uniqueResult();

            if (dateDiscarded != null) {
                dateDiscarded = dateDiscarded.after(today) ? today : dateDiscarded;
                equipment.setDateDiscarded(dateDiscarded);
            }

            session.update(equipment);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }

            return -1;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return 0;
    }

    /* Backup */
    public Backup viewBackup(int backupID) {
        Session session = null;
        Transaction transaction = null;
        Backup backup = null;

        try {
            session = factory.openSession();
            String hql = "FROM Backup B WHERE B.backupId = :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", backupID);
            backup = (Backup) query.uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return backup;
    }

    public int addBackup(String backupName, Date datePurchased) {
        if (backupName == null || (backupName = backupName.trim()).equals("")) {
            return -1;
        }

        Session session = null;
        Transaction transaction = null;
        Date today = new Date();

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            Backup backup = new Backup();
            backup.setBackupName(backupName);
            if (datePurchased == null || datePurchased.after(today)) {
                datePurchased = today;
            }
            backup.setDatePurchased(datePurchased);

            session.save(backup);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }

            return -1;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return 0;
    }

    public int modifyBackup(int backupID, String backupName, Date datePurchased, Date dateDiscarded, Byte isInstalled) {
        Session session = null;
        Transaction transaction = null;
        Date today = new Date();

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            String hql = "FROM Backup B WHERE B.backupId = :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", backupID);
            Backup backup = (Backup) query.uniqueResult();

            if (backupName != null && !(backupName = backupName.trim()).equals("")) {
                backup.setBackupName(backupName);
            }

            if (datePurchased != null) {
                datePurchased = datePurchased.after(today) ? today : datePurchased;
                backup.setDatePurchased(datePurchased);
            }

            if (dateDiscarded != null) {
                dateDiscarded = dateDiscarded.after(today) ? today : dateDiscarded;
                backup.setDateDiscarded(dateDiscarded);
            }

            if (isInstalled != null && (isInstalled == 0 || isInstalled == 1)) {
                backup.setIsInstalled(isInstalled);
            }

            session.update(backup);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }

            return -1;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return 0;
    }

    //TODO: 还差安装
    public void installBackup(int equipmentID, int backupID) {

    }

    //TODO: 还差归还
    public void returnBackup(int backupID, Date dateReturned) {

    }

    public int discardBackup(int backupID, Date dateDiscarded) {
        Session session = null;
        Transaction transaction = null;
        Date today = new Date();

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            String hql = "FROM Backup B WHERE B.backupId = :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", backupID);
            Backup backup = (Backup) query.uniqueResult();

            if (dateDiscarded != null) {
                dateDiscarded = dateDiscarded.after(today) ? today : dateDiscarded;
                backup.setDateDiscarded(dateDiscarded);
            }

            session.update(backup);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }

            return -1;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return 0;
    }


    public static void main(String[] args) throws ParseException {
        AdminService service = new AdminService();
        int result = service.removeEquipment(1);
//        int result = service.modifyEquipment(2,null,null,null,new Byte("1"));
//        int result = service.addEquipment("TestE",service.dateFormat.parse("2017-12-01"));
        System.out.println(result);

//        int result = service.removeEmployee(3);
//        System.out.println(result);
//        int result = service.addEmployee("   fawewe   ");
//        System.out.println(result);
//        Employee employee = service.viewEmployee(1);
//        System.out.printf("ID: %d, Name: %s \n", employee.getEmployeeId(), employee.getEmployeeName());
//
//        service.modifyEmployee(1,"newNameTest2");
//        Employee employee1 = service.viewEmployee(1);
//        System.out.printf("ID: %d, Name: %s \n", employee1.getEmployeeId(), employee1.getEmployeeName());

    }


}
