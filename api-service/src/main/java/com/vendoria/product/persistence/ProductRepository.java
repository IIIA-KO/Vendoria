package com.vendoria.product.persistence;

import com.vendoria.product.entity.Product;
import com.vendoria.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRepository {
    private static final SessionFactory factory = HibernateUtil.getSessionFactory();
    private Map<Long, Product> productCache = new HashMap<>();

    public Optional<Product> findById(Long id) {
        if (productCache.containsKey(id)) {
            return Optional.of(productCache.get(id));
        }

        Session session = factory.openSession();
        Product product = session.get(Product.class, id);
        session.close();
        return Optional.ofNullable(product);
    }

    public List<Product> findAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Product", Product.class).list();
        }
    }


    public List<Product> findByNameContainingIgnoreCase(String name) {
        try (Session session = factory.openSession()) {
            Query<Product> query = session.createQuery("from Product where name like :name", Product.class);
            query.setParameter("name", "%" + name.toLowerCase() + "%");
            return query.list();
        }
    }

    public Product save(Product product) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(product);
        tx.commit();
        session.close();

        productCache.put(product.getId(), product);

        return product;
    }

    public Product update(Long id, Product updatedProduct) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Product product = session.get(Product.class, id);
        if (product != null) {
            productCache.remove(id);

            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setStockQuantity(updatedProduct.getStockQuantity());
            session.merge(product);
        }

        tx.commit();
        session.close();

        productCache.put(id, updatedProduct);

        return product;
    }

    public void delete(Long id) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Product product = session.get(Product.class, id);
        if (product != null) {
            session.remove(product);
        }
        tx.commit();
        session.close();

        productCache.remove(id);
    }
}
