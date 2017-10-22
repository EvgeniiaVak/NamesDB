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
        return getNames(1, getNumberOfNames());
    }

    public ArrayList<String> getNames(int id) {
        return getNames(id, id);
    }
    public ArrayList<String> getNames(int fromId, int toId) {
        List<NameData> data = jdbcTemplate.query(
                "SELECT * FROM names WHERE id BETWEEN ? AND ?;",
                new Object[]{fromId, toId},
                new NameRowMapper());
        ArrayList<String> result = new ArrayList<>();
        for (NameData nameData : data) {
            result.add(nameData.toString());
        }
        return result;
    }

    public void close() {
        try {
            dataSource.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNumberOfNames() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM names", Integer.class);
    }
}
