import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Week 8 Practice 3 - Delivery Fee Calculator (polymorphism)
 *
 * Note: this follows the business rules written in the sheet. The sheet's sample output
 * (EXPRESS 29.00, INTERNATIONAL 155.00) does not match those rules - with the written rules
 * EXPRESS 5kg/20km = 15 + 5*1.00 + 20*0.20 = 24.00 and
 * INTERNATIONAL 20kg/100km + 30 customs = 25 + 40 + 50 + 30 = 145.00.
 */
public class P3_DeliveryFeeCalculator {

    static final String SAMPLE = "3\nSTANDARD 10 50\nEXPRESS 5 20\nINTERNATIONAL 20 100 30";

    static abstract class Delivery {
        protected final double weight;
        protected final double distance;
        Delivery(double weight, double distance) { this.weight = weight; this.distance = distance; }
        abstract String getType();
        abstract double calculateFee();
    }

    static class StandardDelivery extends Delivery {
        StandardDelivery(double w, double d) { super(w, d); }
        String getType() { return "STANDARD"; }
        double calculateFee() { return 5 + 0.50 * weight + 0.10 * distance; }
    }

    static class ExpressDelivery extends Delivery {
        ExpressDelivery(double w, double d) { super(w, d); }
        String getType() { return "EXPRESS"; }
        double calculateFee() { return 15 + 1.00 * weight + 0.20 * distance; }
    }

    static class InternationalDelivery extends Delivery {
        private final double customsFee;
        InternationalDelivery(double w, double d, double customsFee) { super(w, d); this.customsFee = customsFee; }
        String getType() { return "INTERNATIONAL"; }
        double calculateFee() { return 25 + 2.00 * weight + 0.50 * distance + customsFee; }
    }

    static Delivery create(String[] p) {
        double weight = Double.parseDouble(p[1]);
        double distance = Double.parseDouble(p[2]);
        switch (p[0].toUpperCase()) {
            case "STANDARD": return new StandardDelivery(weight, distance);
            case "EXPRESS": return new ExpressDelivery(weight, distance);
            case "INTERNATIONAL": return new InternationalDelivery(weight, distance, Double.parseDouble(p[3]));
            default: throw new IllegalArgumentException("Unknown delivery type: " + p[0]);
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = readInput();
        int n = Integer.parseInt(lines.get(0).trim());
        List<Delivery> deliveries = new ArrayList<Delivery>();
        for (int i = 1; i <= n; i++) deliveries.add(create(lines.get(i).trim().split("\\s+")));

        double total = 0;
        for (Delivery d : deliveries) {
            double fee = d.calculateFee();
            total += fee;
            System.out.printf("%s: %.2f%n", d.getType(), fee);
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
