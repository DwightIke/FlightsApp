/**
 * Created by Cosmin on 3/28/2017.
 */

import Repository.FlightRepository;
import Repository.PersonRepository;
import Repository.UserRepository;
import Services.FlightService;
import Services.PersonService;
import Services.UserService;
import Views.loginViewController;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import Database.Database;
public class MainLayout extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    Pane pane;
    FXMLLoader loader;
    Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws SQLException,ClassNotFoundException, MalformedURLException, IOException {
        try {
            Database db = new Database("D:\\mpp.db");
            FlightRepository frep = new FlightRepository(db.getConnection());
            PersonRepository prep = new PersonRepository(db.getConnection());
            UserRepository urep = new UserRepository(db.getConnection());
            FlightService fservice = new FlightService(frep);
            PersonService pservice = new PersonService(prep);
            UserService uservice = new UserService(urep);
            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("Emirates Airline App Tickets");
            loader = new FXMLLoader();
            String pathToFxml = "src/Views/loginView.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader.setLocation(fxmlUrl);
            pane = loader.load();
            loginViewController ctrl = loader.getController();
            ctrl.setServices(fservice, pservice, uservice);
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
