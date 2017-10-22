package com.evgeniyavakarina.gmail.namesapp;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NameRowMapper implements RowMapper<NameData> {

    @Override
    public NameData mapRow(ResultSet resultSet, int i) throws SQLException {
        NameData data = new NameData();
        data.setId(resultSet.getInt("id"));
        data.setName(resultSet.getString("name"));
        return data;
    }
}
