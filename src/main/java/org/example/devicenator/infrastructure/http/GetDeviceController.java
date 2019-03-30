package org.example.devicenator.infrastructure.http;

import org.example.devicenator.application.getdevice.GetDevice;
import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class GetDeviceController {

    private GetDevice getDevice;

    @Autowired
    public GetDeviceController(GetDevice getDevice) {
        this.getDevice = getDevice;
    }

    @GetMapping("/devices/{imei}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Device execute(@PathVariable String imei) throws DeviceNotFound {
        return getDevice.execute(imei);
    }
}
