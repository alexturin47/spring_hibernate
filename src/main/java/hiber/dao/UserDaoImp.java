package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {


    private SessionFactory sessionFactory;

    @Autowired
    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void add(User user, Car car) {
        sessionFactory.getCurrentSession().save(user);
        car.setUser(user);
        sessionFactory.getCurrentSession().save(car);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public List<Object[]> listUsersWithCar() {
        TypedQuery<Object[]> query = sessionFactory.getCurrentSession().createQuery("from Car c inner join c.user", Object[].class);
        return query.getResultList();
    }

    @Override
    public User getUserByCar(Car car) {
        Session session = sessionFactory.getCurrentSession();
        Query<Car> qCar = session.createQuery("from Car c where c.model=:model and c.series=:series", Car.class);
        qCar.setParameter("series", car.getSeries());
        qCar.setParameter("model", car.getModel());

        Query<User> query = session.createQuery("from User u where u.id=:id", User.class);
        query.setParameter("id", qCar.getSingleResult().getId());

        return query.getSingleResult();
    }

}
