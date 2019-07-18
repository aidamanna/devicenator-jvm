package org.example.devicenator.application.createdevice;

import org.example.devicenator.domain.device.*;

public class CreateDevice {

  private DeviceRepository deviceRepository;

  public CreateDevice(DeviceRepository deviceRepository) {
    this.deviceRepository = deviceRepository;
  }

  public void execute(CreateRequestDevice createRequestDevice) throws DeviceException {
    Device device = new Device(
        Imei.create(createRequestDevice.getRawImei()),
        createRequestDevice.getVendor(),
        createRequestDevice.getModel(),
        createRequestDevice.getOperatingSystem(),
        createRequestDevice.getOperatingSystemVersion());

    deviceRepository.save(device);
  }
}
