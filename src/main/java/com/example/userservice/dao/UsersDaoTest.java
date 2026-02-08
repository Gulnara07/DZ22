package com.example.userservice.dao;

import com.example.userservice.entity.Users;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UsersDaoTest extends BaseDaoTest {

    private UsersDao usersDao;

    @BeforeEach
    void setUp() {
        usersDao = new UsersDao();
    }

    @AfterAll
    static void tearDown() {
        com.example.userservice.util.HibernateUtil.shutdown();
    }

    @Test
    @DisplayName("Создание пользователя")
    void shouldCreateUser() {
        Users user = new Users("Test", "test@example.com", 25);
        Long id = usersDao.create(user);
        assertNotNull(id);
    }

    @Test
    @DisplayName("Поиск по ID")
    void shouldFindById() {
        Users user = new Users("Alice", "alice@example.com", 30);
        Long id = usersDao.create(user);

        Users found = usersDao.findById(id);
        assertNotNull(found);
        assertEquals("Alice", found.getName());
    }

    @Test
    @DisplayName("Получение всех пользователей")
    void shouldFindAll() {
        usersDao.create(new Users("User1", "user1@example.com", 20));
        usersDao.create(new Users("User2", "user2@example.com", 25));

        List<Users> users = usersDao.findAll();
        assertTrue(users.size() >= 2);
    }

    @Test
    @DisplayName("Обновление пользователя")
    void shouldUpdateUser() {
        Users user = new Users("Old", "old@example.com", 25);
        Long id = usersDao.create(user);

        Users toUpdate = usersDao.findById(id);
        toUpdate.setName("New");
        usersDao.update(toUpdate);

        Users updated = usersDao.findById(id);
        assertEquals("New", updated.getName());
    }

    @Test
    @DisplayName("Удаление пользователя")
    void shouldDeleteUser() {
        Users user = new Users("Delete", "delete@example.com", 40);
        Long id = usersDao.create(user);

        usersDao.delete(id);
        Users deleted = usersDao.findById(id);
        assertNull(deleted);
    }
}