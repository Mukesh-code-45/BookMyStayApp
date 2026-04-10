/**
 * UseCase5BookingRequestQueue
 *
 * This program demonstrates handling booking requests using a Queue
 * to ensure First-Come-First-Served (FIFO) processing.
 *
 * No inventory updates or room allocation occur in this stage.
 * It only collects and organizes booking requests.
 *
 * @author YourName
 * @version 5.0
 */

import java.util.*;

// -------------------- RESERVATION (REQUEST OBJECT) --------------------

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

    public void display() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Room Type  : " + roomType);
    }
}

// -------------------- BOOKING REQUEST QUEUE --------------------

class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue (FIFO)
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display all queued requests
    public void displayQueue() {
        System.out.println("\n----- Booking Request Queue -----\n");

        if (queue.isEmpty()) {
            System.out.println("No booking requests available.");
            return;
        }

        for (Reservation r : queue) {
            r.display();
            System.out.println();
        }
    }
}

// -------------------- MAIN APPLICATION --------------------

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v5.0");
        System.out.println("======================================\n");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate incoming booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display queue (FIFO order)
        bookingQueue.displayQueue();

        // Note: No inventory updates or allocation happens here
    }
}