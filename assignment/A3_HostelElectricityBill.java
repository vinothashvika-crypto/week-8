import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Week 8 Assignment 3 - The Hostel Electricity Bill (polymorphism)
 * The shared room stores its own occupant count, so the main loop still treats every room the same.
 */
public class A3_HostelElectricityBill {

    static final String SAMPLE = "3\nSINGLE 120\nSHARED 150 3\nAC 100";

    static abstract class Room {
        protected final int units;
        Room(int units) { this.units = units; }
        abstract String getType();
        abstract double calculateBill();
    }

    static class SingleRoom extends Room {
        SingleRoom(int u) { super(u); }
        String getType() { return "SINGLE"; }
        double calculateBill() { return 8.0 * units; }
    }

    static class SharedRoom extends Room {
        private final int occupants;
        SharedRoom(int u, int occupants) { super(u); this.occupants = occupants; }
        String getType() { return "SHARED"; }
        double calculateBill() { return 6.0 * units / occupants; } // amount each occupant pays
    }

    static class AcRoom extends Room {
        AcRoom(int u) { super(u); }
        String getType() { return "AC"; }
        double calculateBill() { return 10.0 * units + 200; }
    }

    static Room create(String[] p) {
        int units = Integer.parseInt(p[1]);
        switch (p[0].toUpperCase()) {
            case "SINGLE": return new SingleRoom(units);
            case "SHARED": return new SharedRoom(units, Integer.parseInt(p[2]));
            case "AC": return new AcRoom(units);
            default: throw new IllegalArgumentException("Unknown room type: " + p[0]);
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = readInput();
        int n = Integer.parseInt(lines.get(0).trim());
        List<Room> rooms = new ArrayList<Room>();
        for (int i = 1; i <= n; i++) rooms.add(create(lines.get(i).trim().split("\\s+")));

        double total = 0;
        for (Room r : rooms) {
            double bill = r.calculateBill();
            total += bill;
            System.out.printf("%s: %.2f%n", r.getType(), bill);
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
