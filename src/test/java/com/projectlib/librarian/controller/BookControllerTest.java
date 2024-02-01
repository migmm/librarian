package com.projectlib.librarian.controller;

import com.projectlib.librarian.dto.BookDTO;
import com.projectlib.librarian.service.BookImplementation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Book controllers tests")
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookImplementation bookImplementation;

    @Value("${jwt.test.token}")
    private String JWT_TOKEN_TEST;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Get all books")
    public void testGetAllBooksTest() throws Exception {

        BookDTO bookDTO = mock(BookDTO.class);
        BookDTO bookDTO2 = mock(BookDTO.class);

        List<BookDTO> bookDTOList = new ArrayList<>();

        bookDTOList.add(bookDTO);
        bookDTOList.add(bookDTO2);

        when(bookImplementation.getAllBooks()).thenReturn(bookDTOList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/books/findAll").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()
                );
    }

    @Test
    @DisplayName("Get book by ID")
    public void testGetBookByIdTest() throws Exception {

        BookDTO bookDTO = mock(BookDTO.class);
        BookDTO bookDTO2 = mock(BookDTO.class);

        List<BookDTO> bookDTOList = new ArrayList<>();

        bookDTOList.add(bookDTO);
        bookDTOList.add(bookDTO2);

        when(bookImplementation.getBookById(1L)).thenReturn(bookDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/books/find/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Save new book")
    public void saveNewBookTest() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setISBN(1234567890123L);
        bookDTO.setTitle("Example Title");
        bookDTO.setYear(new SimpleDateFormat("yyyy").parse("2023"));
        bookDTO.setBooks_quantity(10);
        bookDTO.setGenre("Example Genre");

        when(bookImplementation.createBook(eq(bookDTO), anyList())).thenReturn("Book created successfully.");

        String bookJson = "{\"ISBN\":1234567890123,\"title\":\"Example Title\",\"year\":2023,\"books_quantity\":10,\"genre\":\"Example Genre\"}";

        MockMultipartFile bookJsonPart = new MockMultipartFile("book", "book.json", MediaType.APPLICATION_JSON_VALUE, bookJson.getBytes());
        MockMultipartFile imagesPart = new MockMultipartFile("images", "image1.jpg", MediaType.IMAGE_JPEG_VALUE, "Image1Content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/books/save")
                        .file(bookJsonPart)
                        .file(imagesPart)
                        .with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Update a book")
    public void eupdateBookTest() throws Exception {

        BookDTO updatedBookDTO = new BookDTO();
        updatedBookDTO.setId(1L);
        updatedBookDTO.setISBN(1234567890123L);
        updatedBookDTO.setTitle("Updated Title");
        updatedBookDTO.setYear(new SimpleDateFormat("yyyy").parse("2023"));
        updatedBookDTO.setBooks_quantity(10);
        updatedBookDTO.setGenre("Example Genre");

        when(bookImplementation.updateBook(eq(1L), eq(updatedBookDTO), anyList())).thenReturn("Book updated successfully.");

        String updatedBookJson = "{\"id\":1,\"ISBN\":1234567890123,\"title\":\"Updated Title\",\"year\":2023,\"books_quantity\":10,\"genre\":\"Example Genre\"}";

        byte[] updatedBookJsonBytes = updatedBookJson.getBytes();

        MockMultipartFile bookJsonPart = new MockMultipartFile("book", "book.json", MediaType.APPLICATION_JSON_VALUE, updatedBookJsonBytes);

        MockMultipartFile imagesPart = new MockMultipartFile("images", "image1.jpg", MediaType.IMAGE_JPEG_VALUE, "Image1Content".getBytes());

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/books/update/1");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        mockMvc.perform(builder
                        .file(bookJsonPart)
                        .file(imagesPart)
                        .with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Delete an book")
    public void deleteBookTest() throws Exception {

        when(bookImplementation.deleteBook(1L)).thenReturn("Book with ID 1 deleted successfully.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/delete/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Book with ID 1 deleted successfully."));
    }

    @Test
    @DisplayName("Set book status")
    public void setBookStatusTest() throws Exception {
        Long bookId = 1L;
        boolean status = false;

        when(bookImplementation.setStatus(bookId, status)).thenReturn("Book status updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/books/setstatus/1").with(csrf())
                        .content("{\"status\":false}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(status().isOk());
    }
}
