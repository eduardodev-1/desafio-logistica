package com.luizalabs.logistica.desafio.integration;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.luizalabs.logistica.desafio.application.usecase.SaveFileDataToDataBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
@Profile("test")
class FileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaveFileDataToDataBase saveFileDataToDataBase;

    private MockMultipartFile mockFile;

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14.3")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withReuse(true)
            .withCreateContainerCmdModifier(cmd -> cmd.withPortBindings(new PortBinding(Ports.Binding.bindPort(5439), new ExposedPort(5432))))
            .withCopyFileToContainer(
                    MountableFile.forHostPath(Paths.get("create_database.sql")),
                    "/docker-entrypoint-initdb.d/init.sql"
            );

    @BeforeAll
    public static void setUp() {
        postgresContainer.start();
    }

    @BeforeEach
    public void startMockFile() {
        mockFile = mockFile();
    }

    @Test
    public void testDatabaseConnection() {
        assertTrue(postgresContainer.isRunning());
    }

    @Test
    public void testUploadFileSuccess() throws Exception {
        mockMvc.perform(multipart("/api/file")
                        .file(mockFile))
                .andExpect(status().isOk());
    }

    private MockMultipartFile mockFile() {
        return new MockMultipartFile("file",
                "testfile.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308".getBytes());
    }
}
