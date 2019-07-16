package org.example.devicenator.infrastructure.persistence;

import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.OldDevice;
import org.example.devicenator.domain.device.DeviceAlreadyExists;
import org.example.devicenator.domain.device.DeviceRepository;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceJDBCRepository implements DeviceRepository {

    private JdbcTemplate jdbcTemplate;
    private String getDeviceByIdQuery;

    public DeviceJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(OldDevice device) throws DeviceAlreadyExists {
        String saveDeviceQuery = "INSERT INTO devices VALUES (?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(
                    saveDeviceQuery,
                    device.getImei(),
                    device.getVendor(),
                    device.getModel(),
                    device.getOperatingSystem(),
                    device.getOperatingSystemVersion());
        } catch (DuplicateKeyException e) {
            throw new DeviceAlreadyExists();
        }
    }

    @Override
    public void save(Device device) throws DeviceAlreadyExists {
        String saveDeviceQuery = "INSERT INTO devices VALUES (?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(
                saveDeviceQuery,
                device.getImei(),
                device.getVendor(),
                device.getModel(),
                device.getOperatingSystem(),
                device.getOperatingSystemVersion());
        } catch (DuplicateKeyException e) {
            throw new DeviceAlreadyExists();
        }
    }

    @Override
    public OldDevice getBy(String imei) throws DeviceNotFound {
        getDeviceByIdQuery = "SELECT * FROM devices WHERE imei = ?";

        try {
            return jdbcTemplate.queryForObject(
                    getDeviceByIdQuery,
                    new Object[]{imei},
                    new DeviceRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new DeviceNotFound();
        }
    }

    @Override
    public void update(OldDevice device) {
        String updateDeviceQuery =
                "UPDATE devices " +
                "SET vendor = ?, model = ?, operatingSystem = ?, operatingSystemVersion = ? " +
                "WHERE imei = ?";

        jdbcTemplate.update(
                updateDeviceQuery,
                device.getVendor(),
                device.getModel(),
                device.getOperatingSystem(),
                device.getOperatingSystemVersion(),
                device.getImei());
    }

    public void delete(String imei) {
        String deleteDeviceQuery = "DELETE devices WHERE imei = ?";

        jdbcTemplate.update(
                deleteDeviceQuery,
                imei);
    }

    public class DeviceRowMapper implements RowMapper<OldDevice> {

        @Override
        public OldDevice mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new OldDevice(
                    rs.getString("imei"),
                    rs.getString("vendor"),
                    rs.getString("model"),
                    rs.getString("operatingSystem"),
                    rs.getString("operatingSystemVersion"));
        }
    }
}
