package org.example.devicenator.infrastructure.http.device;

import org.example.devicenator.application.listdevices.ListDevices;
import org.example.devicenator.infrastructure.http.GlobalExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.example.devicenator.fixtures.DeviceFixtures.aDeviceList;
import static org.example.devicenator.fixtures.DeviceFixtures.aDeviceListJson;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ListDevicesControllerTest {

    private static final String[] RAW_IMEI_LIST = {"990000862471853", "990000862471861"};

    private MockMvc mockMvc;
    private ListDevicesController listDevicesController;
    private ListDevices listDevices;

    @Before
    public void setUp() {
        listDevices = mock(ListDevices.class);
        listDevicesController = new ListDevicesController(listDevices);
        mockMvc = MockMvcBuilders.standaloneSetup(listDevicesController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    public void retrievesAllDevices() throws Exception {
        when(listDevices.execute()).thenReturn(aDeviceList(RAW_IMEI_LIST));

        mockMvc.perform(get("/devices"))
                .andExpect(status().isOk())
                .andExpect(content().json(aDeviceListJson(RAW_IMEI_LIST)));
    }
}
