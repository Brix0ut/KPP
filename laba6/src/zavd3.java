import java.util.PriorityQueue;
import java.util.Scanner;

class Customer implements Comparable<Customer> {
    private String name;
    private boolean hasReservation;
    private long arrivalTime;

    public Customer(String name, boolean hasReservation) {
        this.name = name;
        this.hasReservation = hasReservation;
        this.arrivalTime = System.nanoTime();
    }

    public String getName() { return name; }
    public boolean hasReservation() { return hasReservation; }

    @Override
    public int compareTo(Customer other) {
        if (this.hasReservation && !other.hasReservation) return -1;
        if (!this.hasReservation && other.hasReservation) return 1;
        return Long.compare(this.arrivalTime, other.arrivalTime);
    }

    @Override
    public String toString() {
        return name + (hasReservation ? " [Резерв]" : " [Звичайна черга]");
    }
}

public class zavd3 {
    private static PriorityQueue<Customer> queue = new PriorityQueue<>();
    private static int freeTables = 2;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== КАФЕ (Вільних столиків: " + freeTables + ") ===");
            System.out.println("1. Прийшов відвідувач (Звичайна черга)");
            System.out.println("2. Прийшов відвідувач (Із резервом)");
            System.out.println("3. Звільнився столик");
            System.out.println("4. Показати поточну чергу");
            System.out.println("5. Знайти клієнта в черзі");
            System.out.println("0. Вихід");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1": addCustomer(false); break;
                case "2": addCustomer(true); break;
                case "3": freeTable(); break;
                case "4": showQueue(); break;
                case "5": searchCustomer(); break;
                case "0": running = false; break;
                default: System.out.println("Невірна команда!");
            }
        }
    }

    private static void addCustomer(boolean hasReservation) {
        System.out.print("Введіть ім'я відвідувача: ");
        String name = scanner.nextLine();
        Customer customer = new Customer(name, hasReservation);

        if (freeTables > 0 && queue.isEmpty()) {
            freeTables--;
            System.out.println("Відвідувач " + name + " одразу зайняв столик!");
        } else {
            queue.add(customer);
            if (hasReservation) {
                System.out.println("Усі столики зайняті, але " + name + " стає ПЕРШИМ у чергу (позачергово)!");
            } else {
                System.out.println("Відвідувач " + name + " став у кінець черги.");
            }
        }
    }

    private static void freeTable() {
        if (queue.isEmpty()) {
            freeTables++;
            System.out.println("Столик звільнився. Черга порожня, столик чекає на гостей.");
        } else {
            Customer next = queue.poll();
            System.out.println("Столик звільнився! За нього сідає: " + next.getName());
        }
    }

    private static void showQueue() {
        if (queue.isEmpty()) {
            System.out.println("Черга порожня.");
        } else {
            System.out.println("\n--- ПОТОЧНА ЧЕРГА (" + queue.size() + " людей) ---");

            queue.stream()
                    .sorted()
                    .forEach(c -> System.out.println(" - " + c));

            long reservedCount = queue.stream().filter(Customer::hasReservation).count();
            System.out.println("-----------------------------------");
            System.out.println("Статистика: з резервом = " + reservedCount +
                    ", звичайних = " + (queue.size() - reservedCount));
        }
    }

    private static void searchCustomer() {
        System.out.print("Введіть ім'я для пошуку: ");
        String searchName = scanner.nextLine();

        boolean found = queue.stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(searchName));

        if (found) {
            System.out.println("Відвідувач " + searchName + " наразі стоїть у черзі.");
        } else {
            System.out.println("Відвідувача з таким ім'ям у черзі немає.");
        }
    }
}