import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Week 8 Assignment 5 - The Streaming Plan Renewal Reminder (polymorphism + LocalDate.plusDays)
 * Basic 30 days, Standard 90 days, Premium 365 days. LocalDate handles leap years and year changes.
 */
public class A5_StreamingPlanRenewal {

    static final String SAMPLE = "4\nBASIC Asha 2024-01-15\nSTANDARD Ravi 2024-02-01\nPREMIUM Neha 2024-03-10\nBASIC Kiran 2024-12-20";

    static abstract class Subscription {
        protected final String name;
        protected final LocalDate startDate;
        Subscription(String name, LocalDate startDate) { this.name = name; this.startDate = startDate; }
        abstract int validityDays();
        LocalDate renewalDate() { return startDate.plusDays(validityDays()); }
        String getName() { return name; }
    }

    static class BasicPlan extends Subscription {
        BasicPlan(String n, LocalDate d) { super(n, d); }
        int validityDays() { return 30; }
    }

    static class StandardPlan extends Subscription {
        StandardPlan(String n, LocalDate d) { super(n, d); }
        int validityDays() { return 90; }
    }

    static class PremiumPlan extends Subscription {
        PremiumPlan(String n, LocalDate d) { super(n, d); }
        int validityDays() { return 365; }
    }

    static Subscription create(String type, String name, LocalDate start) {
        switch (type.toUpperCase()) {
            case "BASIC": return new BasicPlan(name, start);
            case "STANDARD": return new StandardPlan(name, start);
            case "PREMIUM": return new PremiumPlan(name, start);
            default: throw new IllegalArgumentException("Unknown plan: " + type);
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = readInput();
        int n = Integer.parseInt(lines.get(0).trim());
        List<Subscription> subscribers = new ArrayList<Subscription>();
        for (int i = 1; i <= n; i++) {
            String[] p = lines.get(i).trim().split("\\s+");
            subscribers.add(create(p[0], p[1], LocalDate.parse(p[2])));
        }
        for (Subscription s : subscribers) {
            System.out.println(s.getName() + ": " + s.renewalDate());
        }
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
