package org.example.util;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.util.exception.JdbcUtilException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.function.Consumer;

public class JdbcUtil {
    private final static String DEFAULT_DATABASE_NAME = "db_webshop";
    private final static String DEFAULT_USERNAME = "root";
    private final static String DEFAULT_PASSWORD = "qwerty";
    private static DataSource dataSource;

    public static DataSource createDefaultDataSource() {
        String url = formatMysqlDbUrl(DEFAULT_DATABASE_NAME);
        return createMysqlDataSource(url, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static DataSource createMysqlDataSource(String url, String username, String pass) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(pass);
        dataSource.setUrl(url);
        return dataSource;
    }

    private static String formatMysqlDbUrl(String databaseName) {
        return String.format("jdbc:mysql://localhost:13306/%s", databaseName);
    }

    private static DataSource checkDataSource() {
        if (dataSource == null) createDefaultDataSource();
        return dataSource;
    }

    public static void performQuery(Consumer<Connection> operation) {
        try (Connection con = checkDataSource().getConnection()) {
            operation.accept(con);
        } catch (Exception e) {
            throw new JdbcUtilException("Error performing JDBC operation. Transaction is rolled back", e);
        }
    }
}
