package com.example.userservice.dao;
import com.example.userservice.entity.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import com.example.userservice.util.HibernateUtil;
public class UsersDao {

    // Создать пользователя
    public Long create(Users user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user.getId();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка создания: " + e.getMessage(), e);
        }
    }

    // Найти по ID
    public Users findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Users.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка поиска: " + e.getMessage(), e);
        }
    }

    // Все пользователи
    public List<Users> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Users> query = session.createQuery("FROM Users", Users.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка получения: " + e.getMessage(), e);
        }
    }

    // Обновить
    public void update(Users user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка обновления: " + e.getMessage(), e);
        }
    }

    // Удалить
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Users user = session.get(Users.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка удаления: " + e.getMessage(), e);
        }
    }

    // Проверить email
    public boolean emailExists(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(*) FROM Users WHERE email = :email", Long.class);
            query.setParameter("email", email);
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка проверки email: " + e.getMessage(), e);
        }
    }
}