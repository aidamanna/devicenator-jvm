package org.example.devicenator.infrastructure.http;

import org.example.devicenator.infrastructure.dtos.CreateRequestDevice;
import org.example.devicenator.infrastructure.persistence.DeviceCreator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateDeviceTest {

    private MockMvc mockMvc;
    private DeviceCreator deviceCreator;

    @Before
    public void setUp() {
        deviceCreator = Mockito.mock(DeviceCreator.class);
        CreateDevice createDevice = new CreateDevice(deviceCreator);
        mockMvc = MockMvcBuilders.standaloneSetup(createDevice).build();
    }

    @Test
    public void createsADevice() throws Exception {
        mockMvc.perform(post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body()))
                .andExpect(status().isCreated());

        CreateRequestDevice device = new CreateRequestDevice("iPhone", "iPhone X", "iOS", "10");

        verify(deviceCreator).execute(device);
    }

    private String body() {
        return "{\"vendor\": \"iPhone\", " +
                "\"model\": \"iPhone X\", " +
                "\"operatingSystem\": \"iOS\", " +
                "\"operatingSystemVersion\": \"10\"}";
    }
}
