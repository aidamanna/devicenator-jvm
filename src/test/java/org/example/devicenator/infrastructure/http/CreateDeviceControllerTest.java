package org.example.devicenator.infrastructure.http;

import org.example.devicenator.DeviceFixtures;
import org.example.devicenator.application.createdevice.CreateDevice;
import org.example.devicenator.domain.device.DeviceAlreadyExists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.example.devicenator.DeviceFixtures.aCreateDeviceJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateDeviceControllerTest {

    public static final String IMEI = "990000862471854";
    public static final String EXISTING_IMEI = "990000862471856";
    private static final String EMPTY_REQUEST_BODY = "{}";

    private MockMvc mockMvc;
    private CreateDevice createDevice;

    @Before
    public void setUp() {
        createDevice = mock(CreateDevice.class);
        CreateDeviceController createDeviceController = new CreateDeviceController(createDevice);
        mockMvc = MockMvcBuilders.standaloneSetup(createDeviceController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    public void createsADevice() throws Exception {
        mockMvc.perform(post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aCreateDeviceJson(IMEI)))
                .andExpect(status().isCreated());

        verify(createDevice).execute(DeviceFixtures.aCreateRequestDevice(IMEI));
    }

    @Test
    public void throwsConflictWhenTheDeviceAlreadyExists() throws Exception {
        doThrow(DeviceAlreadyExists.class).when(createDevice).execute(DeviceFixtures.aCreateRequestDevice(EXISTING_IMEI));

        String errorBody = "{\"error\": \"EXISTING_DEVICE\", \"reason\": \"The device is registered\"}";

        mockMvc.perform(post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aCreateDeviceJson(EXISTING_IMEI)))
                .andExpect(status().isConflict())
                .andExpect(content().json(errorBody));
    }

    @Test
    public void throwsBadRequestWhenNotSpecifyingDeviceValues() throws Exception {
        mockMvc.perform(post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(EMPTY_REQUEST_BODY))
                .andExpect(status().isBadRequest());

        verify(createDevice, never()).execute(any());
    }
}
