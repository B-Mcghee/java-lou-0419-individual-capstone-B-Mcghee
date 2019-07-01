package com.tsg.superherosighting.mappers;

import com.tsg.superherosighting.dto.SuperHero;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SuperHeroMapper implements RowMapper<SuperHero> {
    @Override
    public SuperHero mapRow(ResultSet rs, int index) throws SQLException {
        SuperHero superHero = new SuperHero();
        superHero.setId(rs.getInt("Id"));
        superHero.setName(rs.getString("name"));
        superHero.setDescription(rs.getString("description"));
        superHero.setVillian(rs.getBoolean("isVillian"));
        return superHero;
    }

}