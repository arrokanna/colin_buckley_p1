package dev.colin.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection createConnection(){

        try {
            Connection conn = DriverManager.getConnection(System.getenv("Project1"));
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            String method = "createConnection()";
            Logger.log(message, LogLevel.ERROR,method,"ConnectionUtil");
            return null;
        }

    }

}
