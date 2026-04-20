import java.util.Arrays;

public class zavd1 {
    public static void main(String[] args) {
        String text = "Привіт! Це перше речення нашої лабораторної роботи.\n" +
                "Тут знаходиться найдовший рядок у всьому цьому тексті, який ми будемо дублювати.\n" +
                "А це просто короткий рядок.\n" +
                "В цьому тексті є погане слово, і навіть не одне заборонене слово!";

        String[] forbiddenWords = {"погане", "заборонене"};

        System.out.println("=== ПОЧАТКОВИЙ ТЕКСТ ===");
        System.out.println(text);
        System.out.println("========================\n");

        String[] sentences = text.split("[.!?]+");
        int sentenceCount = text.trim().isEmpty() ? 0 : sentences.length;

        String[] words = text.split("[\\s\\p{Punct}]+");
        long wordCount = Arrays.stream(words).filter(w -> !w.isEmpty()).count();

        System.out.println("Кількість речень: " + sentenceCount);
        System.out.println("Кількість слів: " + wordCount + "\n");

        String[] lines = text.split("\\n");
        int longestIndex = 0;
        int maxLength = 0;

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() > maxLength) {
                maxLength = lines[i].length();
                longestIndex = i;
            }
        }

        StringBuilder newTextBuilder = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            newTextBuilder.append(lines[i]).append("\n");
            if (i == longestIndex) {
                newTextBuilder.append(lines[i]).append(" --- [ЦЕ ДУБЛІКАТ]\n");
            }
        }

        String processedText = newTextBuilder.toString();

        for (String word : forbiddenWords) {
            String stars = "*".repeat(word.length());
            processedText = processedText.replaceAll("(?i)\\b" + word + "\\b", stars);
        }

        System.out.println("=== РЕЗУЛЬТАТ ОБРОБКИ ===");
        System.out.println(processedText);
    }
}