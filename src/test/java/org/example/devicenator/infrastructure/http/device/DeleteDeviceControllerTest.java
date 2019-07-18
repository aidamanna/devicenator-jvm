package org.example.devicenator.infrastructure.http.device;

import static org.example.devicenator.DeviceFixtures.anInvalidImeiResponseJson;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.devicenator.application.deletedevice.DeleteDevice;
import org.example.devicenator.domain.device.InvalidImei;
import org.example.devicenator.infrastructure.http.GlobalExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class DeleteDeviceControllerTest {

    private static final String RAW_IMEI = "990000862471853";
    private static final String INVALID_RAW_IMEI = "990000862471855";

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
        mockMvc.perform(delete("/devices/" + RAW_IMEI))
                .andExpect(status().isOk());

        verify(deleteDevice).execute(RAW_IMEI);
    }

    @Test
    public void returnsBadRequestWhenImeiIsInvalid() throws Exception {
        doThrow(new InvalidImei("The device imei is invalid")).when(deleteDevice).execute(INVALID_RAW_IMEI);

        mockMvc.perform(delete("/devices/" + INVALID_RAW_IMEI))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(anInvalidImeiResponseJson()));
    }
}