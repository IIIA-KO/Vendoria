package com.vendoria.order.persistence;

import com.vendoria.order.entity.OrderItem;
import com.vendoria.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderItemRepository {
    private static final SessionFactory factory = HibernateUtil.getSessionFactory();

    public Optional<OrderItem> findById(Long id) {
        Session session = factory.openSession();
        OrderItem orderItem = session.get(OrderItem.class, id);
        session.close();
        return Optional.ofNullable(orderItem);
    }

    public List<OrderItem> findAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from OrderItem", OrderItem.class).list();
        }
    }

    public OrderItem save(OrderItem orderItem) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(orderItem);
        tx.commit();
        session.close();
        return orderItem;
    }

    public void delete(Long id) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        OrderItem orderItem = session.get(OrderItem.class, id);
        if (orderItem != null) {
            session.remove(orderItem);
        }
        tx.commit();
        session.close();
    }
}
