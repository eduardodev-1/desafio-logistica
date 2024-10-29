package com.luizalabs.logistica.desafio.application.usecase;

import com.luizalabs.logistica.desafio.domain.entity.FileProcessorResult;
import com.luizalabs.logistica.desafio.domain.service.FileProcessor;
import com.luizalabs.logistica.desafio.infra.repository.OrderRepository;
import com.luizalabs.logistica.desafio.infra.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SaveFileDataToDataBase {
    private final OrderRepository orderRepository;
    private final FileProcessor fileProcessor;
    private final UserRepository userRepository;

    public SaveFileDataToDataBase(OrderRepository orderRepository, FileProcessor fileProcessor, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.fileProcessor = fileProcessor;
        this.userRepository = userRepository;
    }

    public void execute(MultipartFile file) {
        FileProcessorResult result = fileProcessor.processFile(file);
        userRepository.saveAll(result.users());
        orderRepository.saveAll(result.orders());
    }
}
