package com.projectlib.librarian.controller;

import com.projectlib.librarian.dto.AuthorDTO;

import com.projectlib.librarian.service.AuthorImplementation;

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
@DisplayName("Author controllers tests")
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorImplementation authorImplementation;

    @Value("${jwt.test.token}")
    private String JWT_TOKEN_TEST;


    @Test
    @DisplayName("Get all authors")
    public void testfindAllAuthorsTest() throws Exception {

        AuthorDTO authorDTO = mock(AuthorDTO.class);
        AuthorDTO authorDTO2 = mock(AuthorDTO.class);

        List<AuthorDTO> authorDTOList = new ArrayList<>();

        authorDTOList.add(authorDTO);
        authorDTOList.add(authorDTO2);

        when(authorImplementation.getAllAuthors()).thenReturn(authorDTOList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/authors/findAll").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()
                );
    }

    @Test
    @DisplayName("Get author by ID")
    public void testfindAuthorByIdTest() throws Exception {

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("John");

        when(authorImplementation.getAuthorById(1L)).thenReturn(authorDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/authors/find/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"));
    }

    @Test
    @DisplayName("Add new author")
    public void saveAuthorTest() throws Exception {

        AuthorDTO authorDTO = mock(AuthorDTO.class);
        when(authorImplementation.createAuthor(authorDTO)).thenReturn(anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/authors/save").with(csrf())
                        .content("{\"name\":\"Example name\", \"surname\":\"Example surname\", \"books\":[]}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Update an author")
    public void updateAuthorTest() throws Exception {

        AuthorDTO updatedAuthorDTO = new AuthorDTO();

        updatedAuthorDTO.setId(1L);
        updatedAuthorDTO.setName("Updated Name");

        when(authorImplementation.updateAuthor(1L, updatedAuthorDTO)).thenReturn("Author updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/authors/update/1").with(csrf())
                        .content("{\"id\":1,\"name\":\"Updated Name\",\"surname\":\"Example surname\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Delete an author")
    public void deleteAuthorTest() throws Exception {

        when(authorImplementation.deleteAuthor(1L)).thenReturn("Author with ID 1 deleted successfully.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/delete/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Author with ID 1 deleted successfully."));
    }

    @Test
    @DisplayName("Set author status")
    public void setAuthorStatusTest() throws Exception {
        Long authorId = 1L;
        boolean status = false;

        when(authorImplementation.setStatus(authorId, status)).thenReturn("Author status updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/authors/setstatus/1").with(csrf())
                        .content("{\"status\":false}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(status().isOk());
    }
}
