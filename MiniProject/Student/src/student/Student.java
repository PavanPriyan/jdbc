package student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student {
    private Connection connection;
    private String url;
    private String username;
    private String password;

    public Student(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        establishConnection();
    }

    private void establishConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database.");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing the database connection.");
            e.printStackTrace();
        }
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

    public void addStudent(String studentName, String studentEmail, long studentNumber, String studentDept) {
        try {
            String query = "INSERT INTO students (student_name, student_mail, student_number, student_dept) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, studentName);
            statement.setString(2, studentEmail);
            statement.setLong(3, studentNumber);
            statement.setString(4, studentDept);
            statement.executeUpdate();
            System.out.println("Student added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding student to the database.");
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

    public void updateEvent(int eventId, String eventName, Date eventDate, String eventVenue) {
        try {
            String query = "UPDATE events SET event_name=?, event_date=?, event_venue=? WHERE event_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, eventName);
            statement.setDate(2, eventDate);
            statement.setString(3, eventVenue);
            statement.setInt(4, eventId);
            statement.executeUpdate();
            System.out.println("Event updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating event in the database.");
            e.printStackTrace();
        }
    }

    public void deleteEvent(int eventId) {
        try {
            String query = "DELETE FROM events WHERE event_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, eventId);
            statement.executeUpdate();
            System.out.println("Event deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error deleting event from the database.");
            e.printStackTrace();
        }
    }

    public void updateStudent(String studentName, String studentEmail, long studentNumber, String studentDept) {
        try {
            String query = "UPDATE students SET student_mail=?, student_number=?, student_dept=? WHERE student_name=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, studentEmail);
            statement.setLong(2, studentNumber);
            statement.setString(3, studentDept);
            statement.setString(4, studentName);
            statement.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating student in the database.");
            e.printStackTrace();
        }
    }

    public void deleteStudent(String studentName) {
        try {
            String query = "DELETE FROM students WHERE student_name=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, studentName);
            statement.executeUpdate();
            System.out.println("Student deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error deleting student from the database.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Student app = new Student("jdbc:mysql://localhost:3306/dss", "root", "Pavan@153");

        // Main loop
        boolean running = true;
        while (running) {
            System.out.println("1. Add Event");
            System.out.println("2. Update Event");
            System.out.println("3. Delete Event");
            System.out.println("4. Add Student");
            System.out.println("5. Update Student");
            System.out.println("6. Delete Student");
            System.out.println("7. Display All Events");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter event name: ");
                    String eventName = scanner.nextLine();
                    System.out.print("Enter event date (YYYY-MM-DD): ");
                    String dateInput = scanner.nextLine();
                    Date eventDate = Date.valueOf(dateInput);
                    System.out.print("Enter event venue: ");
                    String eventVenue = scanner.nextLine();

                    app.addEvent(eventName, eventDate, eventVenue);
                    break;

                case 2:
                    System.out.print("Enter event ID to update: ");
                    int eventIdToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter updated event name: ");
                    String updatedEventName = scanner.nextLine();
                    System.out.print("Enter updated event date (YYYY-MM-DD): ");
                    String updatedDateInput = scanner.nextLine();
                    Date updatedEventDate = Date.valueOf(updatedDateInput);
                    System.out.print("Enter updated event venue: ");
                    String updatedEventVenue = scanner.nextLine();

                    app.updateEvent(eventIdToUpdate, updatedEventName, updatedEventDate, updatedEventVenue);
                    break;

                case 3:
                    System.out.print("Enter event ID to delete: ");
                    int eventIdToDelete = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    app.deleteEvent(eventIdToDelete);
                    break;

                case 4:
                    // Add student information
                    break;

                case 5:
                    // Update student information
                    break;

                case 6:
                    // Delete student
                    break;

                case 7:
                    List<Event> allEvents = app.getAllEvents();
                    for (Event event : allEvents) {
                        System.out.println(event);
                    }
                    break;

                case 8:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
        app.closeConnection();
    }
}
