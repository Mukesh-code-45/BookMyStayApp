import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Validator Class
class BookingValidator {

    public static void validate(String roomType, int rooms, Map<String, Integer> inventory)
            throws InvalidBookingException {

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Error: Invalid Room Type!");
        }

        if (rooms <= 0) {
            throw new InvalidBookingException("Error: Number of rooms must be greater than 0!");
        }

        if (inventory.get(roomType) < rooms) {
            throw new InvalidBookingException("Error: Not enough rooms available!");
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);

        try {
            System.out.println("Available Room Types: Single, Double, Suite");

            System.out.print("Enter Room Type: ");
            String roomType = sc.nextLine();

            System.out.print("Enter Number of Rooms: ");
            int rooms = sc.nextInt();

            BookingValidator.validate(roomType, rooms, inventory);

            inventory.put(roomType, inventory.get(roomType) - rooms);

            System.out.println("Booking Successful!");
            System.out.println("Remaining " + roomType + " rooms: " + inventory.get(roomType));

        } catch (InvalidBookingException e) {
            System.out.println(e.getMessage());

        } catch (Exception e) {
            System.out.println("Error: Invalid input format!");

        } finally {
            System.out.println("System is running safely...");
            sc.close();
        }
    }
}