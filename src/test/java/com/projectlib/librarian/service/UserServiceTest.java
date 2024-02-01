package com.projectlib.librarian.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.projectlib.librarian.dto.UserDTO;
import com.projectlib.librarian.exception.NotFoundException;
import com.projectlib.librarian.model.UserModel;
import com.projectlib.librarian.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserImplementation userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void getAllUsersTest() {

        UserModel user1 = mock(UserModel.class);
        UserModel user2 = mock(UserModel.class);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
    }

    @Test
    void createUserTest() {
        UserDTO userDto = mock(UserDTO.class);
        UserModel user = mock(UserModel.class);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        String result = userService.createUser(userDto);

        assertEquals("User created successfully.", result);
    }


    @Test
    void deleteUserTest() {

        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        String result = userService.deleteUser(userId);

        assertEquals("User with ID " + userId + " deleted successfully.", result);
    }

    @Test
    void getUserByIdTest() {

        Long userId = 1L;
        UserModel user = new UserModel();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(userId);

        assertEquals(userId, result.getId());
    }

    @Test
    void getUserByIdNotFoundTest() {

        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void updateUserNotFoundTest() {

        Long userId = 1L;
        UserDTO updatedUserDTO = new UserDTO();
        updatedUserDTO.setId(userId);

        assertThrows(NotFoundException.class, () -> userService.updateUser(userId, updatedUserDTO));
    }

    @Test
    void deleteUserNotFoundTest() {

        Long userId = 1L;
        doThrow(new NotFoundException("User with ID " + userId + " does not exist.")).when(userRepository)
                .deleteById(userId);

        assertThrows(NotFoundException.class, () -> userService.deleteUser(userId));
    }
}
