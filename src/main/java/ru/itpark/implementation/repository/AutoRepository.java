package ru.itpark.implementation.repository;

import ru.itpark.framework.annotation.Component;
import ru.itpark.framework.util.JdbcTemplate;
import ru.itpark.implementation.model.Auto;

import java.util.List;

@Component
public class AutoRepository {
    private JdbcTemplate jdbcTemplate;

    public AutoRepository() {
        jdbcTemplate = new JdbcTemplate();

        String sql = "CREATE TABLE IF NOT EXISTS autos (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, description TEXT NOT NULL, imageUrl TEXT);";
        jdbcTemplate.executeUpdate(sql, statement -> statement);
    }

    public List<Auto> getAll() {
        String sql = "SELECT id, name, description, imageUrl FROM autos";
        final List<Auto> autos;
        autos = jdbcTemplate.executeQuery(sql, stmt -> stmt, resultSet -> new Auto(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("imageUrl")
        ));
        return autos;
    }

    public Auto getAutoById(int id) {
        String sql = "SELECT * FROM autos WHERE id=?;";
        final List<Auto> autos;
        autos = jdbcTemplate.executeQuery(
                sql,
                statement -> {
                    statement.setInt(1, id);
                    return statement;
                },
                resultSet -> new Auto(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("imageUrl")
                ));
        return autos.get(0);
    }

    public List<Auto> getAutoByName(String name) {
        String sql = "SELECT * FROM autos WHERE name=?;";
        final List<Auto> autos;
        autos = jdbcTemplate.executeQuery(
                sql,
                statement -> {
                    statement.setString(1, name);
                    return statement;
                },
                resultSet -> new Auto(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("imageUrl")
                ));
        return autos;
    }

    public void saveAuto(Auto auto) {
        String sql = "INSERT INTO autos (name, description, imageUrl) VALUES (?, ?, ?);";

        jdbcTemplate.executeUpdate(sql, statement -> {
            statement.setString(1, auto.getName());
            statement.setString(2, auto.getDescription());
            statement.setString(3, auto.getImageUrl());
            return statement;
        });
    }
}
