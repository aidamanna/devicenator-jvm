package org.example.devicenator;

import org.example.devicenator.application.createdevice.CreateRequestDevice;
import org.example.devicenator.application.updatedevice.UpdateRequestDevice;
import org.example.devicenator.domain.device.OldDevice;

public class DeviceFixtures {

    public static final String VENDOR = "iPhone";
    public static final String MODEL = "iPhone X";
    public static final String OPERATING_SYSTEM = "iOS";
    public static final String OPERATING_SYSTEM_VERSION = "10";

    public static OldDevice aDevice(String imei) {
        return aDevice(imei, OPERATING_SYSTEM_VERSION);
    }

    public static OldDevice aDevice(String imei, String operatingSystemVersion) {
        return new OldDevice(imei,
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

    public static UpdateRequestDevice anUpdateRequestDevice(String operatingSystemVersion) {
        return new UpdateRequestDevice(operatingSystemVersion);
    }

    public static String aCreateDeviceJson(String imei) {
        return String.format("{\"imei\":\"%s\"," +
                        "\"vendor\":\"%s\"," +
                        "\"model\":\"%s\"," +
                        "\"operatingSystem\":\"%s\"," +
                        "\"operatingSystemVersion\":\"%s\"}",
                imei, VENDOR, MODEL, OPERATING_SYSTEM, OPERATING_SYSTEM_VERSION);
    }

    public static String anUpdateDeviceJson(String operatingSystemVersion) {
        return String.format("{\"operatingSystemVersion\":\"%s\"}", operatingSystemVersion);
    }

    public static String aNonExistingDeviceResponseJson() {
        return "{\"error\": \"NON_EXISTING_DEVICE\", \"reason\": \"The device is not registered\"}";
    }
}
