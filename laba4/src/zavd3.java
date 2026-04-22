import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

interface LibraryItem {
    String getTitle();
    void printDetails();
    boolean matchesAuthor(String author);
}


class Book implements LibraryItem {
    private String title;
    private String author;
    private String genre;
    private int pages;

    public Book(String title, String author, String genre, int pages) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.pages = pages;
    }

    @Override
    public String getTitle() { return title; }

    @Override
    public void printDetails() {
        System.out.printf("[Книга] '%s' | Автор: %s | Жанр: %s | Сторінок: %d\n", title, author, genre, pages);
    }

    @Override
    public boolean matchesAuthor(String searchAuthor) {
        return this.author.toLowerCase().contains(searchAuthor.toLowerCase());
    }
}

class Newspaper implements LibraryItem {
    private String title;
    private LocalDate releaseDate;
    private List<String> headlines;

    public Newspaper(String title, LocalDate releaseDate, List<String> headlines) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.headlines = headlines;
    }

    @Override
    public String getTitle() { return title; }

    @Override
    public void printDetails() {
        System.out.printf("[Газета] '%s' | Дата: %s | Головні теми: %s\n", title, releaseDate, String.join(", ", headlines));
    }

    @Override
    public boolean matchesAuthor(String searchAuthor) {
        return false;
    }
}

class Almanac implements LibraryItem {
    private String title;
    private List<Book> works;

    public Almanac(String title, List<Book> works) {
        this.title = title;
        this.works = works;
    }

    @Override
    public String getTitle() { return title; }

    @Override
    public void printDetails() {
        System.out.printf("[Альманах] '%s' | Кількість творів: %d\n", title, works.size());
        for (Book b : works) {
            System.out.print("   -> ");
            b.printDetails();
        }
    }

    @Override
    public boolean matchesAuthor(String searchAuthor) {
        for (Book b : works) {
            if (b.matchesAuthor(searchAuthor)) return true;
        }
        return false;
    }
}

class LibraryCatalog {
    private List<LibraryItem> catalog = new ArrayList<>();
    private Random random = new Random();

    public void initTestData() {
        addItem(new Book("Тіні забутих предків", "Михайло Коцюбинський", "Повість", 120));
        addItem(new Newspaper("Голос України", LocalDate.now(), Arrays.asList("Новини політики", "Економіка сьогодні")));

        List<Book> almanacBooks = new ArrayList<>();
        almanacBooks.add(new Book("Кайдашева сім'я", "Іван Нечуй-Левицький", "Повість", 200));
        almanacBooks.add(new Book("Intermezzo", "Михайло Коцюбинський", "Новела", 30));
        addItem(new Almanac("Українська класика", almanacBooks));

        System.out.println("Каталог ініціалізовано тестовими даними.");
    }

    public void addItem(LibraryItem item) {
        catalog.add(item);
    }

    public void addRandomItem() {
        int type = random.nextInt(3);
        if (type == 0) {
            addItem(new Book("Випадкова Книга " + random.nextInt(100), "Невідомий Автор", "Фантастика", random.nextInt(300) + 50));
            System.out.println("Додано випадкову книгу.");
        } else if (type == 1) {
            addItem(new Newspaper("Daily News " + random.nextInt(100), LocalDate.now(), Arrays.asList("Спорт", "Погода")));
            System.out.println("Додано випадкову газету.");
        } else {
            List<Book> randomBooks = Arrays.asList(new Book("Твір 1", "Автор А", "Драма", 100));
            addItem(new Almanac("Випадковий Альманах " + random.nextInt(100), randomBooks));
            System.out.println("Додано випадковий альманах.");
        }
    }

    public void removeByTitle(String title) {
        boolean removed = catalog.removeIf(item -> item.getTitle().equalsIgnoreCase(title));
        if (removed) {
            System.out.println("🗑Видалено об'єкт з назвою: '" + title + "'");
        } else {
            System.out.println("Об'єкт з назвою '" + title + "' не знайдено для видалення.");
        }
    }

    public void printCatalog() {
        System.out.println("\n=== КАТАЛОГ БІБЛІОТЕКИ (" + catalog.size() + " об'єктів) ===");
        for (LibraryItem item : catalog) {
            item.printDetails();
        }
        System.out.println("==========================\n");
    }

    public void searchByTitle(String title) {
        System.out.println("Результати пошуку за назвою '" + title + "':");
        boolean found = false;
        for (LibraryItem item : catalog) {
            if (item.getTitle().toLowerCase().contains(title.toLowerCase())) {
                item.printDetails();
                found = true;
            }
        }
        if (!found) System.out.println("   Нічого не знайдено.");
    }

    public void searchByAuthor(String author) {
        System.out.println("Результати пошуку за автором '" + author + "':");
        boolean found = false;
        for (LibraryItem item : catalog) {
            if (item.matchesAuthor(author)) {
                item.printDetails();
                found = true;
            }
        }
        if (!found) System.out.println("   Нічого не знайдено.");
    }
}

public class zavd3 {
    public static void main(String[] args) {
        LibraryCatalog myLibrary = new LibraryCatalog();

        myLibrary.initTestData();
        myLibrary.addRandomItem();
        myLibrary.addRandomItem();

        myLibrary.printCatalog();

        myLibrary.searchByTitle("Голос");
        System.out.println();

        myLibrary.searchByAuthor("Коцюбинський");
        System.out.println();

        myLibrary.removeByTitle("Тіні забутих предків");
        myLibrary.searchByTitle("Тіні забутих предків");
    }
}