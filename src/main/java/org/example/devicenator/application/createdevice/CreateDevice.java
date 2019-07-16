package org.example.devicenator.application.createdevice;

import org.example.devicenator.domain.device.DeviceAlreadyExists;
import org.example.devicenator.domain.device.DeviceRepository;
import org.example.devicenator.domain.device.OldDevice;

public class CreateDevice {

  private DeviceRepository deviceRepository;

  public CreateDevice(DeviceRepository deviceRepository) {
    this.deviceRepository = deviceRepository;
  }

  public void execute(CreateRequestDevice createRequestDevice)
      throws DeviceAlreadyExists {
    OldDevice device = new OldDevice(
        createRequestDevice.getImei(),
        createRequestDevice.getVendor(),
        createRequestDevice.getModel(),
        createRequestDevice.getOperatingSystem(),
        createRequestDevice.getOperatingSystemVersion());

    deviceRepository.save(device);
  }
}
