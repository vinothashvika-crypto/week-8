import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Week 8 Assignment 4 - The Festival Bonus Calculator (inheritance + polymorphism)
 * Full-time 10% of salary, part-time 5%, interns a fixed Rs 2000.
 */
public class A4_FestivalBonusCalculator {

    static final String SAMPLE = "3\nFULLTIME Asha 50000\nPARTTIME Ravi 30000\nINTERN Neha 15000";

    static abstract class Employee {
        protected final String name;          // shared details live in the base class
        protected final double monthlySalary;
        Employee(String name, double monthlySalary) { this.name = name; this.monthlySalary = monthlySalary; }
        String getName() { return name; }
        abstract double calculateBonus();
    }

    static class FullTimeEmployee extends Employee {
        FullTimeEmployee(String n, double s) { super(n, s); }
        double calculateBonus() { return monthlySalary * 0.10; }
    }

    static class PartTimeEmployee extends Employee {
        PartTimeEmployee(String n, double s) { super(n, s); }
        double calculateBonus() { return monthlySalary * 0.05; }
    }

    static class Intern extends Employee {
        Intern(String n, double s) { super(n, s); }
        double calculateBonus() { return 2000; }
    }

    static Employee create(String type, String name, double salary) {
        switch (type.toUpperCase()) {
            case "FULLTIME": return new FullTimeEmployee(name, salary);
            case "PARTTIME": return new PartTimeEmployee(name, salary);
            case "INTERN": return new Intern(name, salary);
            default: throw new IllegalArgumentException("Unknown employee type: " + type);
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = readInput();
        int n = Integer.parseInt(lines.get(0).trim());
        List<Employee> employees = new ArrayList<Employee>();
        for (int i = 1; i <= n; i++) {
            String[] p = lines.get(i).trim().split("\\s+");
            employees.add(create(p[0], p[1], Double.parseDouble(p[2])));
        }
        double total = 0;
        for (Employee e : employees) {
            double bonus = e.calculateBonus();
            total += bonus;
            System.out.printf("%s: %.2f%n", e.getName(), bonus);
        }
        System.out.printf("Total Bonus: %.2f%n", total);
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
