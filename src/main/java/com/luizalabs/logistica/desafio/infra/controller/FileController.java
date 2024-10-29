package com.luizalabs.logistica.desafio.infra.controller;

import com.luizalabs.logistica.desafio.application.usecase.SaveFileDataToDataBase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileController {
    private final SaveFileDataToDataBase saveFileDataToDataBase;

    public FileController(SaveFileDataToDataBase saveFileDataToDataBase) {
        this.saveFileDataToDataBase = saveFileDataToDataBase;
    }

    @PostMapping("/file")
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {
        saveFileDataToDataBase.execute(file);
        return ResponseEntity.ok().build();
    }
}
