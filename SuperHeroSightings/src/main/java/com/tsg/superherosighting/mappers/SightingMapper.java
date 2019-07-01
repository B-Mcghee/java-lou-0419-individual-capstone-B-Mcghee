package com.tsg.superherosighting.mappers;


import com.tsg.superherosighting.dto.Sighting;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SightingMapper implements RowMapper<Sighting> {
    @Override
    public Sighting mapRow(ResultSet rs, int index) throws SQLException {
        Sighting sighting = new Sighting();
        sighting.setId(rs.getInt("Id"));
        sighting.setDate(rs.getTimestamp("date").toLocalDateTime());
        return sighting;
    }

}
