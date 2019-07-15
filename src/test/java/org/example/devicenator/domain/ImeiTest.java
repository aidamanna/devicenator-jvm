package org.example.devicenator.domain;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import org.example.devicenator.domain.device.Imei;
import org.example.devicenator.domain.device.InvalidImei;
import org.junit.Test;

public class ImeiTest {

  @Test(expected = InvalidImei.class)
  public void throwsInvalidImeiIfImeiLengthIsNotFifteen() throws InvalidImei {
    Imei.create("123456");
  }

  @Test(expected = InvalidImei.class)
  public void throwsInvalidImeiIfImeiIsNotValid() throws InvalidImei {
    Imei.create("123456789012345");
  }

  @Test
  public void createsImeiIfImeiIsValid() throws InvalidImei {
    Imei imei = Imei.create("990000862471853");

    assertThat(imei, instanceOf(Imei.class));
  }
}