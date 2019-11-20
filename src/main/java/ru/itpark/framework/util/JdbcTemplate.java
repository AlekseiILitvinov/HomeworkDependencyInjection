package ru.itpark.framework.util;


import ru.itpark.framework.exception.DataStoreException;
import ru.itpark.framework.exception.NoGeneratedKeysException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTemplate {
    private DataSource ds;

    public JdbcTemplate() {
        Context context = null;
        try {
            context = new InitialContext();
            ds = (DataSource) context.lookup("java:/comp/env/jdbc/db");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private <T> T executeInternal(String sql, PreparedStatementExecutor<T> executor) {
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            return executor.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataStoreException(e);
        }
    }

    public <T> List<T> executeQuery(
            String sql,
            PreparedStatementSetter preparedStatementSetter,
            RowMapper<T> mapper
    ) {
        return executeInternal(sql, stmt -> {
            try (ResultSet resultSet = preparedStatementSetter.setValues(stmt)
                    .executeQuery();) {
                List<T> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(mapper.map(resultSet));
                }
                return result;
            }
        });


    }

    public <T> Optional<T> executeQueryForObject(
            String sql,
            PreparedStatementSetter preparedStatementSetter,
            RowMapper<T> mapper
    ) {
        return executeInternal(sql, stmt -> {
            try (ResultSet resultSet = preparedStatementSetter
                    .setValues(stmt)
                    .executeQuery();) {
                if (resultSet.next()) {
                    return Optional.of(mapper.map(resultSet));
                }
                return Optional.empty();
            }
        });
    }

    public int executeUpdate(
            String sql,
            PreparedStatementSetter preparedStatementSetter
    ) {
        return executeInternal(sql, stmt -> preparedStatementSetter
                .setValues(stmt)
                .executeUpdate());
    }

    // FIXME: multiple keys
    public int executeUpdateWithGeneratedKey(
            String sql,
            PreparedStatementSetter preparedStatementSetter
    ) {
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            preparedStatementSetter
                    .setValues(statement)
                    .executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys();) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }

                throw new NoGeneratedKeysException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataStoreException(e);
        }
    }
}
