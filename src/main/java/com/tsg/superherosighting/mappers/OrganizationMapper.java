package com.tsg.superherosighting.mappers;

import com.tsg.superherosighting.dto.Organization;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationMapper implements RowMapper<Organization> {

    @Override
    public Organization mapRow(ResultSet rs, int index) throws SQLException {
        Organization Organization = new Organization();
        Organization.setId(rs.getInt("id"));
        Organization.setName(rs.getString("name"));
        Organization.setDescription(rs.getString("description"));
        return Organization;
    }

}
