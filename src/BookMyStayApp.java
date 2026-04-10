import java.util.*;

// Booking Request
class BookingRequest {
    String bookingId;
    String roomType;

    public BookingRequest(String bookingId, String roomType) {
        this.bookingId = bookingId;
        this.roomType = roomType;
    }
}

// Shared Booking System
class BookingSystem {

    private Map<String, Integer> inventory = new HashMap<>();
    private Queue<BookingRequest> bookingQueue = new LinkedList<>();

    public BookingSystem() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    // Add request (shared resource)
    public synchronized void addRequest(BookingRequest request) {
        bookingQueue.add(request);
        System.out.println("Request Added: " + request.bookingId);
    }

    // Process booking (Critical Section)
    public synchronized void processBooking() {

        if (bookingQueue.isEmpty()) {
            return;
        }

        BookingRequest request = bookingQueue.poll();

        String roomType = request.roomType;

        System.out.println(Thread.currentThread().getName() +
                " processing " + request.bookingId);

        // Critical Section (Inventory update)
        if (inventory.containsKey(roomType) && inventory.get(roomType) > 0) {
            inventory.put(roomType, inventory.get(roomType) - 1);
            System.out.println("Booking Confirmed: " + request.bookingId);
        } else {
            System.out.println("Booking Failed (No Rooms): " + request.bookingId);
        }
    }

    public void showInventory() {
        System.out.println("Final Inventory: " + inventory);
    }
}

// Worker Thread
class BookingProcessor extends Thread {

    private BookingSystem system;

    public BookingProcessor(BookingSystem system, String name) {
        super(name);
        this.system = system;
    }

    public void run() {
        // Each thread tries multiple times
        for (int i = 0; i < 3; i++) {
            system.processBooking();
            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        // Simulate multiple requests
        system.addRequest(new BookingRequest("B101", "Single"));
        system.addRequest(new BookingRequest("B102", "Single"));
        system.addRequest(new BookingRequest("B103", "Single"));
        system.addRequest(new BookingRequest("B104", "Double"));
        system.addRequest(new BookingRequest("B105", "Double"));

        // Multiple threads (Guests)
        BookingProcessor t1 = new BookingProcessor(system, "Thread-1");
        BookingProcessor t2 = new BookingProcessor(system, "Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.showInventory();
        System.out.println("System handled concurrent bookings safely.");
    }
}