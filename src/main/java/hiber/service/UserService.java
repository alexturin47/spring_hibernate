package hiber.service;

import hiber.model.Car;
import hiber.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    @Transactional
    void add(User user);

    @Transactional
    void add(User user, Car car);

    List<User> listUsers();

    List<Object[]> listUsersWithCar();

    User getUserByCar(Car car);
}
