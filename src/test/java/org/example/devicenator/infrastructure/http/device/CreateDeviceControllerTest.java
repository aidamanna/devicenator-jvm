package org.example.devicenator.infrastructure.http.device;

import org.example.devicenator.domain.device.InvalidImei;
import org.example.devicenator.infrastructure.http.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.example.devicenator.application.createdevice.CreateDevice;
import org.example.devicenator.domain.device.DeviceAlreadyExists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.example.devicenator.fixtures.DeviceFixtures.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateDeviceControllerTest {

    private static final String RAW_IMEI = "990000862471853";
    private static final String EXISTING_RAW_IMEI = "990000862471853";
    private static final String INVALID_RAW_IMEI = "990000862471855";
    private static final String EMPTY_REQUEST_BODY = "{}";

    private MockMvc mockMvc;
    private Logger logger;
    private CreateDevice createDevice;

    @Before
    public void setUp() {
        logger = mock(Logger.class);
        createDevice = mock(CreateDevice.class);
        CreateDeviceController createDeviceController = new CreateDeviceController(createDevice, logger);
        mockMvc = MockMvcBuilders.standaloneSetup(createDeviceController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    public void createsADevice() throws Exception {
        mockMvc.perform(post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aCreateDeviceJson(RAW_IMEI)))
                .andExpect(status().isCreated());

        verify(createDevice).execute(aCreateRequestDevice(RAW_IMEI));
    }

    @Test
    public void returnsConflictWhenTheDeviceAlreadyExists() throws Exception {
        doThrow(new DeviceAlreadyExists()).when(createDevice).execute(aCreateRequestDevice(EXISTING_RAW_IMEI));

        mockMvc.perform(post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aCreateDeviceJson(EXISTING_RAW_IMEI)))
                .andExpect(status().isConflict())
                .andExpect(content().json(aExistingDeviceResponseJson()));
    }

    @Test
    public void returnsBadRequestWhenImeiIsInvalid() throws Exception {
        doThrow(new InvalidImei("The device imei is invalid"))
                .when(createDevice).execute(aCreateRequestDevice(INVALID_RAW_IMEI));

        mockMvc.perform(post("/devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(aCreateDeviceJson(INVALID_RAW_IMEI)))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(anInvalidImeiResponseJson()));
    }

    @Test
    public void returnsBadRequestWhenNotSpecifyingDeviceValues() throws Exception {
        mockMvc.perform(post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(EMPTY_REQUEST_BODY))
                .andExpect(status().isBadRequest());

        verify(createDevice, never()).execute(any());
    }
}
