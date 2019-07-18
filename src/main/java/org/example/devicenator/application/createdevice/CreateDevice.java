package org.example.devicenator.application.createdevice;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.DeviceAlreadyExists;
import org.example.devicenator.domain.device.DeviceRepository;
import org.example.devicenator.domain.device.Imei;
import org.example.devicenator.domain.device.InvalidImei;

public class CreateDevice {

  private DeviceRepository deviceRepository;

  public CreateDevice(DeviceRepository deviceRepository) {
    this.deviceRepository = deviceRepository;
  }

  public void execute(CreateRequestDevice createRequestDevice)
      throws DeviceAlreadyExists, InvalidImei {
    Device device = new Device(
        Imei.create(createRequestDevice.getRawImei()),
        createRequestDevice.getVendor(),
        createRequestDevice.getModel(),
        createRequestDevice.getOperatingSystem(),
        createRequestDevice.getOperatingSystemVersion());

    deviceRepository.save(device);
  }
}
