package org.example.devicenator;

import org.example.devicenator.application.createdevice.CreateRequestDevice;
import org.example.devicenator.application.updatedevice.UpdateRequestDevice;
import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.Imei;
import org.example.devicenator.domain.device.InvalidImei;

public class DeviceFixtures {

    public static final String VENDOR = "iPhone";
    public static final String MODEL = "iPhone X";
    public static final String OPERATING_SYSTEM = "iOS";
    public static final String OPERATING_SYSTEM_VERSION = "10";

    public static Device aDevice(String rawImei) throws InvalidImei {
        return aDevice(rawImei, OPERATING_SYSTEM_VERSION);
    }

    public static Device aDevice(String rawImei, String operatingSystemVersion) throws InvalidImei {
        return new Device(Imei.create(rawImei),
                VENDOR,
                MODEL,
                OPERATING_SYSTEM,
                operatingSystemVersion);
    }

    public static CreateRequestDevice aCreateRequestDevice(String rawImei) {
        return new CreateRequestDevice(rawImei,
                VENDOR,
                MODEL,
                OPERATING_SYSTEM,
                OPERATING_SYSTEM_VERSION);
    }

    public static UpdateRequestDevice anUpdateRequestDevice(String operatingSystemVersion) {
        return new UpdateRequestDevice(operatingSystemVersion);
    }

    public static String aCreateDeviceJson(String rawImei) {
        return String.format("{\"imei\":\"%s\"," +
                        "\"vendor\":\"%s\"," +
                        "\"model\":\"%s\"," +
                        "\"operatingSystem\":\"%s\"," +
                        "\"operatingSystemVersion\":\"%s\"}",
                rawImei, VENDOR, MODEL, OPERATING_SYSTEM, OPERATING_SYSTEM_VERSION);
    }

    public static String anUpdateDeviceJson(String operatingSystemVersion) {
        return String.format("{\"operatingSystemVersion\":\"%s\"}", operatingSystemVersion);
    }

    public static String aNonExistingDeviceResponseJson() {
        return "{\"error\": \"NON_EXISTING_DEVICE\", \"reason\": \"The device is not registered\"}";
    }

    public static String anInvalidImeiResponseJson() {
        return "{\"error\": \"INVALID_IMEI\", \"reason\": \"The device imei is invalid\"}";
    }
}
