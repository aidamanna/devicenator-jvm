package org.example.devicenator.application.listdevices;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.InvalidImei;
import org.example.devicenator.infrastructure.persistence.DeviceJDBCRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.example.devicenator.fixtures.DeviceFixtures.aDeviceList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListDevicesTest {

    private static final String[] RAW_IMEI_LIST = {"990000862471853", "990000862471861"};

    private DeviceJDBCRepository deviceRepository;
    private ListDevices listDevices;

    @Before
    public void setUp() {
        deviceRepository = mock(DeviceJDBCRepository.class);
        listDevices = new ListDevices(deviceRepository);
    }

    @Test
    public void retrievesAllDevices() throws InvalidImei {
        List<Device> expectedDeviceList = aDeviceList(RAW_IMEI_LIST);
        when(deviceRepository.getList()).thenReturn(expectedDeviceList);

        List<Device> deviceList = listDevices.execute();

        assertThat(deviceList, is(expectedDeviceList));
    }
}
