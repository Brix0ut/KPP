import java.util.regex.Pattern;

public class zavd4 {
    public static void main(String[] args) {
        String input = "Situation: Motivation, Action! Obligation. WrongWord! noCapitalization. TooLongWordBeforeTion!";

        String[] words = input.split("\\s+");

        String regex = "^[A-Z][a-zA-Z]{1,8}tion[.!:;,]$";

        System.out.println("=== Перевірка слів за шаблоном ===");
        System.out.println("Шаблон вимагає: Велика літера + 1-8 літер + 'tion' + розділовий знак (.!:;,)\n");

        for (String word : words) {
            boolean isMatch = Pattern.matches(regex, word);

            if (isMatch) {
                System.out.println("Підходить:    " + word);
            } else {
                System.out.println("Не підходить: " + word);
            }
        }
    }
}