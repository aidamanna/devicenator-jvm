package org.example.devicenator.infrastructure.http;

import org.example.devicenator.DeviceFixtures;
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

    public static final String IMEI = "990000862471854";
    public static final String UNKNOWN_IMEI = "990000862471855";
    public static final String OPERATING_SYSTEM_VERSION = "11";
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
                .content(aDeviceJson(IMEI, OPERATING_SYSTEM_VERSION)))
                .andExpect(status().isOk());

        verify(updateDevice).execute(anUpdateRequestDevice(IMEI, OPERATING_SYSTEM_VERSION));
    }

    @Test
    public void throwsNotFoundWhenTheDeviceDoesNotExist() throws Exception {
        doThrow(DeviceNotFound.class).when(updateDevice)
                .execute(anUpdateRequestDevice(UNKNOWN_IMEI, OPERATING_SYSTEM_VERSION));

        mockMvc.perform(put("/devices/" + UNKNOWN_IMEI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(DeviceFixtures.aDeviceJson(UNKNOWN_IMEI, OPERATING_SYSTEM_VERSION)))
                .andExpect(status().isNotFound())
                .andExpect(content().json(aNonExistingDeviceResponseJson()));
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