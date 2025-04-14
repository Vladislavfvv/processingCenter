package util;

public class CardValidator {

    public static boolean validateCardNumber(String cardNumber) {
        // Проверяем, что строка не null и состоит только из цифр
        if (cardNumber == null || !cardNumber.matches("\\d+") || cardNumber.length() != 16) {
            return false; // Если null или есть нецифровые символы или мало цифр — номер некорректен
        }

        int sum = 0; // Общая сумма всех пройденных цифр
        boolean flag = false; // Флаг: нужно ли удваивать текущую цифру

        // Проходим по всем цифрам номера карты справа налево (с конца к началу)
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            // Получаем числовое значение символа по индексу i
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            // Если нужно удваивать цифру
            if (flag) {
                digit *= 2; // Удваиваем

                // Если после удвоения больше 9 — отнимаем 9
                if (digit > 9) {
                    digit -= 9;
                }
            }

            // Прибавляем обработанное значение к общей сумме
            sum += digit;

            // Переключаем флаг: следующая цифра будет удваиваться или нет
            flag = !flag;
        }

        // Если сумма кратна 10 — номер карты корректен
        return sum % 10 == 0;
    }
}
