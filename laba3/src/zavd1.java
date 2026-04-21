import java.time.LocalDate;
import java.util.Random;

class Apartment {
    private String address;
    private int rooms;
    private double rentCost;
    private boolean isRented;
    private LocalDate rentDate;
    private int rentTermMonths;

    public Apartment(String address, int rooms, double rentCost, boolean isRented, LocalDate rentDate, int rentTermMonths) {
        this.address = address;
        this.rooms = rooms;
        this.rentCost = rentCost;
        this.isRented = isRented;
        this.rentDate = rentDate;
        this.rentTermMonths = rentTermMonths;
    }

    public Apartment(String address, int rooms, double rentCost) {
        this.address = address;
        this.rooms = rooms;
        this.rentCost = rentCost;
        this.isRented = false;
        this.rentDate = null;
        this.rentTermMonths = 0;
    }

    public boolean isRented() { return isRented; }
    public int getRooms() { return rooms; }
    public double getRentCost() { return rentCost; }

    public void printInfo() {
        System.out.println(this.toString());
    }

    public void printInfo(String prefix) {
        System.out.println(prefix + " " + this.toString());
    }

    @Override
    public String toString() {
        if (isRented) {
            return String.format("Адреса: %s | Кімнат: %d | Ціна: %.2f грн | Орендовано з %s на %d міс.",
                    address, rooms, rentCost, rentDate, rentTermMonths);
        } else {
            return String.format("Адреса: %s | Кімнат: %d | Ціна: %.2f грн | [ВІЛЬНЕ ЖИТЛО]",
                    address, rooms, rentCost);
        }
    }
}

class ApartmentGenerator {
    private static final String[] STREETS = {"вул. Хрещатик", "просп. Перемоги", "вул. Франка", "вул. Шевченка", "вул. Лесі Українки"};
    private static final Random random = new Random();

    public static Apartment generateRandom() {
        String address = STREETS[random.nextInt(STREETS.length)] + ", буд. " + (random.nextInt(50) + 1) + ", кв. " + (random.nextInt(100) + 1);
        int rooms = random.nextInt(4) + 1; // 1-4 кімнати
        double cost = 5000 + random.nextInt(15000);
        boolean isRented = random.nextBoolean();

        if (isRented) {
            LocalDate date = LocalDate.now().minusDays(random.nextInt(100));
            int term = random.nextInt(12) + 1;
            return new Apartment(address, rooms, cost, true, date, term);
        } else {
            return new Apartment(address, rooms, cost);
        }
    }

    public static Apartment[] generateArray(int count) {
        Apartment[] arr = new Apartment[count];
        for (int i = 0; i < count; i++) {
            arr[i] = generateRandom();
        }
        return arr;
    }
}

public class zavd1 {
    public static void main(String[] args) {
        Apartment[] apartments = ApartmentGenerator.generateArray(7);

        System.out.println("=== УСІ ПОМЕШКАННЯ В БАЗІ ===");
        for (Apartment apt : apartments) {
            apt.printInfo("[-] ");
        }

        System.out.println("\n=== СПИСОК ВІЛЬНИХ ПОМЕШКАНЬ ===");
        for (Apartment apt : apartments) {
            if (!apt.isRented()) apt.printInfo();
        }

        System.out.println("\n=== СПИСОК ОРЕНДОВАНИХ ПОМЕШКАНЬ ===");
        for (Apartment apt : apartments) {
            if (apt.isRented()) apt.printInfo();
        }

        int targetRooms = 2;
        double maxPrice = 12000.0;

        System.out.println("\n=== ПОШУК ЖИТЛА (Кімнат: " + targetRooms + ", Бюджет до: " + maxPrice + " грн) ===");
        boolean found = false;
        for (Apartment apt : apartments) {
            if (!apt.isRented() && apt.getRooms() == targetRooms && apt.getRentCost() <= maxPrice) {
                apt.printInfo("--> ЗНАЙДЕНО:");
                found = true;
            }
        }

        if (!found) {
            System.out.println("На жаль, за вашими критеріями нічого не знайдено.");
        }
    }
}