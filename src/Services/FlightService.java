package Services;

import Domain.Flight;
import Repository.FlightRepository;

import java.sql.SQLException;
import java.util.List;
/**
 * Created by Cosmin on 3/28/2017.
 */
public class FlightService {
    FlightRepository frep;
    public FlightService(FlightRepository frep) {
        this.frep = frep;
    }
    public List<Flight> getAll() throws SQLException {
        return frep.getAll();
    }
    public void buyTickets(String destination, int tickets) throws SQLException {
        frep.buyTickets(destination, tickets);
    }
    public Flight getFlight(int id) throws SQLException {
        return frep.getFlight(id);
    }
    public Flight getFlight(String destination) throws SQLException {
        return frep.getFlight(destination);
    }
}
