import java.time.LocalDate;

enum Frequency {
    WEEKLY, MONTHLY, YEARLY
}

class Person {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (нар. " + birthDate + ")";
    }
}

class Article {
    private Person author;
    private String title;
    private double rating;
    private int pages;

    public Article(Person author, String title, double rating, int pages) {
        this.author = author;
        this.title = title;
        this.rating = rating;
        this.pages = pages;
    }

    public int getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return String.format("Стаття: '%s' | Автор: %s | Рейтинг: %.1f | Сторінок: %d",
                title, author.toString(), rating, pages);
    }
}

class Magazine {
    private String magazineName;
    private Frequency frequency;
    private LocalDate releaseDate;
    private int circulation;
    private Article[] articles;

    public Magazine(String magazineName, Frequency frequency, LocalDate releaseDate, int circulation, Article[] articles) {
        this.magazineName = magazineName;
        this.frequency = frequency;
        this.releaseDate = releaseDate;
        this.circulation = circulation;
        this.articles = articles;
    }

    public Article[] getArticles() {
        return articles;
    }

    public String getMagazineName() {
        return magazineName;
    }
}

public class zavd2 {
    public static void main(String[] args) {
        Person author1 = new Person("Тарас", "Шевченко", LocalDate.of(1814, 3, 9));
        Person author2 = new Person("Ліна", "Костенко", LocalDate.of(1930, 3, 19));
        Person author3 = new Person("Сергій", "Жадан", LocalDate.of(1974, 8, 23));

        Article a1 = new Article(author1, "Думки про майбутнє", 4.8, 12);
        Article a2 = new Article(author2, "Поезія і час", 5.0, 8);
        Article a3 = new Article(author3, "Сучасна література", 4.5, 25); // Найбільша стаття!
        Article a4 = new Article(author1, "Історія одного міста", 4.2, 15);
        Article a5 = new Article(author2, "Весняні мотиви", 4.9, 25); // Теж найбільша!

        Article[] mag1Articles = {a1, a2};
        Article[] mag2Articles = {a3, a4, a5};

        Magazine mag1 = new Magazine("Літературний вісник", Frequency.MONTHLY, LocalDate.of(2023, 10, 1), 5000, mag1Articles);
        Magazine mag2 = new Magazine("Сучасник", Frequency.WEEKLY, LocalDate.of(2023, 10, 15), 12000, mag2Articles);

        Magazine[] allMagazines = {mag1, mag2};

        System.out.println("=== ПОШУК НАЙБІЛЬШИХ СТАТЕЙ У ЖУРНАЛАХ ===\n");

        int maxPages = 0;

        for (Magazine mag : allMagazines) {
            for (Article article : mag.getArticles()) {
                if (article.getPages() > maxPages) {
                    maxPages = article.getPages();
                }
            }
        }

        System.out.println("Максимальна кількість сторінок у статті: " + maxPages);
        System.out.println("Статті з такою кількістю сторінок:");

        for (Magazine mag : allMagazines) {
            for (Article article : mag.getArticles()) {
                if (article.getPages() == maxPages) {
                    System.out.println("- " + article.toString() + " (Опубліковано в журналі: '" + mag.getMagazineName() + "')");
                }
            }
        }
    }
}