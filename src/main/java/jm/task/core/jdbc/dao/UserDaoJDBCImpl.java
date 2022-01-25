package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection conn = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement stat = conn.createStatement()) {
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS tabl(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), " +
                    "lastName VARCHAR(100), age TINYINT)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement stat = conn.createStatement()) {
            stat.executeUpdate("DROP TABLE IF EXISTS tabl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String nam, String lastNam, byte ag) {
        try (PreparedStatement prestat = conn.prepareStatement("INSERT INTO tabl(name, lastName, age) VALUES(?, ?, ?)")) {
            prestat.setString(1, nam);
            prestat.setString(2, lastNam);
            prestat.setByte(3, ag);
            prestat.executeUpdate();
            System.out.println("User с именем - " + nam + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement prestat = conn.prepareStatement("DELETE FROM tabl WHERE id = ?")) {
            prestat.setLong(1, id);
            prestat.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> alluser = new ArrayList<>();
        try (Statement stat = conn.createStatement()) {
            ResultSet rs = stat.executeQuery("SELECT * FROM tabl");
            while(rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("lastName"), rs.getByte("age"));
                user.setId(rs.getLong("id"));
                alluser.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alluser;
    }

    public void cleanUsersTable() {
        try (Statement stat = conn.createStatement()) {
            stat.executeUpdate("TRUNCATE TABLE tabl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
