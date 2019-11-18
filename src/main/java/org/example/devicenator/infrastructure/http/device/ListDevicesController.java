package org.example.devicenator.infrastructure.http.device;

import org.example.devicenator.application.listdevices.ListDevices;
import org.example.devicenator.domain.device.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListDevicesController {


    private ListDevices listDevices;

    @Autowired
    public ListDevicesController(ListDevices listDevices) {
        this.listDevices = listDevices;
    }

    @GetMapping("/devices")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Device> execute() {
        return listDevices.execute();
    }
}
