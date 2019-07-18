package org.example.devicenator.infrastructure.http.device;

import org.example.devicenator.application.updatedevice.UpdateDevice;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.domain.device.InvalidImei;
import org.example.devicenator.infrastructure.http.GlobalExceptionHandler;
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

    private static final String RAW_IMEI = "990000862471853";
    private static final String UNKNOWN_RAW_IMEI = "990000862471853";
    private static final String INVALID_RAW_IMEI = "990000862471855";
    private static final String OPERATING_SYSTEM_VERSION = "11";
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
        mockMvc.perform(put("/devices/" + RAW_IMEI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(anUpdateDeviceJson(OPERATING_SYSTEM_VERSION)))
                .andExpect(status().isOk());

        verify(updateDevice).execute(RAW_IMEI, anUpdateRequestDevice(OPERATING_SYSTEM_VERSION));
    }

    @Test
    public void returnsNotFoundWhenTheDeviceDoesNotExist() throws Exception {
        doThrow(new DeviceNotFound()).when(updateDevice)
                .execute(UNKNOWN_RAW_IMEI, anUpdateRequestDevice(OPERATING_SYSTEM_VERSION));

        mockMvc.perform(put("/devices/" + UNKNOWN_RAW_IMEI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(anUpdateDeviceJson(OPERATING_SYSTEM_VERSION)))
                .andExpect(status().isNotFound())
                .andExpect(content().json(aNonExistingDeviceResponseJson()));
    }

    @Test
    public void returnsBadRequestWhenImeiIsInvalid() throws Exception {
        doThrow(new InvalidImei("The device imei is invalid")).when(updateDevice)
                .execute(INVALID_RAW_IMEI, anUpdateRequestDevice(OPERATING_SYSTEM_VERSION));

        mockMvc.perform(put("/devices/" + INVALID_RAW_IMEI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(anUpdateDeviceJson(OPERATING_SYSTEM_VERSION)))
                .andExpect(status().isBadRequest() )
                .andExpect(content().json(anInvalidImeiResponseJson()));
    }

    @Test
    public void throwsBadRequestWhenNotSpecifyingDeviceValues() throws Exception {
        mockMvc.perform(put("/devices/" + RAW_IMEI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(EMPTY_REQUEST_BODY))
                .andExpect(status().isBadRequest());

        verify(updateDevice, never()).execute(eq(RAW_IMEI), any());
    }
}