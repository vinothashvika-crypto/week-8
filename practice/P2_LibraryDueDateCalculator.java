import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Week 8 Practice 2 - Library Item Due Date Calculator (polymorphism + java.time.LocalDate)
 * Current date is fixed at 2023-10-26 as the problem says.
 */
public class P2_LibraryDueDateCalculator {

    static final LocalDate CURRENT_DATE = LocalDate.of(2023, 10, 26);
    static final String SAMPLE = "3\nBOOK \"1984\"\nDVD \"The Matrix\"\nMAGAZINE \"Forbes Issue 500\"";

    static abstract class LibraryItem {
        protected final String title;
        LibraryItem(String title) { this.title = title; }
        abstract int getLoanDays();
        LocalDate getDueDate(LocalDate from) { return from.plusDays(getLoanDays()); }
        String getTitle() { return title; }
    }

    static class Book extends LibraryItem {
        Book(String title) { super(title); }
        int getLoanDays() { return 14; }
    }

    static class Dvd extends LibraryItem {
        Dvd(String title) { super(title); }
        int getLoanDays() { return 7; }
    }

    static class Magazine extends LibraryItem {
        Magazine(String title) { super(title); }
        int getLoanDays() { return 3; }
    }

    static LibraryItem create(String type, String title) {
        switch (type.toUpperCase()) {
            case "BOOK": return new Book(title);
            case "DVD": return new Dvd(title);
            case "MAGAZINE": return new Magazine(title);
            default: throw new IllegalArgumentException("Unknown item type: " + type);
        }
    }

    // Splits a line into words, keeping "quoted text" together
    static List<String> tokens(String line) {
        List<String> result = new ArrayList<String>();
        Matcher m = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(line);
        while (m.find()) result.add(m.group(1) != null ? m.group(1) : m.group(2));
        return result;
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = readInput();
        int n = Integer.parseInt(lines.get(0).trim());
        List<LibraryItem> items = new ArrayList<LibraryItem>();
        for (int i = 1; i <= n; i++) {
            List<String> t = tokens(lines.get(i));
            items.add(create(t.get(0), t.get(1)));
        }
        for (LibraryItem item : items) {
            System.out.println(item.getTitle() + ": " + item.getDueDate(CURRENT_DATE)); // LocalDate prints YYYY-MM-DD
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
