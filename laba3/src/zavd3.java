class AtmException extends Exception {
    public AtmException(String message) {
        super(message);
    }
}

class LimitExceededException extends AtmException {
    public LimitExceededException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends AtmException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}


class ATM {
    private final int[] DENOMINATIONS = {500, 200, 100, 50, 20, 10, 5, 2, 1};
    private int[] counts = new int[9];

    private int maxDispenseAmount;
    private int maxDispenseNotes;

    public ATM(int maxDispenseAmount, int maxDispenseNotes) {
        this.maxDispenseAmount = maxDispenseAmount;
        this.maxDispenseNotes = maxDispenseNotes;
    }

    public void initializeMoney(int[] initialCounts) {
        if (initialCounts.length == 9) {
            for (int i = 0; i < 9; i++) {
                this.counts[i] += initialCounts[i];
            }
        }
    }

    public void insertBanknotes(int denomination, int count) {
        for (int i = 0; i < DENOMINATIONS.length; i++) {
            if (DENOMINATIONS[i] == denomination) {
                counts[i] += count;
                System.out.println("Успішно внесено: " + count + " купюр номіналом " + denomination + " грн.");
                return;
            }
        }
        System.out.println("Помилка: Невідомий номінал " + denomination);
    }

    public void withdrawMoney(int amount) throws AtmException {
        if (amount > maxDispenseAmount) {
            throw new LimitExceededException("Перевищено максимальну суму видачі за один раз (" + maxDispenseAmount + " грн).");
        }

        int[] toDispense = new int[9];
        int remainingAmount = amount;
        int totalNotesToDispense = 0;

        for (int i = 0; i < DENOMINATIONS.length; i++) {
            if (remainingAmount <= 0) break;

            int denom = DENOMINATIONS[i];
            int availableNotes = counts[i];
            int requiredNotes = remainingAmount / denom;
            int notesToTake = Math.min(requiredNotes, availableNotes);

            toDispense[i] = notesToTake;
            remainingAmount -= notesToTake * denom;
            totalNotesToDispense += notesToTake;
        }

        if (remainingAmount > 0) {
            throw new InsufficientFundsException("У банкоматі недостатньо коштів або немає потрібних дрібних купюр для видачі " + amount + " грн.");
        }

        if (totalNotesToDispense > maxDispenseNotes) {
            throw new LimitExceededException("Для видачі цієї суми потрібно " + totalNotesToDispense + " купюр. Максимальний ліміт: " + maxDispenseNotes + " купюр.");
        }

        System.out.println("Видача " + amount + " грн успішна. Видані купюри:");
        for (int i = 0; i < DENOMINATIONS.length; i++) {
            counts[i] -= toDispense[i];
            if (toDispense[i] > 0) {
                System.out.println("- " + DENOMINATIONS[i] + " грн: " + toDispense[i] + " шт.");
            }
        }
    }

    public int getTotalMoney() {
        int total = 0;
        for (int i = 0; i < DENOMINATIONS.length; i++) {
            total += DENOMINATIONS[i] * counts[i];
        }
        return total;
    }
}


class Bank {
    private ATM[] atms;
    private int atmCount;

    public Bank(int atmCount) {
        this.atmCount = atmCount;
        this.atms = new ATM[atmCount];
    }

    public void initializeNetwork(int maxAmount, int maxNotes, int[] initialCountsPerAtm) {
        for (int i = 0; i < atmCount; i++) {
            atms[i] = new ATM(maxAmount, maxNotes);
            atms[i].initializeMoney(initialCountsPerAtm);
        }
        System.out.println("Мережа з " + atmCount + " банкоматів успішно ініціалізована.");
    }

    public int getTotalNetworkMoney() {
        int total = 0;
        for (int i = 0; i < atmCount; i++) {
            total += atms[i].getTotalMoney();
        }
        return total;
    }

    public ATM getAtm(int index) {
        if (index >= 0 && index < atmCount) {
            return atms[index];
        }
        return null;
    }
}


public class zavd3 {
    public static void main(String[] args) {
        Bank myBank = new Bank(3);
        int[] initialLoad = {10, 20, 50, 100, 100, 100, 100, 100, 100};

        myBank.initializeNetwork(10000, 40, initialLoad);

        System.out.println("Загальна сума у мережі банку: " + myBank.getTotalNetworkMoney() + " грн.\n");

        ATM atm1 = myBank.getAtm(0);

        System.out.println("--- ТЕСТ: Ручне внесення грошей ---");
        atm1.insertBanknotes(500, 2);
        atm1.insertBanknotes(300, 1);

        System.out.println("\n--- ТЕСТ: Успішне зняття грошей ---");
        try {
            atm1.withdrawMoney(1878);
        } catch (AtmException e) {
            System.out.println("Помилка банкомата: " + e.getMessage());
        }

        System.out.println("\n--- ТЕСТ: Перевищення ліміту суми ---");
        try {
            atm1.withdrawMoney(15000);
        } catch (AtmException e) {
            System.out.println("Помилка банкомата: " + e.getMessage());
        }

        System.out.println("\n--- ТЕСТ: Перевищення кількості купюр ---");
        try {
            atm1.withdrawMoney(9999);
        } catch (AtmException e) {
            System.out.println("Помилка банкомата: " + e.getMessage());
        }

        System.out.println("\nЗагальна сума у мережі після операцій: " + myBank.getTotalNetworkMoney() + " грн.");
    }
}