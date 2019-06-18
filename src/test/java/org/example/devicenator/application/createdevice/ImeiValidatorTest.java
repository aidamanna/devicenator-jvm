package org.example.devicenator.application.createdevice;

import static org.junit.Assert.*;

import org.junit.Test;

public class ImeiValidatorTest {

  @Test
  public void returnsFalseIfImeiLengthIsNotFifteen() {
    ImeiValidator imeiValidator = new ImeiValidator();
    boolean isValidImei = imeiValidator.isValid("123456", null);

    assertFalse(isValidImei);
  }

  @Test
  public void returnFalseIfImeiIsNotValid() {
    ImeiValidator imeiValidator = new ImeiValidator();
    boolean isValidImei = imeiValidator.isValid("123456789012345", null);

    assertFalse(isValidImei);
  }

  @Test
  public void returnTrueIfImeiIsValid() {
    ImeiValidator imeiValidator = new ImeiValidator();
    boolean isValidImei = imeiValidator.isValid("990000862471853", null);

    assertTrue(isValidImei);
  }
}