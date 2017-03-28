package Views;
import Services.FlightService;
import Services.PersonService;
import Services.UserService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import Domain.Flight;
import java.util.*;

import java.sql.SQLException;

/**
 * Created by Cosmin on 3/28/2017.
 */
public class loginViewController {
    @FXML private TextField loginTextField;
    @FXML private TextField destinationTextField;
    @FXML private TextField ddateTextField;
    @FXML private TextField dtimeTextField;
    @FXML private TextField touristsTextField;
    @FXML private TextField bdestinationTextField;
    @FXML private TextField ticketsTextField;
    @FXML private Button loginButton;
    @FXML private Button searchButton;
    @FXML private Button buyticketsButton;
    @FXML private Button getflightsButton;
    @FXML private ListView<Flight> flightsList;

    FlightService fservice;
    PersonService pservice;
    UserService uservice;

    ObservableList<Flight> fmodel;

    boolean loggedin;

    public void setServices(FlightService fservice, PersonService pservice, UserService uservice) throws SQLException {
        this.fservice = fservice;
        this.pservice = pservice;
        this.uservice = uservice;
        this.fmodel = FXCollections.observableArrayList(fservice.getAll());
    }
    @FXML private void initialize() {
        //flightsList.setItems(this.fmodel);
        loggedin = false;
        setDisableItems(true);
    }


    private void setDisableItems(boolean what) {
        destinationTextField.setDisable(what);
        ddateTextField.setDisable(what);
        ddateTextField.setDisable(what);
        dtimeTextField.setDisable(what);
        touristsTextField.setDisable(what);
        bdestinationTextField.setDisable(what);
        ticketsTextField.setDisable(what);
        searchButton.setDisable(what);
        buyticketsButton.setDisable(what);
        getflightsButton.setDisable(what);
        flightsList.setDisable(what);
    }
    @FXML private void loginButtonAction() throws SQLException{
        if (this.loggedin == true) {
            this.loggedin = false;
            setDisableItems(true);
            flightsList.setItems(null);
            loginButton.setText("Login");
            loginTextField.setDisable(false);
            clearBuyTicketsFields();
            clearSearchFields();
            showMessage(Alert.AlertType.CONFIRMATION, "Logout !", "You have been logged out !");
            return;
        }
        else {
            String loginCode = loginTextField.getText();
            if (uservice.canLogIn(loginCode)) {
                setDisableItems(false);
                this.loggedin = true;
                showMessage(Alert.AlertType.CONFIRMATION, "Login !", "You have been logged in!");
                loginButton.setText("Logout");
                loginTextField.setDisable(true);
                flightsList.setItems(this.fmodel);
            }
            else {
                showMessage(Alert.AlertType.ERROR, "Login !", "Wrong password. Are you trying to hack us ?");
            }
        }
    }
    @FXML private void searchButtonAction() throws SQLException {
        String destination = destinationTextField.getText();
        String ddate = ddateTextField.getText();
        String dtime = dtimeTextField.getText();
        List<Flight> flist = fservice.getAll();
        List<Flight> fnewlist = new ArrayList<Flight>();
        for (Flight f : flist)
            if (f.getDeparture_date().equals(ddate) &&
                    f.getDeparture_time().equals(dtime) &&
                    f.getDestination().equals(destination))
                fnewlist.add(f);
        ObservableList<Flight> olist = FXCollections.observableArrayList(fnewlist);
        flightsList.setItems(olist);
        clearSearchFields();
    }
    @FXML private void buyticketsButtonAction() throws SQLException {
        String destination = bdestinationTextField.getText();
        String szTickets = ticketsTextField.getText();
        int tickets;
        try {
        tickets = Integer.parseInt(szTickets);}
        catch(Exception ex) {
            showMessage(Alert.AlertType.ERROR, "Error", "Error parsing tickets number !");
            return;
        }
        Flight f = fservice.getFlight(destination);
        if (f == null) {
            showMessage(Alert.AlertType.ERROR, "Error !", "There is no flight to destination: "+destination);
            return;
        }
        if (f.getSeats() < tickets) {
            showMessage(Alert.AlertType.WARNING, "Warning !", "There are no enough seats in this flight !");
            return;
        }
        fservice.buyTickets(destination, tickets);
        getflightsButtonAction();
        clearBuyTicketsFields();
        showMessage(Alert.AlertType.CONFIRMATION, "Succes !", "Tickets bought");
    }
    @FXML private void getflightsButtonAction() throws SQLException {
        fmodel = FXCollections.observableArrayList(fservice.getAll());
        flightsList.setItems(fmodel);
    }
    private void clearSearchFields() {
        destinationTextField.setText("");
        ddateTextField.setText("");
        dtimeTextField.setText("");
    }
    private void clearBuyTicketsFields() {
        bdestinationTextField.setText("");
        touristsTextField.setText("");
        ticketsTextField.setText("");
    }
    public loginViewController() {
        //flightsList.setItems(fmodel);
    }
    static void showMessage(Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        //message.initOwner(dialogStage);
        message.showAndWait();
    }
}
