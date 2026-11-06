import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Week 8 Practice 1 - Payment System Fee Calculation (polymorphism)
 * Each payment type overrides finalAmount(); the main loop treats every payment the same way.
 *
 * Run with input piped in (java P1_PaymentFeeCalculator < input.txt), or with no input to use the sample.
 */
public class P1_PaymentFeeCalculator {

    static final String SAMPLE = "3\nCARD 1000\nWALLET 500\nBANKTRANSFER 2000";

    static abstract class Payment {
        protected final double amount;
        Payment(double amount) { this.amount = amount; }
        abstract String getType();
        abstract double finalAmount();
    }

    static class CardPayment extends Payment {
        CardPayment(double amount) { super(amount); }
        String getType() { return "CARD"; }
        double finalAmount() { return amount * 1.02; }   // 2% fee
    }

    static class WalletPayment extends Payment {
        WalletPayment(double amount) { super(amount); }
        String getType() { return "WALLET"; }
        double finalAmount() { return amount * 1.01; }   // 1% fee
    }

    static class BankTransferPayment extends Payment {
        BankTransferPayment(double amount) { super(amount); }
        String getType() { return "BANKTRANSFER"; }
        double finalAmount() { return amount; }          // no fee
    }

    // The only place that looks at the type text: turning input into objects
    static Payment create(String type, double amount) {
        switch (type.toUpperCase()) {
            case "CARD": return new CardPayment(amount);
            case "WALLET": return new WalletPayment(amount);
            case "BANKTRANSFER": return new BankTransferPayment(amount);
            default: throw new IllegalArgumentException("Unknown payment type: " + type);
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = readInput();
        int n = Integer.parseInt(lines.get(0).trim());
        List<Payment> payments = new ArrayList<Payment>();
        for (int i = 1; i <= n; i++) {
            String[] parts = lines.get(i).trim().split("\\s+");
            payments.add(create(parts[0], Double.parseDouble(parts[1])));
        }

        double total = 0;
        for (Payment p : payments) {            // uniform processing - no type checks
            double value = p.finalAmount();
            total += value;
            System.out.printf("%s: %.2f%n", p.getType(), value);
        }
        System.out.printf("Total: %.2f%n", total);
    }

    // Reads piped/redirected input; falls back to the sample when nothing is provided
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
