import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Week 8 Practice 4 - Examination Question Grader (polymorphism)
 * MCQ / TF: full points on an exact match. ESSAY: 2+ keywords = 75%, 1 keyword = 50%, else 0.
 */
public class P4_ExamQuestionGrader {

    static final String SAMPLE = "4\n"
            + "MCQ \"What is the capital of France?\" \"Paris\" \"Paris\" 10\n"
            + "TF \"The Earth is flat?\" \"False\" \"True\" 5\n"
            + "ESSAY \"Name two primary OOP principles.\" \"Inheritance, Polymorphism, Encapsulation\" \"Polymorphism is one.\" 20\n"
            + "ESSAY \"Describe abstraction and composition.\" \"Abstraction, Composition\" \"I talked about abstraction.\" 15";

    static abstract class Question {
        protected final String text, correctAnswer, studentAnswer;
        protected final int points;
        Question(String text, String correctAnswer, String studentAnswer, int points) {
            this.text = text; this.correctAnswer = correctAnswer; this.studentAnswer = studentAnswer; this.points = points;
        }
        abstract String getType();
        abstract double grade();
    }

    static class McqQuestion extends Question {
        McqQuestion(String t, String c, String s, int p) { super(t, c, s, p); }
        String getType() { return "MCQ"; }
        double grade() { return studentAnswer.trim().equals(correctAnswer.trim()) ? points : 0; }
    }

    static class TrueFalseQuestion extends Question {
        TrueFalseQuestion(String t, String c, String s, int p) { super(t, c, s, p); }
        String getType() { return "TF"; }
        double grade() { return studentAnswer.trim().equals(correctAnswer.trim()) ? points : 0; }
    }

    static class EssayQuestion extends Question {
        EssayQuestion(String t, String c, String s, int p) { super(t, c, s, p); }
        String getType() { return "ESSAY"; }
        double grade() {
            String answer = studentAnswer.toLowerCase();
            int matches = 0;
            for (String keyword : correctAnswer.split(",")) {
                String k = keyword.trim().toLowerCase();
                if (!k.isEmpty() && answer.contains(k)) matches++;
            }
            if (matches >= 2) return points * 0.75;
            if (matches == 1) return points * 0.50;
            return 0;
        }
    }

    static Question create(List<String> t) {
        int points = Integer.parseInt(t.get(4));
        switch (t.get(0).toUpperCase()) {
            case "MCQ": return new McqQuestion(t.get(1), t.get(2), t.get(3), points);
            case "TF": return new TrueFalseQuestion(t.get(1), t.get(2), t.get(3), points);
            case "ESSAY": return new EssayQuestion(t.get(1), t.get(2), t.get(3), points);
            default: throw new IllegalArgumentException("Unknown question type: " + t.get(0));
        }
    }

    static List<String> tokens(String line) {
        List<String> result = new ArrayList<String>();
        Matcher m = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(line);
        while (m.find()) result.add(m.group(1) != null ? m.group(1) : m.group(2));
        return result;
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = readInput();
        int n = Integer.parseInt(lines.get(0).trim());
        List<Question> questions = new ArrayList<Question>();
        for (int i = 1; i <= n; i++) questions.add(create(tokens(lines.get(i))));

        double total = 0;
        for (Question q : questions) {
            double score = q.grade();
            total += score;
            System.out.printf("%s: %.2f%n", q.getType(), score);
        }
        System.out.printf("Total Score: %.2f%n", total);
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
