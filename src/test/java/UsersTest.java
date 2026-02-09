import com.example.userservice.entity.Users;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class UsersTest {

    @Test
    void testConstructorSetsCreatedAt() {
        Users user = new Users("Vasya", "vasya@test.com", 25);

        assertNotNull(user.getCreatedAt());
        assertTrue(user.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void testGettersAndSetters() {
        Users user = new Users();

        user.setId(1L);
        user.setName("Vika");
        user.setEmail("vika@test.com");
        user.setAge(30);
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);

        assertEquals(1L, user.getId());
        assertEquals("Vika", user.getName());
        assertEquals("vika@test.com", user.getEmail());
        assertEquals(30, user.getAge());
        assertEquals(now, user.getCreatedAt());
    }

    @Test
    void testToString() {
        Users user = new Users("Borya", "borya@test.com", 35);
        user.setId(42L);

        String expected = "Пользователь 42: Borya (borya@test.com), возраст: 35";
        assertEquals(expected, user.toString());
    }
}

