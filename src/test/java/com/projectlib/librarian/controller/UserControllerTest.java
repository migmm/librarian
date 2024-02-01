package com.projectlib.librarian.controller;

import com.projectlib.librarian.dto.UserDTO;

import com.projectlib.librarian.service.UserImplementation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("User controllers tests")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserImplementation userImplementation;

    @Value("${jwt.test.token}")
    private String JWT_TOKEN_TEST;


    @Test
    @DisplayName("Get all users")
    public void testGetAllUsersTest() throws Exception {

        UserDTO userDTO = mock(UserDTO.class);
        UserDTO userDTO2 = mock(UserDTO.class);

        List<UserDTO> userDTOList = new ArrayList<>();

        userDTOList.add(userDTO);
        userDTOList.add(userDTO2);

        when(userImplementation.getAllUsers()).thenReturn(userDTOList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/findAll").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()
                );
    }

    @Test
    @DisplayName("Get user by ID")
    public void testGetUserByIdTest() throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("Example name");
        userDTO.setSurname("Example surname");
        userDTO.setEmail("email@email.com");
        userDTO.setUsername("username");
        userDTO.setPassword("pass1234");

        when(userImplementation.getUserById(1L)).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/find/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Example name"));
    }

    @Test
    @DisplayName("Add new user")
    public void addNewUserTest() throws Exception {

        UserDTO userDTO = mock(UserDTO.class);
        when(userImplementation.createUser(userDTO)).thenReturn(anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/save").with(csrf())
                        .content("{\"name\":\"Example name\", \"surname\":\"Example surname\", \"email\":\"email@email.com\", \"username\":\"username\" , \"password\":\"pass1234\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Edit a user")
    public void editUserTest() throws Exception {

        UserDTO updatedUserDTO = new UserDTO();

        updatedUserDTO.setId(1L);
        updatedUserDTO.setName("Updated Name");

        when(userImplementation.updateUser(1L, updatedUserDTO)).thenReturn("User updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/users/update/1").with(csrf())
                        .content("{\"name\":\"Example name edited\", \"surname\":\"Example surname edited\", \"email\":\"emails@email.com\", \"username\":\"usernames\" , \"password\":\"passs1234\"}")                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Delete an user")
    public void deleteUserTest() throws Exception {

        when(userImplementation.deleteUser(1L)).thenReturn("User with ID 1 deleted successfully.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User with ID 1 deleted successfully."));
    }

    @Test
    @DisplayName("Set user status")
    public void setUserStatusTest() throws Exception {
        Long userId = 1L;
        boolean status = false;

        when(userImplementation.setStatus(userId, status)).thenReturn("User status updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/users/setstatus/1").with(csrf())
                        .content("{\"status\":false}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(status().isOk());
    }
}
