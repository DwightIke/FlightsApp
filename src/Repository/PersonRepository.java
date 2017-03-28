package Repository;

import Domain.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

/**
 * Created by Musafir on 3/12/2017.
 */
public class PersonRepository {
    Connection connection;
    public PersonRepository(Connection connection) {
        this.connection = connection;
    }
    public List<Person> getAll() throws SQLException {
        List<Person> persons = new ArrayList<Person>();
        ResultSet resultSet = connection.createStatement().executeQuery("select * from person");
        while (resultSet.next()) {
            persons.add(new Person(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("extra")));
        }
        return persons;
    }

    public void add(String name, String extra) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into person(name,extra) values(?,?)");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, extra);
        preparedStatement.execute();
    }

    public void delete(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from person where id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public void update(int id, String name, String extra) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update person set name=?, extra=? where id=?");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, extra);
        preparedStatement.setInt(3, id);
        preparedStatement.execute();
    }
    public Person getPerson(String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from person where name=?");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Person(resultSet.getInt("id"),
                    name,
                    resultSet.getString("extra"));
        }
        return null;
    }
    public Person getPerson(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from person where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Person(id,
                    resultSet.getString("name"),
                    resultSet.getString("extra"));
        }
        return null;
    }

}
