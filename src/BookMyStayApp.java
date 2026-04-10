import java.util.*;

// Custom Exception
class CancellationException extends Exception {
    public CancellationException(String message) {
        super(message);
    }
}

// Booking System
class BookingSystem {

    Map<String, Integer> inventory = new HashMap<>();
    Map<String, String> bookings = new HashMap<>(); // bookingId -> roomType
    Stack<String> rollbackStack = new Stack<>();

    public BookingSystem() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    // Confirm Booking
    public void bookRoom(String bookingId, String roomType) {
        if (inventory.containsKey(roomType) && inventory.get(roomType) > 0) {
            inventory.put(roomType, inventory.get(roomType) - 1);
            bookings.put(bookingId, roomType);
            System.out.println("Booking Confirmed: " + bookingId);
        } else {
            System.out.println("Booking Failed: Room not available");
        }
    }

    // Cancel Booking (Rollback)
    public void cancelBooking(String bookingId) throws CancellationException {

        // Validate booking exists
        if (!bookings.containsKey(bookingId)) {
            throw new CancellationException("Error: Booking does not exist!");
        }

        String roomType = bookings.get(bookingId);

        // Push to rollback stack (LIFO)
        rollbackStack.push(bookingId);

        // Restore inventory
        inventory.put(roomType, inventory.get(roomType) + 1);

        // Remove booking
        bookings.remove(bookingId);

        System.out.println("Cancellation Successful for Booking ID: " + bookingId);
    }

    public void showInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingSystem system = new BookingSystem();

        try {
            // Sample booking
            system.bookRoom("B101", "Single");
            system.bookRoom("B102", "Double");

            system.showInventory();

            // Cancellation input
            System.out.print("Enter Booking ID to cancel: ");
            String bookingId = sc.nextLine();

            system.cancelBooking(bookingId);

            system.showInventory();

        } catch (CancellationException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("System state is consistent and safe.");
            sc.close();
        }
    }
}