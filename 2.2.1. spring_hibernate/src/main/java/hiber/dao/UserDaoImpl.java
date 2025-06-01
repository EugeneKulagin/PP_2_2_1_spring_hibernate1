package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public void deleteAllUsers() {
        List<User> users = listUsers();
        for (User user : users) {
            sessionFactory.getCurrentSession().delete(user);
        }
    }

    @Override
    public User findOwner(String carName, String carSeries) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT u FROM User u JOIN u.car c WHERE c.name = :carName AND c.series = :carSeries";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("carName", carName);
            query.setParameter("carSeries", carSeries);

            return query.getSingleResult();
        }
    }
}