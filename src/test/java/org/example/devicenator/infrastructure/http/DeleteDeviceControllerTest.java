package org.example.devicenator.infrastructure.http;

import org.example.devicenator.application.deletedevice.DeleteDevice;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.example.devicenator.DeviceFixtures.IMEI;
import static org.example.devicenator.DeviceFixtures.aNonExistingDeviceResponseJson;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteDeviceControllerTest {

    private DeleteDevice deleteDevice;
    private MockMvc mockMvc;
    private DeleteDeviceController deleteDeviceController;

    @Before
    public void setUp() {
        deleteDevice = mock(DeleteDevice.class);
        deleteDeviceController = new DeleteDeviceController(deleteDevice);
        mockMvc = MockMvcBuilders.standaloneSetup(deleteDeviceController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    public void deletesADevice() throws Exception {
        mockMvc.perform(delete("/devices/" + IMEI))
                .andExpect(status().isOk());

        verify(deleteDevice).execute(IMEI);
    }

    @Test
    public void returnsNotFoundWhenDeletingANonExistingDevice() throws Exception {
        doThrow(DeviceNotFound.class).when(deleteDevice).execute(IMEI);

        mockMvc.perform(delete("/devices/" + IMEI))
                .andExpect(status().isNotFound())
                .andExpect(content().json(aNonExistingDeviceResponseJson()));
    }
}