import java.io.*;
import java.util.*;

// Serializable Booking System
class BookingSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    Map<String, String> bookings;

    public BookingSystem() {
        inventory = new HashMap<>();
        bookings = new HashMap<>();

        // Default inventory
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    // Book Room
    public void bookRoom(String bookingId, String roomType) {
        if (inventory.containsKey(roomType) && inventory.get(roomType) > 0) {
            inventory.put(roomType, inventory.get(roomType) - 1);
            bookings.put(bookingId, roomType);
            System.out.println("Booking Confirmed: " + bookingId);
        } else {
            System.out.println("Booking Failed: No rooms available");
        }
    }

    public void showState() {
        System.out.println("Inventory: " + inventory);
        System.out.println("Bookings: " + bookings);
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "booking_data.ser";

    // Save state
    public static void save(BookingSystem system) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(system);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data!");
        }
    }

    // Load state
    public static BookingSystem load() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state restored from file.");
            return (BookingSystem) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh...");
            return new BookingSystem();

        } catch (Exception e) {
            System.out.println("Error loading data. Starting with safe state...");
            return new BookingSystem();
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Load previous state (Recovery)
        BookingSystem system = PersistenceService.load();

        Scanner sc = new Scanner(System.in);

        System.out.println("1. Book Room");
        System.out.println("2. View State");
        System.out.println("3. Exit");

        while (true) {
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {

                case 1:
                    System.out.print("Enter Booking ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Room Type (Single/Double/Suite): ");
                    String type = sc.nextLine();

                    system.bookRoom(id, type);
                    break;

                case 2:
                    system.showState();
                    break;

                case 3:
                    // Save state before exit (Persistence)
                    PersistenceService.save(system);
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}