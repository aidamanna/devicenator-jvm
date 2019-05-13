package org.example.devicenator.infrastructure.persistence;

import io.vavr.control.Try;
import org.example.devicenator.domain.device.Device;
import org.example.devicenator.domain.device.DeviceAlreadyExists;
import org.example.devicenator.domain.device.DeviceRepository;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.xml.ws.Response;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceJDBCRepository implements DeviceRepository {

    private JdbcTemplate jdbcTemplate;
    private String getDeviceByIdQuery;

    public DeviceJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Try<Response> save(Device device) {
        String saveDeviceQuery = "INSERT INTO devices VALUES (?, ?, ?, ?, ?)";

        return Try.of(() ->
            jdbcTemplate.update(
                    saveDeviceQuery,
                    device.getImei(),
                    device.getVendor(),
                    device.getModel(),
                    device.getOperatingSystem(),
                    device.getOperatingSystemVersion())
        );
    }

    @Override
    public Device getBy(String imei) throws DeviceNotFound {
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
    public void update(Device device) {
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

    public class DeviceRowMapper implements RowMapper<Device> {

        @Override
        public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Device(
                    rs.getString("imei"),
                    rs.getString("vendor"),
                    rs.getString("model"),
                    rs.getString("operatingSystem"),
                    rs.getString("operatingSystemVersion"));
        }
    }
}
