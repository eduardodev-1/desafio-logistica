package com.luizalabs.logistica.desafio.integration;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.luizalabs.logistica.desafio.domain.entity.Order;
import com.luizalabs.logistica.desafio.domain.entity.Product;
import com.luizalabs.logistica.desafio.domain.entity.User;
import com.luizalabs.logistica.desafio.infra.repository.OrderRepository;
import com.luizalabs.logistica.desafio.infra.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
@Profile("test")
class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

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

    @Test
    public void testDatabaseConnection() {
        assertTrue(postgresContainer.isRunning());
    }

    @Test
    void testGetOrdersSuccess() throws Exception {
        createTestOrder(1L);
        mockMvc.perform(get("/api/orders")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetOrderByIdSuccess() throws Exception {
        createTestOrder(2L);
        mockMvc.perform(get("/api/orders/{id}", 2L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetOrderByUserIdSuccess() throws Exception {
        createTestOrder(3L);
        mockMvc.perform(get("/api/orders/user/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    private void createTestOrder(Long orderId) {
        User user = new User(1L, "Test User");
        Order order = new Order(orderId, LocalDate.now(), user);
        order.addProduct(new Product(1L, new BigDecimal("10.80")));
        userRepository.save(user);
        orderRepository.save(order);
    }
}
