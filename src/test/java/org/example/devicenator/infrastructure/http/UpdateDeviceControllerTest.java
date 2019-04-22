package org.example.devicenator.infrastructure.http;

import org.example.devicenator.application.updatedevice.UpdateDevice;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.example.devicenator.DeviceFixtures.IMEI;
import static org.example.devicenator.DeviceFixtures.anUpdateRequestDevice;
import static org.example.devicenator.DeviceFixtures.anUpdateRequestDeviceJson;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateDeviceControllerTest {

    @Test
    public void updatesADevice() throws Exception {
        UpdateDevice updateDevice = mock(UpdateDevice.class);
        UpdateDeviceController updateDeviceController = new UpdateDeviceController(updateDevice);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(updateDeviceController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();

        mockMvc.perform(put("/devices/" + IMEI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(anUpdateRequestDeviceJson()))
                .andExpect(status().isOk());

        verify(updateDevice).execute(anUpdateRequestDevice());
    }
}