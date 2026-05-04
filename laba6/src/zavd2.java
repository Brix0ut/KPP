import java.util.*;
import java.util.stream.Collectors;

class DictionaryEntry {
    private String word;
    private List<String> translations;
    private int accessCount;

    public DictionaryEntry(String word, List<String> translations) {
        this.word = word;
        this.translations = new ArrayList<>(translations);
        this.accessCount = 0;
    }

    public String getWord() { return word; }
    public List<String> getTranslations() { return translations; }
    public int getAccessCount() { return accessCount; }

    public void incrementAccessCount() { this.accessCount++; }
    public void setWord(String word) { this.word = word; }
}

public class zavd2 {
    private static Map<String, DictionaryEntry> dictionary = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initDictionary();

        boolean running = true;
        while (running) {
            System.out.println("\n=== АНГЛО-УКРАЇНСЬКИЙ СЛОВНИК ===");
            System.out.println("1. Знайти слово та його переклад");
            System.out.println("2. Додати нове слово");
            System.out.println("3. Замінити існуюче слово");
            System.out.println("4. Видалити слово");
            System.out.println("5. Додати переклад до існуючого слова");
            System.out.println("6. Замінити переклад слова");
            System.out.println("7. Видалити переклад слова");
            System.out.println("8. ТОП-10 найпопулярніших слів");
            System.out.println("9. ТОП-10 найменш популярних слів");
            System.out.println("0. Вихід");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": searchWord(); break;
                case "2": addWord(); break;
                case "3": replaceWord(); break;
                case "4": deleteWord(); break;
                case "5": addTranslation(); break;
                case "6": replaceTranslation(); break;
                case "7": deleteTranslation(); break;
                case "8": showTop10Popular(); break;
                case "9": showTop10Unpopular(); break;
                case "0": running = false; break;
                default: System.out.println("Невірна команда!");
            }
        }
    }


    private static void searchWord() {
        System.out.print("Введіть слово англійською: ");
        String word = scanner.nextLine().toLowerCase();

        DictionaryEntry entry = dictionary.get(word);
        if (entry != null) {
            entry.incrementAccessCount();
            System.out.println("Переклад: " + String.join(", ", entry.getTranslations()));
        } else {
            System.out.println("Слово не знайдено в словнику.");
        }
    }

    private static void addWord() {
        System.out.print("Введіть нове слово англійською: ");
        String word = scanner.nextLine().toLowerCase();

        if (dictionary.containsKey(word)) {
            System.out.println("Таке слово вже існує!");
            return;
        }

        System.out.print("Введіть переклад (або кілька через кому): ");
        String transInput = scanner.nextLine();
        List<String> translations = Arrays.stream(transInput.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        dictionary.put(word, new DictionaryEntry(word, translations));
        System.out.println("Слово успішно додано.");
    }

    private static void replaceWord() {
        System.out.print("Введіть слово, яке хочете замінити: ");
        String oldWord = scanner.nextLine().toLowerCase();

        if (!dictionary.containsKey(oldWord)) {
            System.out.println("Слово не знайдено.");
            return;
        }

        System.out.print("Введіть нове слово на заміну: ");
        String newWord = scanner.nextLine().toLowerCase();

        DictionaryEntry entry = dictionary.remove(oldWord);
        entry.setWord(newWord);
        dictionary.put(newWord, entry);
        System.out.println("Слово успішно замінено.");
    }

    private static void deleteWord() {
        System.out.print("Введіть слово для видалення: ");
        String word = scanner.nextLine().toLowerCase();
        if (dictionary.remove(word) != null) {
            System.out.println("Слово успішно видалено.");
        } else {
            System.out.println("Слово не знайдено.");
        }
    }

    private static void addTranslation() {
        System.out.print("Введіть слово, до якого додати переклад: ");
        String word = scanner.nextLine().toLowerCase();
        DictionaryEntry entry = dictionary.get(word);

        if (entry != null) {
            System.out.print("Введіть новий переклад: ");
            String newTrans = scanner.nextLine().trim();
            entry.getTranslations().add(newTrans);
            System.out.println("Переклад додано.");
        } else {
            System.out.println("Слово не знайдено.");
        }
    }

    private static void replaceTranslation() {
        System.out.print("Введіть слово: ");
        String word = scanner.nextLine().toLowerCase();
        DictionaryEntry entry = dictionary.get(word);

        if (entry != null) {
            System.out.println("Поточні переклади: " + entry.getTranslations());
            System.out.print("Який переклад хочете замінити?: ");
            String oldTrans = scanner.nextLine().trim();

            if (entry.getTranslations().contains(oldTrans)) {
                System.out.print("Введіть новий переклад: ");
                String newTrans = scanner.nextLine().trim();
                entry.getTranslations().remove(oldTrans);
                entry.getTranslations().add(newTrans);
                System.out.println("Переклад замінено.");
            } else {
                System.out.println("Такого перекладу немає.");
            }
        } else {
            System.out.println("Слово не знайдено.");
        }
    }

    private static void deleteTranslation() {
        System.out.print("Введіть слово: ");
        String word = scanner.nextLine().toLowerCase();
        DictionaryEntry entry = dictionary.get(word);

        if (entry != null) {
            System.out.println("Поточні переклади: " + entry.getTranslations());
            System.out.print("Який переклад хочете видалити?: ");
            String trans = scanner.nextLine().trim();

            if (entry.getTranslations().remove(trans)) {
                System.out.println("Переклад видалено.");
            } else {
                System.out.println("Такого перекладу немає.");
            }
        } else {
            System.out.println("Слово не знайдено.");
        }
    }


    private static void showTop10Popular() {
        System.out.println("\n--- ТОП-10 НАЙПОПУЛЯРНІШИХ СЛІВ ---");
        dictionary.values().stream()
                .sorted((a, b) -> Integer.compare(b.getAccessCount(), a.getAccessCount()))
                .limit(10) // Беремо тільки перші 10
                .forEach(e -> System.out.println(e.getWord() + " (звернень: " + e.getAccessCount() + ")"));
    }

    private static void showTop10Unpopular() {
        System.out.println("\n--- ТОП-10 НАЙМЕНШ ПОПУЛЯРНИХ СЛІВ ---");
        dictionary.values().stream()
                .sorted((a, b) -> Integer.compare(a.getAccessCount(), b.getAccessCount()))
                .limit(10)
                .forEach(e -> System.out.println(e.getWord() + " (звернень: " + e.getAccessCount() + ")"));
    }

    private static void initDictionary() {
        dictionary.put("apple", new DictionaryEntry("apple", Arrays.asList("яблуко")));
        dictionary.put("book", new DictionaryEntry("book", Arrays.asList("книга", "забронювати")));
        dictionary.put("cat", new DictionaryEntry("cat", Arrays.asList("кіт", "кішка")));
        dictionary.put("dog", new DictionaryEntry("dog", Arrays.asList("собака")));
        dictionary.put("hello", new DictionaryEntry("hello", Arrays.asList("привіт", "вітаю")));
        dictionary.put("world", new DictionaryEntry("world", Arrays.asList("світ")));
        dictionary.put("computer", new DictionaryEntry("computer", Arrays.asList("комп'ютер")));
        dictionary.put("java", new DictionaryEntry("java", Arrays.asList("джава", "острів")));
        dictionary.put("sun", new DictionaryEntry("sun", Arrays.asList("сонце")));
        dictionary.put("moon", new DictionaryEntry("moon", Arrays.asList("місяць")));
        dictionary.put("water", new DictionaryEntry("water", Arrays.asList("вода")));
        dictionary.put("fire", new DictionaryEntry("fire", Arrays.asList("вогонь", "пожежа")));

        for (int i = 0; i < 5; i++) dictionary.get("apple").incrementAccessCount();
        for (int i = 0; i < 3; i++) dictionary.get("java").incrementAccessCount();
        for (int i = 0; i < 10; i++) dictionary.get("hello").incrementAccessCount();
    }
}