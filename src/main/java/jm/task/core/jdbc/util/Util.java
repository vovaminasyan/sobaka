package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Util {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/trest2?useSSL=false&serverTimezone=UTC", "root", "555648");
            conn.setAutoCommit(false);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    private static SessionFactory sesFac;

    public static SessionFactory getSesFac() {
        if (sesFac == null) {
            try {
                Configuration config = new Configuration();
                Properties set = new Properties();
                set.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                set.put(Environment.URL, "jdbc:mysql://localhost:3306/trest2?useSSL=false&serverTimezone=UTS");
                set.put(Environment.USER, "root");
                set.put(Environment.PASS, "555648");
                set.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                set.put(Environment.SHOW_SQL, "true");
                set.put(Environment.HBM2DDL_AUTO, "update");
                set.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                config.setProperties(set);
                config.addAnnotatedClass(User.class);
                ServiceRegistry serReg = new StandardServiceRegistryBuilder().
                        applySettings(config.getProperties()).build();
                sesFac = config.buildSessionFactory(serReg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sesFac;
    }

    public static void closeconn() {
        try {
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closesesFac() {
        try {
            getSesFac().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
