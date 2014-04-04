package com.foundationdb.sql.example;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Tutorial {
    
    public Tutorial() {
        sessionFactory = new Configuration().
                configure().
                //addPackage("com.xyz") //add package if used.
                addAnnotatedClass(Customer.class).
                addAnnotatedClass(Order.class).
                addAnnotatedClass(Address.class).
                buildSessionFactory();

    }

    public void firstCustomer() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Customer customer = new Customer (1, "Thomas", "New Customer", new Date() );
        Order firstOrder = new Order();
        firstOrder.setOrder_date(new Date());
        firstOrder.setOrder_info("Too many items");
        customer.addOrder(firstOrder);
        firstOrder.setCustomer(customer);
        
        session.persist(firstOrder);
        session.persist( customer );
        session.flush();
        session.getTransaction().commit();
        session.close();
        
    }
    
    public void listCustomers() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery( "from Customer" ).list();
        for ( Customer customer : (List<Customer>) result ) {
            System.out.println ("Customer (" + customer.getId() + ", " + customer.getRand_id() + ") : " + customer.getName());
            
        }
        session.getTransaction().commit();
        session.close();
        
    }
    
    public static void main(String[] args) {
       Tutorial tutorial = new Tutorial();

       tutorial.firstCustomer();
       tutorial.listCustomers();
       
    }
    
    private SessionFactory sessionFactory;

}
