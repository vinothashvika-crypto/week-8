import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Week 8 Assignment 2 - The Campus Parking Charge Calculator (polymorphism)
 * Bike Rs 10/hr; Car Rs 30 first hour + Rs 20 each extra hour; Truck Rs 50/hr, minimum Rs 100.
 */
public class A2_CampusParkingCharge {

    static final String SAMPLE = "4\nBIKE 3\nCAR 4\nTRUCK 1\nCAR 1";

    static abstract class Vehicle {
        protected final int hours;
        Vehicle(int hours) { this.hours = hours; }
        abstract String getType();
        abstract double parkingCharge();
    }

    static class Bike extends Vehicle {
        Bike(int h) { super(h); }
        String getType() { return "BIKE"; }
        double parkingCharge() { return 10.0 * hours; }
    }

    static class Car extends Vehicle {
        Car(int h) { super(h); }
        String getType() { return "CAR"; }
        double parkingCharge() { return 30.0 + 20.0 * (hours - 1); }
    }

    static class Truck extends Vehicle {
        Truck(int h) { super(h); }
        String getType() { return "TRUCK"; }
        double parkingCharge() { return Math.max(50.0 * hours, 100.0); }
    }

    static Vehicle create(String type, int hours) {
        switch (type.toUpperCase()) {
            case "BIKE": return new Bike(hours);
            case "CAR": return new Car(hours);
            case "TRUCK": return new Truck(hours);
            default: throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = readInput();
        int n = Integer.parseInt(lines.get(0).trim());
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        for (int i = 1; i <= n; i++) {
            String[] p = lines.get(i).trim().split("\\s+");
            vehicles.add(create(p[0], Integer.parseInt(p[1])));
        }
        double total = 0;
        for (Vehicle v : vehicles) {
            double charge = v.parkingCharge();
            total += charge;
            System.out.printf("%s: %.2f%n", v.getType(), charge);
        }
        System.out.printf("Total: %.2f%n", total);
    }

    static List<String> readInput() throws Exception {
        for (int i = 0; i < 10 && System.in.available() == 0; i++) Thread.sleep(20);
        List<String> lines = new ArrayList<String>();
        if (System.in.available() > 0) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = reader.readLine()) != null) if (!line.trim().isEmpty()) lines.add(line);
        }
        if (lines.isEmpty()) {
            System.err.println("(no input given - using the sample input)");
            for (String line : SAMPLE.split("\n")) lines.add(line);
        }
        return lines;
    }
}
