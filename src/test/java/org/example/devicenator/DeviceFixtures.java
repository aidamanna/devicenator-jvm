package org.example.devicenator;

import org.example.devicenator.application.createdevice.CreateRequestDevice;
import org.example.devicenator.application.updatedevice.UpdateRequestDevice;
import org.example.devicenator.domain.device.Device;

public class DeviceFixtures {

    public static final String VENDOR = "iPhone";
    public static final String MODEL = "iPhone X";
    public static final String OPERATING_SYSTEM = "iOS";
    public static final String OPERATING_SYSTEM_VERSION = "10";

    public static Device aDevice(String imei) {
        return aDevice(imei, OPERATING_SYSTEM_VERSION);
    }

    public static Device aDevice(String imei, String operatingSystemVersion) {
        return new Device(imei,
                VENDOR,
                MODEL,
                OPERATING_SYSTEM,
                operatingSystemVersion);
    }

    public static CreateRequestDevice aCreateRequestDevice(String imei) {
        return new CreateRequestDevice(imei,
                VENDOR,
                MODEL,
                OPERATING_SYSTEM,
                OPERATING_SYSTEM_VERSION);
    }

    public static UpdateRequestDevice anUpdateRequestDevice(String imei, String operatingSystemVersion) {
        return new UpdateRequestDevice(imei,
                VENDOR,
                MODEL,
                OPERATING_SYSTEM,
                operatingSystemVersion);
    }

    public static String aDeviceJson(String imei) {
        return aDeviceJson(imei, OPERATING_SYSTEM_VERSION);
    }

    public static String aDeviceJson(String imei, String operatingSystemVersion) {
        return String.format("{\"imei\":\"%s\"," +
                        "\"vendor\":\"%s\"," +
                        "\"model\":\"%s\"," +
                        "\"operatingSystem\":\"%s\"," +
                        "\"operatingSystemVersion\":\"%s\"}",
                imei, VENDOR, MODEL, OPERATING_SYSTEM, operatingSystemVersion);
    }

    public static String aNonExistingDeviceResponseJson() {
        return "{\"error\": \"NON_EXISTING_DEVICE\", \"reason\": \"The device is not registered\"}";
    }
}
