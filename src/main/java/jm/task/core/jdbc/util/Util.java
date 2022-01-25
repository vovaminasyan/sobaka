package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Util {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/trest2?useSSL=false&serverTimezone=UTC", "root", "555648");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
