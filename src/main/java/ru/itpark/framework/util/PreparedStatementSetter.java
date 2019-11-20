package ru.itpark.framework.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementSetter{
  PreparedStatement setValues(PreparedStatement preparedStatement) throws SQLException;
}
