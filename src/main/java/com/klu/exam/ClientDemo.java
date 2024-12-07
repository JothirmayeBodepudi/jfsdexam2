package com.klu.exam;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.klu.exam.Department;

public class ClientDemo {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = config.buildSessionFactory();

       
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Department dept1 = new Department();
            dept1.setName("Computer Science");
            dept1.setLocation("Building A");
            dept1.setHodName("Dr. Smith");

            Department dept2 = new Department();
            dept2.setName("robotic Engineering");
            dept2.setLocation("Building B");
            dept2.setHodName("Dr. Johnson");

            Department dept3 = new Department();
            dept3.setName("Civil Engineering");
            dept3.setLocation("Building C");
            dept3.setHodName("Dr. Brown");

            session.save(dept1);
            session.save(dept2);
            session.save(dept3);

            transaction.commit();
            System.out.println("Sample records inserted successfully!");
            
            transaction = session.beginTransaction();

            String hql = "UPDATE Department SET name = ?1, location = ?2 WHERE departmentId = ?3";
            int updatedRows = session.createQuery(hql)
                                     .setParameter(1, "Updated Department Name")
                                     .setParameter(2, "Updated Location")
                                     .setParameter(3, 1) 
                                     .executeUpdate();

            transaction.commit();
            System.out.println(updatedRows + " record(s) updated.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}
