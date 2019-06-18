package org.example.devicenator.application.createdevice;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImeiValidator implements ConstraintValidator<Imei, String> {

  public static final int IMEI_LENGTH = 15;

  @Override
  public void initialize(Imei constraint) {

  }

  @Override
  public boolean isValid(String imeiString, ConstraintValidatorContext context) {
    if (imeiString == null || imeiString.length() != IMEI_LENGTH) {
      return false;
    }

    long imei = Long.parseLong(imeiString);

    int sum = 0;

    for (int i = 0; i < imeiString.length(); i++) {
      int digit = getLastDigit(imei);

      digit = doubleDigitIfInEvenPosition(i, digit);

      sum += getSumOfDigits(digit);

      imei = removeLastDigit(imei);
    }

    if (!isSumOfDigitsDivisibleByTen(sum)) {
      return false;
    }

    return true;
  }

  private int getLastDigit(long number) {
    return (int) (number % 10);
  }

  private int doubleDigitIfInEvenPosition(int position, int digit) {
    if (position % 2 != 0) {
      digit = 2 * digit;
    }
    return digit;
  }

  private int getSumOfDigits(long number) {
    int sumOfDigits = 0;

    while (number > 0) {
      int digit = getLastDigit(number);
      sumOfDigits += digit;
      number = removeLastDigit(number);
    }
    return sumOfDigits;
  }

  private long removeLastDigit(long number) {
    return number / 10;
  }

  private boolean isSumOfDigitsDivisibleByTen(int sum) {
    return sum % 10 == 0;
  }
}
