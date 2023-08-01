package student;

import java.sql.Date;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EventModel eventModel = new EventModel("jdbc:mysql://localhost:3306/dss", "root", "Pavan@153");

        // Main loop
        boolean running = true;
        while (running) {
            System.out.println("1. Add Event");
            System.out.println("2. Update Participation Status");
            System.out.println("3. Display All Events");
            System.out.println("4. Exit");
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

                    eventModel.addEvent(eventName, eventDate, eventVenue);
                    break;

//                case 2:
//                    System.out.print("Enter event ID: ");
//                    int eventId = scanner.nextInt();
//                    scanner.nextLine(); // Consume the newline character
//                    System.out.print("Enter new participation status: ");
//                    String status = scanner.nextLine();
//
//                    eventModel.updateParticipationStatus(eventId, status);
//                    break;

                case 3:
                    displayAllEvents(eventModel);
                    break;

                case 4:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
        eventModel.closeConnection();
    }

    private static void displayAllEvents(EventModel eventModel) {
        System.out.println("All Events:\n");
        for (Event event : eventModel.getAllEvents()) {
            System.out.println(event);
        }
    }
}
