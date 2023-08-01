package student;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventModel extends Model {

    public EventModel(String url, String username, String password) {
        super(url, username, password);
    }

    public void addEvent(String eventName, Date eventDate, String eventVenue) {
        try {
            String query = "INSERT INTO events (event_name, event_date, event_venue) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, eventName);
            statement.setDate(2, eventDate);
            statement.setString(3, eventVenue);
            statement.executeUpdate();
            System.out.println("Event added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding event to the database.");
            e.printStackTrace();
        }
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        try {
            String query = "SELECT * FROM events";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int eventId = resultSet.getInt("event_id");
                String eventName = resultSet.getString("event_name");
                Date eventDate = resultSet.getDate("event_date");
                String eventVenue = resultSet.getString("event_venue");
                Event event = new Event(eventId, eventName, eventDate, eventVenue);
                events.add(event);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error retrieving events from the database.");
            e.printStackTrace();
        }
        return events;
    }
}
