package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

        userService.add(new User("User5", "lastname5", "user5@mail.com")
                , new Car("Volvo", 40));
        userService.add(new User("User6", "lastname6", "user6@mail.com")
                , new Car("MMC", 45));
        userService.add(new User("User7", "lastname7", "user7@mail.com")
                , new Car("MMC", 55));


        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
        }

        List<Object[]> usersWithCar = userService.listUsersWithCar();
        for (Object[] obj : usersWithCar) {
            User user = (User) obj[1];
            Car car = (Car) obj[0];
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car model = " + car.getModel());
            System.out.println("Car series = " + car.getSeries());
            System.out.println();
        }


        User user = userService.getUserByCar(new Car("MMC", 45));
        System.out.println("User by MMC 45 : " + user.getFirstName() + " " + user.getLastName()
                + " " + user.getEmail());

        context.close();
    }
}
