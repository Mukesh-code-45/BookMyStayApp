/**
 * UseCase7AddOnServiceSelection
 *
 * This program demonstrates how add-on services can be attached
 * to an existing reservation without modifying booking or inventory logic.
 *
 * @author YourName
 * @version 7.0
 */

import java.util.*;

// -------------------- SERVICE MODEL --------------------

class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void display() {
        System.out.println("Service : " + serviceName + " | Cost : $" + cost);
    }
}

// -------------------- SERVICE MANAGER --------------------

class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added service '" + service.getServiceName() +
                "' to Reservation ID: " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        System.out.println("\n----- Services for Reservation: " + reservationId + " -----\n");

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        for (AddOnService service : services) {
            service.display();
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null) return 0;

        double total = 0;
        for (AddOnService service : services) {
            total += service.getCost();
        }
        return total;
    }
}

// -------------------- MAIN APPLICATION --------------------

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v7.0");
        System.out.println("======================================\n");

        // Simulated existing reservation ID (from Use Case 6)
        String reservationId = "SI-12345";

        // Initialize service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selects add-on services
        manager.addService(reservationId, new AddOnService("Breakfast", 20.0));
        manager.addService(reservationId, new AddOnService("Airport Pickup", 50.0));
        manager.addService(reservationId, new AddOnService("Extra Bed", 30.0));

        // Display selected services
        manager.displayServices(reservationId);

        // Calculate total cost
        double totalCost = manager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Add-On Cost: $" + totalCost);

        // Note: No booking or inventory changes happen here
    }
}