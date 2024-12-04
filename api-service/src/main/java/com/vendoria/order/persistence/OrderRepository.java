package com.vendoria.order.persistence;

import com.vendoria.order.entity.Order;
import com.vendoria.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {
    private static final SessionFactory factory = HibernateUtil.getSessionFactory();

    public Optional<Order> findById(Long id) {
        Session session = factory.openSession();
        Order order = session.get(Order.class, id);
        session.close();
        return Optional.ofNullable(order);
    }

    public List<Order> findAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Order", Order.class).list();
        }
    }

    public Order save(Order order) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(order);
        tx.commit();
        session.close();
        return order;
    }

    public void delete(Long id) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Order order = session.get(Order.class, id);
        if (order != null) {
            session.remove(order);
        }
        tx.commit();
        session.close();
    }

    public Optional<Order> getLast() {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.createQuery("FROM Order o JOIN FETCH o.items ORDER BY o.date DESC", Order.class)
                    .setMaxResults(1)
                    .uniqueResult());
        }
    }
}
