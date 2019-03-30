package org.example.devicenator.infrastructure.http;

import org.example.devicenator.application.getdevice.GetDevice;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.example.devicenator.DeviceFixtures.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class GetDeviceControllerTest {

    private GetDevice getDevice;
    private GetDeviceController getDeviceController;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        getDevice = mock(GetDevice.class);
        getDeviceController = new GetDeviceController(getDevice);
        mockMvc = MockMvcBuilders.standaloneSetup(getDeviceController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    public void retrievesAnExistingDevice() throws Exception {
        when(getDevice.execute(IMEI)).thenReturn(aDevice());

        mockMvc.perform(get("/devices/" + IMEI))
                                .andExpect(status().isOk())
                                .andExpect(content().string(aDeviceJson()));

    }

    @Test
    public void returnsNotFoundWhenRetrievingANonExistingDevice() throws Exception {
        when(getDevice.execute(UNKNOWN_IMEI)).thenThrow(DeviceNotFound.class);

        String errorBody = "{\"error\": \"NON_EXISTING_DEVICE\", \"reason\": \"The device is not registered\"}";

        mockMvc.perform(get("/devices/" + UNKNOWN_IMEI))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(errorBody));
    }
}
