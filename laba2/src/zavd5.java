public class zavd5 {
    public static void main(String[] args) {
        String text = "Контакти для зв'язку: mymail@gmail.com, badmail@mail.ru, university@kpi.ua, spam@yandex.ru, work@ukr.net.";

        System.out.println("=== Початковий рядок ===");
        System.out.println(text);

        String cleanedText = removeRuEmails(text);

        System.out.println("\n=== Рядок після очищення ===");
        System.out.println(cleanedText);
    }

    public static String removeRuEmails(String text) {
        String regex = "\\b[\\w.-]+@[\\w.-]+\\.ru\\b(?:,\\s*)?";

        return text.replaceAll(regex, "");
    }
}