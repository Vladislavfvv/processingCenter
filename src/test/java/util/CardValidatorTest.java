package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardValidatorTest {
    @Test
    void testValidCardNumber() {
        // Проверка с корректным номером карты
        String validCardNumber = "1234567812345670";
        boolean result = CardValidator.validateCardNumber(validCardNumber);
        assertTrue(result, "Card number should be valid");
    }

    @Test
    void testInvalidCardNumberLength() {
        // Проверка на номер карты неправильной длины (не 16 символов)
        String shortCardNumber = "123456781234567";  // 15 цифр
        String longCardNumber = "12345678123456700"; // 18 цифр

        assertFalse(CardValidator.validateCardNumber(shortCardNumber), "Card number should be invalid (too short)");
        assertFalse(CardValidator.validateCardNumber(longCardNumber), "Card number should be invalid (too long)");
    }

    @Test
    void testInvalidCardNumberNotDigits() {
        // Проверка на строку, которая содержит нецифровые символы
        String invalidCardNumber1 = "1234a67812345670"; // Есть буква
        String invalidCardNumber2 = "1234-67812345670"; // Есть дефис

        assertFalse(CardValidator.validateCardNumber(invalidCardNumber1), "Card number should be invalid (contains non-digit)");
        assertFalse(CardValidator.validateCardNumber(invalidCardNumber2), "Card number should be invalid (contains non-digit)");
    }

    @Test
    void testCardNumberWithInvalidCheckDigit() {
        // Проверка с некорректной контрольной цифрой
        String invalidCardNumber = "1234567812345671"; // Сумма не кратна 10
        assertFalse(CardValidator.validateCardNumber(invalidCardNumber), "Card number should be invalid (wrong checksum)");
    }

    @Test
    void testCardNumberWithValidCheckDigit() {
        // Проверка с корректной контрольной цифрой
        String validCardNumber = "1234567812345670"; // Сумма кратна 10
        assertTrue(CardValidator.validateCardNumber(validCardNumber), "Card number should be valid (correct checksum)");
    }

    @Test
    void testNullCardNumber() {
        // Проверка с null значением
        assertFalse(CardValidator.validateCardNumber(null), "Card number should be invalid (null)");
    }

}