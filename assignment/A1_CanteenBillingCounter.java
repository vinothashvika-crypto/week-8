import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Week 8 Assignment 1 - The Canteen Billing Counter (polymorphism)
 * Students 10% off, staff 5% off, guests pay full amount + Rs 10 service charge.
 */
public class A1_CanteenBillingCounter {

    static final String SAMPLE = "3\nSTUDENT 200\nSTAFF 300\nGUEST 150";

    static abstract class Customer {
        protected final double billAmount;
        Customer(double billAmount) { this.billAmount = billAmount; }
        abstract String getType();
        abstract double finalAmount();
    }

    static class Student extends Customer {
        Student(double amount) { super(amount); }
        String getType() { return "STUDENT"; }
        double finalAmount() { return billAmount * 0.90; }
    }

    static class Staff extends Customer {
        Staff(double amount) { super(amount); }
        String getType() { return "STAFF"; }
        double finalAmount() { return billAmount * 0.95; }
    }

    static class Guest extends Customer {
        Guest(double amount) { super(amount); }
        String getType() { return "GUEST"; }
        double finalAmount() { return billAmount + 10; }
    }

    static Customer create(String type, double amount) {
        switch (type.toUpperCase()) {
            case "STUDENT": return new Student(amount);
            case "STAFF": return new Staff(amount);
            case "GUEST": return new Guest(amount);
            default: throw new IllegalArgumentException("Unknown customer type: " + type);
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = readInput();
        int n = Integer.parseInt(lines.get(0).trim());
        List<Customer> bills = new ArrayList<Customer>();
        for (int i = 1; i <= n; i++) {
            String[] p = lines.get(i).trim().split("\\s+");
            bills.add(create(p[0], Double.parseDouble(p[1])));
        }
        double total = 0;
        for (Customer c : bills) {
            double amount = c.finalAmount();
            total += amount;
            System.out.printf("%s: %.2f%n", c.getType(), amount);
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
