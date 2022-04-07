package main;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MoviesJDBCTest {
private static Connection connection = null;

    public static void main(String[] args) throws SQLException {
        DriverManager.registerDriver(new Driver());
        connection = DriverManager.getConnection(TestConfig.getHost(),
                TestConfig.getUser(),
                TestConfig.getPassword()
        );
    }
}
