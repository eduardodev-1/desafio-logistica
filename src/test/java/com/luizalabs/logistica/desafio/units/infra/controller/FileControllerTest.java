package com.luizalabs.logistica.desafio.units.infra.controller;

import com.luizalabs.logistica.desafio.application.usecase.SaveFileDataToDataBase;
import com.luizalabs.logistica.desafio.infra.controller.FileController;
import com.luizalabs.logistica.desafio.infra.exception.FailedToProcessFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaveFileDataToDataBase saveFileDataToDataBase;

    private MockMultipartFile mockFile;

    @BeforeEach
    public void setUp() {
        mockFile = mockFile();
    }

    @Test
    public void testUploadFileSuccess() throws Exception {
        mockMvc.perform(multipart("/api/file")
                        .file(mockFile))
                .andExpect(status().isOk());
    }

    @Test
    public void testUploadFileIOException() throws Exception {
        doThrow(new FailedToProcessFileException("Error processing file")).when(saveFileDataToDataBase).execute(Mockito.any());
        mockMvc.perform(multipart("/api/file").file(mockFile)).andExpect(status().isInternalServerError());
    }

    private MockMultipartFile mockFile() {
        return new MockMultipartFile("file",
                "testfile.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308".getBytes());
    }
}

