package com.luizalabs.logistica.desafio.application.usecase;

import com.luizalabs.logistica.desafio.domain.entity.FileData;
import com.luizalabs.logistica.desafio.domain.service.FileProcessor;
import com.luizalabs.logistica.desafio.infra.repository.FileDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SaveFileDataToDataBase {
    private final FileDataRepository fileDataRepository;

    public SaveFileDataToDataBase(FileDataRepository fileDataRepository) {
        this.fileDataRepository = fileDataRepository;
    }

    public String execute(MultipartFile file) throws IOException {
        FileData fileData = FileProcessor.processFile(file);
        fileDataRepository.save(fileData);
        return "recebido com sucesso";
    }
}
