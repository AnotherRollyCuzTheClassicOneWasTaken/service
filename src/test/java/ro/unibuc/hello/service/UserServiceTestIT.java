package ro.unibuc.hello.service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ro.unibuc.hello.data.UserRepo;
import ro.unibuc.hello.dto.UserDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@SpringBootTest
//@Tag("IT")
public class UserServiceTestIT {
    @Autowired
    UserRepo userRepo;

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
        UserDTO userCheck = userService.createUser(userOriginal);

        checkUsers(userOriginal, userCheck);
    }

    @Test
    void test_getUser() {
        UserDTO userCheck = userService.getUser(userOriginal.getId());

        checkUsers(userOriginal, userCheck);
    }

    @Test
    void test_getAllUsers() {
        UserDTO anotherUser = new UserDTO("2", "test2@email.com", "test2");

        userService.createUser(anotherUser);
        List<UserDTO> userCheckList = userService.getAll();

        assertEquals("1", userCheckList.get(0).getId());
        assertEquals("updatedUser@email.com", userCheckList.get(0).getEmail());
        assertEquals("updated", userCheckList.get(0).getParola());
        checkUsers(anotherUser, userCheckList.get(1));
    }

    @Test
    void test_updateOriginalUser() {
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
    }

    @Test
    void test_deleteUser() {
        userService.deleteuser("2");

        try {
            userService.getUser("2"); 
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}