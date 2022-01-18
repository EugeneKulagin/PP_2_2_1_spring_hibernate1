package hiber.dao;

import hiber.model.User;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    public void deleteAllUsers() {
        List<User> users = listUsers();
        for (User user : users) {
            sessionFactory.getCurrentSession().delete(user);
        }
    }

    public User findOwner(String carName, String carSeries) {
        EntityGraph<User> entityGraph = entityManager.createEntityGraph(User.class);
        entityGraph.addAttributeNodes("car");

        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u JOIN u.car c WHERE c.name = :carName AND c.series = :carSeries", User.class);
        query.setParameter("carName", carName);
        query.setParameter("carSeries", carSeries);
        query.setHint("javax.persistence.fetchgraph", entityGraph);

        return query.getSingleResult();
    }
}