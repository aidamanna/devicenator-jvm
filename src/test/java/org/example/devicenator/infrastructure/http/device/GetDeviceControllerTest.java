package org.example.devicenator.infrastructure.http.device;

import org.example.devicenator.application.getdevice.GetDevice;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.domain.device.InvalidImei;
import org.example.devicenator.infrastructure.http.GlobalExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.example.devicenator.DeviceFixtures.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class GetDeviceControllerTest {

    private static final String RAW_IMEI = "990000862471853";
    private static final String UNKNOWN_RAW_IMEI = "990000862471853";
    private static final String INVALID_RAW_IMEI = "990000862471855";

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
        when(getDevice.execute(RAW_IMEI)).thenReturn(aDevice(RAW_IMEI));

        mockMvc.perform(get("/devices/" + RAW_IMEI))
                                .andExpect(status().isOk())
                                .andExpect(content().string(aCreateDeviceJson(RAW_IMEI)));

    }

    @Test
    public void returnsNotFoundWhenRetrievingANonExistingDevice() throws Exception {
        when(getDevice.execute(UNKNOWN_RAW_IMEI)).thenThrow(new DeviceNotFound());

        mockMvc.perform(get("/devices/" + UNKNOWN_RAW_IMEI))
                .andExpect(status().isNotFound())
                .andExpect(content().json(aNonExistingDeviceResponseJson()));
    }

    @Test
    public void returnsBadRequestWhenImeiIsInvalid() throws Exception {
        when(getDevice.execute(INVALID_RAW_IMEI)).thenThrow(new InvalidImei("The device imei is invalid"));

        mockMvc.perform(get("/devices/" + INVALID_RAW_IMEI))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(anInvalidImeiResponseJson()));
    }
}
