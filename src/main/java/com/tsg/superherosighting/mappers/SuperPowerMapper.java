package com.tsg.superherosighting.mappers;

import com.tsg.superherosighting.dto.SuperPower;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SuperPowerMapper implements RowMapper<SuperPower> {
    @Override
    public SuperPower mapRow(ResultSet rs, int index) throws SQLException {
        SuperPower superPower = new SuperPower();
        superPower.setId(rs.getInt("Id"));
        superPower.setType(rs.getString("type"));
        return superPower;
    }

}
