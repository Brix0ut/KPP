public class zavd2 {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("Програмування на Java - це цікаво!");
        System.out.println("1. Початковий рядок: " + sb);


        String subStr = sb.substring(17, 21);
        System.out.println("\n2. Отриманий підрядок через substring(): " + subStr);

        char[] charArray = new char[4];
        sb.getChars(17, 21, charArray, 0);
        System.out.println("3. Отримані символи через getChars(): " + new String(charArray));

        sb.append(" Згоден?");
        System.out.println("\n4. Після додавання в кінець (append): " + sb);

        sb.insert(17, "мові ");
        System.out.println("5. Після вставки в середину (insert): " + sb);

        sb.delete(31, 37);
        System.out.println("\n6. Після видалення слова (delete): " + sb);

        sb.replace(22, 26, "Python");
        System.out.println("7. Після заміни підрядка (replace): " + sb);
    }
}