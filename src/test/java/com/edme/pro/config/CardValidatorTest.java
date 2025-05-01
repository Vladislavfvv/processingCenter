package com.edme.pro.config;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CardValidatorTest {
    @Test
    void validCardNumber_shouldReturnTrue() {
        // Пример корректного номера карты (прошедшего алгоритм Луна)
        String validCardNumber = "4539 1488 0343 6467"; // Visa тестовая карта
        assertTrue(CardValidator.validateCardNumber(validCardNumber));
    }

    @Test
    void invalidCardNumber_shouldReturnFalse() {
        // Пример некорректного номера карты
        String invalidCardNumber = "1234 5678 9012 3456";
        assertFalse(CardValidator.validateCardNumber(invalidCardNumber));
    }

    @Test
    void cardNumberWithNonDigitCharacters_shouldReturnFalse() {
        String cardNumber = "4539-1488-0343-6467"; // Карта с дефисами
        assertFalse(CardValidator.validateCardNumber(cardNumber));
    }

    @Test
    void cardNumberTooShort_shouldReturnFalse() {
        String shortCardNumber = "1234 5678 9012"; // Карта слишком короткая
        assertFalse(CardValidator.validateCardNumber(shortCardNumber));
    }

    @Test
    void cardNumberIsNull_shouldReturnFalse() {
        assertFalse(CardValidator.validateCardNumber(null)); // null карта
    }

    @Test
    void cardNumberWithExtraSpaces_shouldStillBeValid() {
        String validCardNumberWithSpaces = " 4539  1488 0343 6467 "; // Карта с пробелами
        assertTrue(CardValidator.validateCardNumber(validCardNumberWithSpaces));
    }

    @Test
    void validateCardNumber_shouldReturnTrue_forValidCardNumber() {
        String validCardNumber = generateValidCardNumber();
        System.out.println("Generated valid card number: " + validCardNumber);
        assertTrue(CardValidator.validateCardNumber(validCardNumber));
    }

    @Test
    void validateCardNumber_shouldReturnFalse_forInvalidCardNumber() {
        String invalidCardNumber = generateInvalidCardNumber();
        System.out.println("Generated invalid card number: " + invalidCardNumber);
        assertFalse(CardValidator.validateCardNumber(invalidCardNumber));
    }

    // Метод генерации валидного номера карты длиной 16 цифр
    private String generateValidCardNumber() {
        Random random = new Random();
        int[] digits = new int[16];

        // Заполняем первые 15 цифр случайными числами от 0 до 9
        for (int i = 0; i < 15; i++) {
            digits[i] = random.nextInt(10);
        }

        // Вычисляем контрольную цифру (16-я цифра) с помощью алгоритма Луна
        int sum = 0;
        for (int i = 0; i < 15; i++) {
            int digit = digits[14 - i];
            if (i % 2 == 0) { // Удваиваем каждую вторую цифру справа
                digit *= 2;
                if (digit > 9) digit -= 9;
            }
            sum += digit;
        }

        int checkDigit = (10 - (sum % 10)) % 10;
        digits[15] = checkDigit;

        // Собираем номер карты в строку
        StringBuilder cardNumber = new StringBuilder();
        for (int digit : digits) {
            cardNumber.append(digit);
        }
        return cardNumber.toString();
    }

    // Метод генерации невалидного номера карты
    private String generateInvalidCardNumber() {
        Random random = new Random();
        int[] digits = new int[16];

        // Заполняем первые 15 цифр случайными числами от 0 до 9
        for (int i = 0; i < 15; i++) {
            digits[i] = random.nextInt(10);
        }

        // Контрольная цифра делаем ошибочной, чтобы номер не прошел Луна
        digits[15] = (digits[15] + 1) % 10; // Изменяем последнюю цифру для гарантированной ошибки

        // Собираем номер карты в строку
        StringBuilder cardNumber = new StringBuilder();
        for (int digit : digits) {
            cardNumber.append(digit);
        }
        return cardNumber.toString();
    }
}