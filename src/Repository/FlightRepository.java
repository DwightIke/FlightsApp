package Repository;

import Domain.Flight;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
public class FlightRepository {
    Connection connection;
    public FlightRepository(Connection connection) {
        this.connection = connection;
    }
    public List<Flight> getAll() throws SQLException {
        List<Flight> flights = new ArrayList<Flight>();
        ResultSet resultSet = connection.createStatement().executeQuery("select * from flights");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int seats = resultSet.getInt("seats");
            if (seats > 0)
                flights.add(new Flight(id,
                        resultSet.getString("destination"),
                        resultSet.getString("departure_date"),
                        resultSet.getString("departure_time"),
                        resultSet.getString("airport"),
                        seats));
        }
        return flights;
    }
    public void buyTickets(String destination, int tickets) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "update flights set seats=seats-? where destination=? and seats>=?");
        preparedStatement.setInt(1, tickets);
        preparedStatement.setString(2, destination);
        preparedStatement.setInt(3, tickets);
        preparedStatement.execute();
    }
    public Flight getFlight(String destination) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from flights where destination=?");
        preparedStatement.setString(1, destination);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Flight(resultSet.getInt("id"),
                    resultSet.getString("destination"),
                    resultSet.getString("departure_date"),
                    resultSet.getString("departure_time"),
                    resultSet.getString("airport"),
                    resultSet.getInt("seats"));
        }
        return null;
    }
    public Flight getFlight(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from flights where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Flight(id,
                    resultSet.getString("destination"),
                    resultSet.getString("departure_date"),
                    resultSet.getString("departure_time"),
                    resultSet.getString("airport"),
                    resultSet.getInt("seats"));
        }
        return null;
    }
}
