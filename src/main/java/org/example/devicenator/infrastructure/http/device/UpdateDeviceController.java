package org.example.devicenator.infrastructure.http.device;

import org.example.devicenator.application.updatedevice.UpdateDevice;
import org.example.devicenator.application.updatedevice.UpdateRequestDevice;
import org.example.devicenator.domain.device.DeviceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UpdateDeviceController {


    private UpdateDevice updateDevice;

    @Autowired
    public UpdateDeviceController(UpdateDevice updateDevice) {
        this.updateDevice = updateDevice;
    }

    @PutMapping("/devices/{rawImei}")
    @ResponseStatus(HttpStatus.OK)
    public void execute(@PathVariable String rawImei, @Valid @RequestBody UpdateRequestDevice device)
            throws DeviceException {
        updateDevice.execute(rawImei, device);
    }
}
