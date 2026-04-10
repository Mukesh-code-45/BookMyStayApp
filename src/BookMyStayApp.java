/**
 * UseCase6RoomAllocationService
 *
 * This program demonstrates booking confirmation and safe room allocation.
 * It ensures no double-booking by using Set and maintains inventory consistency.
 *
 * @author YourName
 * @version 6.0
 */

import java.util.*;

// -------------------- RESERVATION --------------------

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// -------------------- INVENTORY SERVICE --------------------

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

// -------------------- BOOKING SERVICE --------------------

class BookingService {

    private Queue<Reservation> queue;
    private RoomInventory inventory;

    // Track allocated room IDs (global uniqueness)
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map room type -> assigned room IDs
    private Map<String, Set<String>> allocationMap = new HashMap<>();

    public BookingService(Queue<Reservation> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    // Process all booking requests
    public void processBookings() {

        System.out.println("----- Processing Bookings -----\n");

        while (!queue.isEmpty()) {

            Reservation request = queue.poll();
            String roomType = request.getRoomType();

            int available = inventory.getAvailability(roomType);

            if (available > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Ensure uniqueness
                while (allocatedRoomIds.contains(roomId)) {
                    roomId = generateRoomId(roomType);
                }

                // Store in set
                allocatedRoomIds.add(roomId);

                // Map room type to IDs
                allocationMap
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                // Decrement inventory immediately
                inventory.decrement(roomType);

                // Confirm reservation
                System.out.println("Booking Confirmed!");
                System.out.println("Guest   : " + request.getGuestName());
                System.out.println("Room    : " + roomType);
                System.out.println("Room ID : " + roomId);
                System.out.println();

            } else {
                System.out.println("Booking Failed (No Availability)");
                System.out.println("Guest : " + request.getGuestName());
                System.out.println("Room  : " + roomType);
                System.out.println();
            }
        }
    }

    // Generate room ID
    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 5);
    }
}

// -------------------- MAIN APPLICATION --------------------

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v6.0");
        System.out.println("======================================\n");

        // Initialize queue (FIFO)
        Queue<Reservation> queue = new LinkedList<>();

        queue.offer(new Reservation("Alice", "Single Room"));
        queue.offer(new Reservation("Bob", "Single Room"));
        queue.offer(new Reservation("Charlie", "Single Room")); // should fail
        queue.offer(new Reservation("David", "Suite Room"));

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Process bookings
        BookingService bookingService = new BookingService(queue, inventory);
        bookingService.processBookings();
    }
}