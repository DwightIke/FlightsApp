import Repository.FlightRepository;
import Repository.PersonRepository;
import Repository.UserRepository;
import Database.Database;
import Services.FlightService;
import Services.PersonService;
import Services.UserService;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = new Database("D:\\mpp.db").getConnection();
        FlightRepository frep = new FlightRepository(connection);
        PersonRepository prep = new PersonRepository(connection);
        UserRepository urep = new UserRepository(connection);
        FlightService fservice = new FlightService(frep);
        PersonService pservice = new PersonService(prep);
        UserService uservice = new UserService(urep);


        System.out.println(new UserRepository(connection).canLogIn("salut"));
        System.out.println(new UserRepository(connection).canLogIn("cefaci"));
    }
}
