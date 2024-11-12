package com.vendoria.user.persistence;

import com.vendoria.user.entity.User;
import com.vendoria.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    private static final SessionFactory factory = HibernateUtil.getSessionFactory();

    public Optional<User> findById(Long id) {
        Session session = factory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return Optional.ofNullable(user);
    }

    public Optional<User> findByUsername(String username) {
        Session session = factory.openSession();
        User user = null;
        try {
            user = session.createQuery("from User where username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return Optional.ofNullable(user);
    }

    public Optional<User> findByEmail(String email) {
        Session session = factory.openSession();
        User user = null;
        try {
            user = session
                    .createQuery("from User where email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return Optional.ofNullable(user);
    }

    public void save(User user) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(user);
        tx.commit();
        session.close();
    }
}
