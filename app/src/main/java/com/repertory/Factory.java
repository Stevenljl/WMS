package com.repertory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by Finderlo on 2016/11/4.
 */
public class Factory {


    private static SessionFactory sessionFactory;

    private static Factory singleton;

    public Factory() {
        setUp();
    }

    public static SessionFactory getSessionFactory() {
        if (singleton == null){
            singleton = new Factory();
        }
//        if (sessionFactory == null) {
//            System.out.println("null");
//            singleton.setUp();
//        }
        return sessionFactory;
    }

    protected  void setUp() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            System.out.println("err");
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
