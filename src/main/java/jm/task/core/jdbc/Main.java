package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.Arrays;
import java.util.List;

import static jm.task.core.jdbc.util.Util.closeconn;
import static jm.task.core.jdbc.util.Util.closesesFac;

public class Main {
    private static final UserService uS = new UserServiceImpl();
    public static void main(String[] args) {

        uS.createUsersTable();
        uS.saveUser("Вася", "Иванов", (byte) 34);
        uS.saveUser("Олеся", "Попова", (byte) 27);
        uS.saveUser("Иван", "Петров", (byte) 42);
        uS.saveUser("Катя", "Лесина", (byte) 22);
        System.out.println(uS.getAllUsers());
        uS.cleanUsersTable();
        uS.dropUsersTable();
        //closeconn();
        closesesFac();
    }
}
