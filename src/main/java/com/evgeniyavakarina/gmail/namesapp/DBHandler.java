package com.evgeniyavakarina.gmail.namesapp;

import com.mysql.cj.jdbc.Driver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHandler {
    private SimpleDriverDataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NameRowMapper nameRowMapper;

    public DBHandler() throws SQLException {
        dataSource = new SimpleDriverDataSource(
                new Driver(),
                "jdbc:mysql://localhost:3306/simple?useSSL=false",
                "nameuser",
                "nameuser"
        );

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS names (\n" +
                "     id MEDIUMINT NOT NULL AUTO_INCREMENT,\n" +
                "     name TEXT NOT NULL,\n" +
                "     PRIMARY KEY (id)\n" +
                ");");
    }

    public boolean putName(String name) {
        return jdbcTemplate.update("INSERT INTO names (name) VALUES (?);", name) == 1;
    }

    public ArrayList<String> getNames () {
        List<NameData> data = jdbcTemplate.query("SELECT * FROM names;", new NameRowMapper());
        ArrayList<String> result = new ArrayList<>();
        for (NameData nameData :
                data) {
            result.add(nameData.toString());
        }
        return result;
    }

    public String getName(int id) {
        NameData data = jdbcTemplate.query(
                "SELECT * FROM names WHERE id IN (?);",
                new Object[]{id},
                new NameRowMapper())
                .get(0);
        return data.getName();
    }
    public ArrayList<String> getNames(int fromId, int toId) {
        return null;
    }

    public void close() {
        try {
            dataSource.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
