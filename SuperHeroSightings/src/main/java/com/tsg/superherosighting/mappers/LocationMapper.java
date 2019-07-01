package com.tsg.superherosighting.mappers;

import com.tsg.superherosighting.dto.Location;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationMapper implements RowMapper<Location> {
    @Override
    public Location mapRow(ResultSet rs, int index) throws SQLException {
        Location location = new Location();
        location.setId(rs.getInt("Id"));
        location.setName(rs.getString("name"));
        location.setDescription(rs.getString("description"));
        location.setAddress(rs.getString("address"));
        location.setLatitude(rs.getBigDecimal("latitude"));
        location.setLongitude(rs.getBigDecimal("longitude"));
        return location;
    }

}
