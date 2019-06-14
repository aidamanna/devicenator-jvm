package org.example.devicenator.infrastructure.http;

import org.example.devicenator.application.deletedevice.DeleteDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteDeviceController {

    private DeleteDevice deleteDevice;

    @Autowired
    public DeleteDeviceController(DeleteDevice deleteDevice) {
        this.deleteDevice = deleteDevice;
    }

    @DeleteMapping("/devices/{imei}")
    @ResponseStatus(HttpStatus.OK)
    public void execute(@PathVariable String imei) {
        deleteDevice.execute(imei);
    }
}
