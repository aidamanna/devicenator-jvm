package org.example.devicenator.domain.device;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Imei {

    public static final int IMEI_LENGTH = 15;

    private final String imei;

    private Imei(String imei) {
        this.imei = imei;
    }

    public static Imei create(String rawImei) throws InvalidImei {
        validate(rawImei);
        return new Imei(rawImei);
    }

    private static void validate(String rawImei) throws InvalidImei {
        if (rawImei == null || rawImei.length() != IMEI_LENGTH) {
            throw new InvalidImei("The device imei does not have 15 digits");
        }

        long imei = Long.parseLong(rawImei);

        int sum = 0;

        for (int i = 0; i < rawImei.length(); i++) {
            int digit = getLastDigit(imei);

            digit = doubleDigitIfInEvenPosition(i, digit);

            sum += getSumOfDigits(digit);

            imei = removeLastDigit(imei);
        }

        if (!isSumOfDigitsDivisibleByTen(sum)) {
            throw new InvalidImei("The device imei is invalid");
        }
    }

    private static int getLastDigit(long number) {
        return (int) (number % 10);
    }

    private static int doubleDigitIfInEvenPosition(int position, int digit) {
        if (position % 2 != 0) {
            digit = 2 * digit;
        }
        return digit;
    }

    private static int getSumOfDigits(long number) {
        int sumOfDigits = 0;

        while (number > 0) {
            int digit = getLastDigit(number);
            sumOfDigits += digit;
            number = removeLastDigit(number);
        }
        return sumOfDigits;
    }

    private static long removeLastDigit(long number) {
        return number / 10;
    }

    private static boolean isSumOfDigitsDivisibleByTen(int sum) {
        return sum % 10 == 0;
    }
}
