/**
 * UseCase8BookingHistoryReport
 *
 * This program demonstrates booking history tracking and reporting.
 * Confirmed reservations are stored and later used for reporting
 * without modifying the stored data.
 *
 * @author YourName
 * @version 8.0
 */

import java.util.*;

// -------------------- RESERVATION --------------------

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
    }
}

// -------------------- BOOKING HISTORY --------------------

class BookingHistory {

    // List to store confirmed reservations (ordered)
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// -------------------- REPORT SERVICE --------------------

class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {

        System.out.println("----- Booking History -----\n");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            r.display();
            System.out.println();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        System.out.println("----- Booking Summary Report -----\n");

        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : reservations) {
            countMap.put(
                    r.getRoomType(),
                    countMap.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            System.out.println("Room Type : " + entry.getKey());
            System.out.println("Bookings  : " + entry.getValue());
            System.out.println();
        }
    }
}

// -------------------- MAIN APPLICATION --------------------

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v8.0");
        System.out.println("======================================\n");

        // Initialize booking history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from Use Case 6)
        history.addReservation(new Reservation("SI-101", "Alice", "Single Room"));
        history.addReservation(new Reservation("DB-102", "Bob", "Double Room"));
        history.addReservation(new Reservation("SU-103", "Charlie", "Suite Room"));
        history.addReservation(new Reservation("SI-104", "David", "Single Room"));

        // Initialize report service
        BookingReportService reportService = new BookingReportService();

        // Display all bookings
        reportService.displayAllBookings(history.getAllReservations());

        // Generate summary report
        reportService.generateSummary(history.getAllReservations());

        // Note: No modification to history (read-only reporting)
    }
}