import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

interface DataManager {
    void saveToFile(Apartment[] apartments, String fileName);
    Apartment[] loadFromFile(String fileName);
}

class Apartment implements Serializable, Comparable<Apartment> {
    // Унікальний ідентифікатор версії серіалізованого класу
    private static final long serialVersionUID = 1L;

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
        this(address, rooms, rentCost, false, null, 0);
    }

    public int getRooms() { return rooms; }
    public double getRentCost() { return rentCost; }

    @Override
    public int compareTo(Apartment other) {
        int roomCompare = Integer.compare(this.rooms, other.rooms);
        if (roomCompare != 0) {
            return roomCompare;
        }
        return Double.compare(this.rentCost, other.rentCost);
    }

    @Override
    public String toString() {
        if (isRented) {
            return String.format("Адреса: %s | Кімнат: %d | Ціна: %.2f грн | Орендовано", address, rooms, rentCost);
        } else {
            return String.format("Адреса: %s | Кімнат: %d | Ціна: %.2f грн | [ВІЛЬНО]", address, rooms, rentCost);
        }
    }
}

class ApartmentDataManager implements DataManager {
    @Override
    public void saveToFile(Apartment[] apartments, String fileName) {
        try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Apartment apt : apartments) {
                if (apt != null) {
                    outStream.writeObject(apt);
                }
            }
            System.out.println("\nДані успішно збережено у файл: " + fileName);
        } catch (IOException e) {
            System.out.println("Помилка запису даних: " + e.getMessage());
        }
    }

    @Override
    public Apartment[] loadFromFile(String fileName) {
        Apartment[] loaded = new Apartment[15];
        int i = 0;
        try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(fileName))) {
            while (true) {
                try {
                    Apartment apt = (Apartment) inStream.readObject();
                    if (i < loaded.length) {
                        loaded[i++] = apt;
                    }
                } catch (EOFException e) {
                    break;
                }
            }
            System.out.println("Дані успішно завантажено з файлу: " + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Не знайдено файл з даними: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Помилка читання даних: " + e.getMessage());
        }
        return Arrays.copyOf(loaded, i);
    }
}

class ApartmentGenerator {
    private static final String[] STREETS = {"вул. Хрещатик", "просп. Перемоги", "вул. Франка"};
    private static final Random random = new Random();

    public static Apartment[] generateArray(int count) {
        Apartment[] arr = new Apartment[count];
        for (int i = 0; i < count; i++) {
            String address = STREETS[random.nextInt(STREETS.length)] + ", буд. " + (random.nextInt(50) + 1);
            int rooms = random.nextInt(4) + 1;
            double cost = 5000 + random.nextInt(10000);
            arr[i] = new Apartment(address, rooms, cost);
        }
        return arr;
    }
}

public class zavd1 {
    public static void main(String[] args) {
        String fileName = "ApartmentsData.dat";
        DataManager dataManager = new ApartmentDataManager();

        Apartment[] originalApartments = ApartmentGenerator.generateArray(5);
        System.out.println("=== ЗГЕНЕРОВАНІ ДАНІ ===");
        for (Apartment apt : originalApartments) System.out.println(apt);

        dataManager.saveToFile(originalApartments, fileName);

        Apartment[] loadedApartments = dataManager.loadFromFile(fileName);

        Arrays.sort(loadedApartments);

        System.out.println("\n=== ЗАВАНТАЖЕНІ ТА ВІДСОРТОВАНІ ДАНІ (за кімнатами, потім за ціною) ===");
        for (Apartment apt : loadedApartments) {
            System.out.println(apt);
        }
    }
}