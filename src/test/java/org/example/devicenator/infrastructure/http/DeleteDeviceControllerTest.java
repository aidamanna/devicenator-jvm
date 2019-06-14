package org.example.devicenator.infrastructure.http;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.devicenator.application.deletedevice.DeleteDevice;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class DeleteDeviceControllerTest {

    public static final String IMEI = "990000862471854";

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
        mockMvc.perform(delete("/devices/" + IMEI))
                .andExpect(status().isOk());

        verify(deleteDevice).execute(IMEI);
    }
}