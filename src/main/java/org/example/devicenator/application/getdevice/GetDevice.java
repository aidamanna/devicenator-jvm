package org.example.devicenator.application.getdevice;

import org.example.devicenator.domain.device.*;

public class GetDevice {

    private DeviceRepository deviceRepository;

    public GetDevice(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device execute(String rawImei) throws DeviceException {
        Imei imei = Imei.create(rawImei);
        return deviceRepository.getBy(imei);
    }
}
