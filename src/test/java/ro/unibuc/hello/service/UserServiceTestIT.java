package ro.unibuc.hello.service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ro.unibuc.hello.dto.UserDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@SpringBootTest
@Tag("IT")
public class UserServiceTestIT {
    @Autowired
    private UserService userService;

    private UserDTO userOriginal = new UserDTO("1", "test1@email.com", "test1");;

    private void checkUsers(UserDTO user1, UserDTO user2) {
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getEmail(), user2.getEmail());
        assertEquals(user1.getParola(), user2.getParola());
    }

    @Test
    void test_createUser() {
        userService.deleteAll();
        UserDTO userCheck = userService.createUser(userOriginal);

        checkUsers(userOriginal, userCheck);
        userService.deleteAll();
    }

    @Test
    void test_getUser() {
        userService.deleteAll();
        userService.createUser(userOriginal);
        UserDTO userCheck = userService.getUser(userOriginal.getId());

        checkUsers(userOriginal, userCheck);
        userService.deleteAll();
    }

    @Test
    void test_getAllUsers() {
        userService.deleteAll();
        UserDTO secondUser = new UserDTO("2", "test2@email.com", "test2");
    
        userService.createUser(userOriginal);
        userService.createUser(secondUser);
        List<UserDTO> userCheckList = userService.getAll();

        checkUsers(userOriginal, userCheckList.get(0));
        checkUsers(secondUser, userCheckList.get(1));
        userService.deleteAll();
    }

    @Test
    void test_updateOriginalUser() {
        userService.deleteAll();
        UserDTO secondUser = new UserDTO("2", "test2@email.com", "test2");

        userService.createUser(userOriginal);
        userService.createUser(secondUser);

        // Integrity checks
        assertEquals("1", userOriginal.getId());
        assertEquals("test1@email.com", userOriginal.getEmail());
        assertEquals("test1", userOriginal.getParola());

        userService.createUser(userOriginal);
        UserDTO updatedUser = new UserDTO("1", "updatedUser@email.com", "updated");

        userService.updateuser(updatedUser);
        userOriginal = userService.getUser("1");

        assertEquals("1", userOriginal.getId());
        assertEquals("updatedUser@email.com", userOriginal.getEmail());
        assertEquals("updated", userOriginal.getParola());
        userService.deleteAll();
    }

    @Test
    void test_deleteUser() {
        userService.deleteAll();
        UserDTO secondUser = new UserDTO("2", "test2@email.com", "test2");

        userService.createUser(secondUser);
        userService.deleteuser("2");

        try {
            userService.getUser("2"); 
        } catch (Exception e) {
            assertTrue(true);
        }
        userService.deleteAll();
    }
}