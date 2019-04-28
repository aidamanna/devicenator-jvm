package org.example.devicenator.infrastructure.http;

import org.example.devicenator.application.updatedevice.UpdateDevice;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.example.devicenator.DeviceFixtures.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateDeviceControllerTest {

    private static final String EMPTY_REQUEST_BODY = "{}";

    private UpdateDevice updateDevice;
    private UpdateDeviceController updateDeviceController;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        updateDevice = mock(UpdateDevice.class);
        updateDeviceController = new UpdateDeviceController(updateDevice);

        mockMvc = MockMvcBuilders.standaloneSetup(updateDeviceController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    public void updatesADevice() throws Exception {
        mockMvc.perform(put("/devices/" + IMEI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(anUpdateRequestDeviceJson()))
                .andExpect(status().isOk());

        verify(updateDevice).execute(anUpdateRequestDevice());
    }

    @Test
    public void throwsNotFoundWhenTheDeviceDoesNotExist() throws Exception {
        doThrow(DeviceNotFound.class).when(updateDevice).execute(anUpdateRequestDevice());

        String errorBody = "{\"error\": \"NON_EXISTING_DEVICE\", \"reason\": \"The device is not registered\"}";

        mockMvc.perform(put("/devices/" + IMEI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(anUpdateRequestDeviceJson()))
                .andExpect(status().isNotFound())
                .andExpect(content().json(errorBody));
    }

    @Test
    public void throwsBadRequestWhenNotSpecifyingDeviceValues() throws Exception {
        mockMvc.perform(put("/devices/" + IMEI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(EMPTY_REQUEST_BODY))
                .andExpect(status().isBadRequest());

        verify(updateDevice, never()).execute(any());
    }
}