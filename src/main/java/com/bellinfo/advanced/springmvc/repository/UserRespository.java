package com.bellinfo.advanced.springmvc.repository;


import com.bellinfo.advanced.springmvc.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class UserRespository {

    @Autowired
    SessionFactory sessionFactory;

    private Session getCurrentSession(){
        return sessionFactory.openSession();
    }


    public void save(Student std){
       Session s = getCurrentSession();
       s.save(std);
    }


}
