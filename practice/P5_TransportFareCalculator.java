import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Week 8 Practice 5 - Public Transport Fare Calculator (polymorphism)
 */
public class P5_TransportFareCalculator {

    static final String SAMPLE = "3\nBUS 15\nTRAIN 50\nMETRO 10 1.5";

    static abstract class Journey {
        protected final double distance;
        Journey(double distance) { this.distance = distance; }
        abstract String getType();
        abstract double calculateFare();
    }

    static class BusJourney extends Journey {
        BusJourney(double d) { super(d); }
        String getType() { return "BUS"; }
        double calculateFare() { return Math.min(2 + 0.10 * distance, 10); } // max fare $10
    }

    static class TrainJourney extends Journey {
        TrainJourney(double d) { super(d); }
        String getType() { return "TRAIN"; }
        double calculateFare() { return 3 + 0.15 * distance; }
    }

    static class MetroJourney extends Journey {
        private final double peakHourFactor;
        MetroJourney(double d, double peakHourFactor) { super(d); this.peakHourFactor = peakHourFactor; }
        String getType() { return "METRO"; }
        double calculateFare() { return (1.50 + 0.20 * distance) * peakHourFactor; }
    }

    static Journey create(String[] p) {
        double distance = Double.parseDouble(p[1]);
        switch (p[0].toUpperCase()) {
            case "BUS": return new BusJourney(distance);
            case "TRAIN": return new TrainJourney(distance);
            case "METRO": return new MetroJourney(distance, Double.parseDouble(p[2]));
            default: throw new IllegalArgumentException("Unknown transport type: " + p[0]);
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = readInput();
        int n = Integer.parseInt(lines.get(0).trim());
        List<Journey> journeys = new ArrayList<Journey>();
        for (int i = 1; i <= n; i++) journeys.add(create(lines.get(i).trim().split("\\s+")));

        double total = 0;
        for (Journey j : journeys) {
            double fare = j.calculateFare();
            total += fare;
            System.out.printf("%s: %.2f%n", j.getType(), fare);
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
