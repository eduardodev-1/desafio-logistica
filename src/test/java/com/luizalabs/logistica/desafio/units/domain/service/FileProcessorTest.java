package com.luizalabs.logistica.desafio.units.domain.service;

import com.luizalabs.logistica.desafio.domain.entity.FileProcessorResult;
import com.luizalabs.logistica.desafio.domain.entity.Order;
import com.luizalabs.logistica.desafio.domain.entity.User;
import com.luizalabs.logistica.desafio.domain.service.FileProcessor;
import com.luizalabs.logistica.desafio.infra.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class FileProcessorTest {

    @Mock
    private MultipartFile mockFile;

    private FileProcessor fileProcessor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fileProcessor = new FileProcessor();
    }

    @Test
    public void testProcessFileSuccess() throws IOException {
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(validLine().getBytes()));

        FileProcessorResult result = fileProcessor.processFile(mockFile);

        assertEquals(1, result.orders().size());
        assertEquals(1, result.users().size());

        Order order = result.orders().getFirst();
        User user = result.users().getFirst();

        assertEquals(753L, order.getId());
        assertEquals(new BigDecimal("1836.74"), order.getTotal());
        assertEquals(LocalDate.of(2021, 3, 8), order.getDate());
        assertEquals(1, order.getProducts().size());
        assertEquals(70L, user.getId());
        assertEquals("Palmer Prosacco", user.getName());
    }

    @Test
    public void testProcessFileInvalidLine() throws IOException {
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(shortLine().getBytes()));
        assertThrows(InvalidFileLineException.class, () -> fileProcessor.processFile(mockFile));
    }

    @Test
    public void testProcessFileInvalidDate() throws IOException {
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(invalidDateLine().getBytes()));
        assertThrows(InvalidDateException.class, () -> fileProcessor.processFile(mockFile));
    }

    @Test
    public void testProcessFileInvalidProductValue() throws IOException {
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(invalidProductValueLine().getBytes()));
        assertThrows(InvalidProductValueException.class, () -> fileProcessor.processFile(mockFile));
    }

    @Test
    public void testProcessFileInvalidProductId() throws IOException {
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(invalidProductIdLine().getBytes()));
        assertThrows(InvalidProductIdException.class, () -> fileProcessor.processFile(mockFile));
    }

    @Test
    public void testProcessFileInvalidOrderId() throws IOException {
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(invalidOrderIdLine().getBytes()));
        assertThrows(InvalidOrderIdException.class, () -> fileProcessor.processFile(mockFile));
    }

    @Test
    public void testProcessFileEmptyUserName() throws IOException {
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(emptyUserNameLine().getBytes()));
        assertThrows(InvalidUserNameException.class, () -> fileProcessor.processFile(mockFile));
    }

    @Test
    public void testProcessFileInvalidUserId() throws IOException {
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(invalidUserIdLine().getBytes()));
        assertThrows(InvalidUserIdException.class, () -> fileProcessor.processFile(mockFile));
    }

    private String shortLine() {
        return "0000000070"; // Linha propositalmente curta para causar o IndexOutOfBoundsException
    }

    private String validLine() {
        return "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308";
    }

    private String invalidDateLine() {
        return "0000000070                              Palmer Prosacco00000007530000000003     1836.742021xx08";
    }

    private String invalidProductValueLine() {
        return "0000000070                              Palmer Prosacco00000007530000000003     abcdef.7420210308";
    }

    private String invalidProductIdLine() {
        return "0000000070                              Palmer Prosacco0000000753abcdef0003     1836.7420210308";
    }

    private String invalidOrderIdLine() {
        return "0000000070abcdef                         Palmer Prosacco00000007530000000003     1836.7420210308";
    }

    private String emptyUserNameLine() {
        return "0000000070                                                            00000007530000000003     1836.7420210308";
    }

    private String invalidUserIdLine() {
        return "00000000abcdef                         Palmer Prosacco00000007530000000003     1836.7420210308";
    }
}

