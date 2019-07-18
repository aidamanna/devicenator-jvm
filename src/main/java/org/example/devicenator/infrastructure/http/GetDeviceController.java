package org.example.devicenator.infrastructure.http;

import org.example.devicenator.application.getdevice.GetDevice;
import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.InvalidImei;
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

    @GetMapping("/devices/{rawImei}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Device execute(@PathVariable String rawImei) throws DeviceNotFound, InvalidImei {
        return getDevice.execute(rawImei);
    }
}
