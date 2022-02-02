package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

//import static jm.task.core.jdbc.util.Util.getSesFac;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sesFac = Util.getSesFac();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction trans = null;
        Session ses = null;
        try {
            ses = sesFac.openSession();
            trans = ses.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS tabl(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100), lastName VARCHAR(100), age TINYINT)";
            Query quer = ses.createSQLQuery(sql).addEntity(User.class);
            quer.executeUpdate();
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            try {
                ses.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction trans = null;
        Session ses = null;
        try {
            ses = sesFac.openSession();
            trans = ses.beginTransaction();
            ses.createSQLQuery("DROP TABLE IF EXISTS tabl").addEntity(User.class).executeUpdate();
            trans.commit();
        } catch(Exception e) {
            e.printStackTrace();
            if(trans != null) {
                trans.rollback();
            }
        } finally {
            try {
                ses.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session ses = null;
        Transaction trans = null;
        User us = new User(name, lastName, age);
        try {
            ses = sesFac.openSession();
            trans = ses.beginTransaction();
            ses.save(us);
            trans.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch(Exception e) {
            e.printStackTrace();
            if(trans != null) {
                trans.rollback();
            }
        } finally {
            try {
                ses.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session ses = null;
        Transaction trans = null;
        try {
            ses = sesFac.openSession();
            trans = ses.beginTransaction();
            //ses.createQuery("delete  User where id = :id").setParameter("id", id).executeUpdate();
            User us = (User) ses.get(User.class, id);
            if(us != null) {
                ses.delete(us);
            }
            trans.commit();
        } catch(Exception e) {
            e.printStackTrace();
            if(trans != null) {
                trans.rollback();
            }
        } finally {
            try {
                ses.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        Transaction trans = null;
        Session ses = null;
        try {
            ses = sesFac.openSession();
            trans = ses.beginTransaction();
            users = ses.createQuery("FROM User").list();
           // users = ses.createQuery("SELECT i FROM User i", User.class).getResultList();
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            try {
                ses.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session ses = null;
        Transaction trans = null;
        try {
            ses = sesFac.openSession();
            trans = ses.beginTransaction();
            ses.createQuery("DELETE FROM tabl u").executeUpdate();
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans != null) {
                trans.rollback();
            }
        } finally {
            try {
                ses.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
