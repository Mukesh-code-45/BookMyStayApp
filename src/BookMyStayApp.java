/**
 * UseCase3InventorySetup
 *
 * This class demonstrates centralized inventory management using HashMap.
 * It replaces scattered availability variables with a single data structure,
 * ensuring consistency and scalability in the Hotel Booking System.
 *
 * @author YourName
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

// Inventory management class
class RoomInventory {

    // HashMap to store room type and availability
    private Map<String, Integer> inventory;

    // Constructor to initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initialize room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Method to get availability of a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to update availability
    public void updateAvailability(String roomType, int count) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, count);
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    // Method to display full inventory
    public void displayInventory() {
        System.out.println("----- Current Room Inventory -----\n");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type : " + entry.getKey());
            System.out.println("Available : " + entry.getValue());
            System.out.println();
        }
    }
}

// Main application class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v3.1");
        System.out.println("======================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Example update
        System.out.println("Updating availability...\n");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        inventory.displayInventory();
    }
}