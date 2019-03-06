package org.example.devicenator.infrastructure.http;

import org.example.devicenator.application.createdevice.CreateRequestDevice;
import org.example.devicenator.application.createdevice.CreateDevice;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateDeviceControllerTest {

    private static final String REQUEST_BODY = "{\"imei\": \"990000862471854\", " +
            "\"vendor\": \"iPhone\", " +
            "\"model\": \"iPhone X\", " +
            "\"operatingSystem\": \"iOS\", " +
            "\"operatingSystemVersion\": \"10\"}";

    private static final String EMPTY_REQUEST_BODY = "{}";

    private MockMvc mockMvc;
    private CreateDevice createDevice;

    @Before
    public void setUp() {
        createDevice = Mockito.mock(CreateDevice.class);
        CreateDeviceController createDeviceController = new CreateDeviceController(createDevice);
        mockMvc = MockMvcBuilders.standaloneSetup(createDeviceController).build();
    }

    @Test
    public void createsADevice() throws Exception {
        mockMvc.perform(post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(REQUEST_BODY))
                .andExpect(status().isCreated());

        CreateRequestDevice device = new CreateRequestDevice(
                "990000862471854",
                "iPhone",
                "iPhone X",
                "iOS",
                10);

        verify(createDevice).execute(device);
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
