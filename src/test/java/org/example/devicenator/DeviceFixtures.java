package org.example.devicenator;

import org.example.devicenator.application.createdevice.CreateRequestDevice;
import org.example.devicenator.domain.device.Device;

public class DeviceFixtures {

    public static final String IMEI = "990000862471854";
    public static final String VENDOR = "iPhone";
    public static final String MODEL = "iPhone X";
    public static final String OPERATING_SYSTEM = "iOS";
    public static final String OPERATING_SYSTEM_VERSION = "10";

    public static final String UNKNOWN_IMEI = "99000086247185";

    public static Device aDevice() {
        return new Device(IMEI,
                VENDOR,
                MODEL,
                OPERATING_SYSTEM,
                OPERATING_SYSTEM_VERSION);
    }

    public static CreateRequestDevice aCreatedRequestDevice() {
        return new CreateRequestDevice(IMEI,
                VENDOR,
                MODEL,
                OPERATING_SYSTEM,
                OPERATING_SYSTEM_VERSION);
    }

    public static String aDeviceJson() {
        return String.format("{\"imei\":\"%s\"," +
                        "\"vendor\":\"%s\"," +
                        "\"model\":\"%s\"," +
                        "\"operatingSystem\":\"%s\"," +
                        "\"operatingSystemVersion\":\"%s\"}",
                IMEI, VENDOR, MODEL, OPERATING_SYSTEM, OPERATING_SYSTEM_VERSION);
    }

    public static String aCreateRequestDeviceJson() {
        return String.format("{\"imei\":\"%s\"," +
                "\"vendor\":\"%s\"," +
                "\"model\":\"%s\"," +
                "\"operatingSystem\":\"%s\"," +
                "\"operatingSystemVersion\":\"%s\"}",
                IMEI, VENDOR, MODEL, OPERATING_SYSTEM, OPERATING_SYSTEM_VERSION);
    }
}
