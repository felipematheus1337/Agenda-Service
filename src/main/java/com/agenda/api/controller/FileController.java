package com.agenda.api.controller;


import com.agenda.domain.service.impl.GetAgendaFileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.stream.Streams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1/file")
@RequiredArgsConstructor
public class FileController {


    private final GetAgendaFileServiceImpl agendaFileService;

    @PostMapping("/upload")
    public ResponseEntity<String> salvarAgendamentosDeArquivos(@RequestParam("file")MultipartFile file) throws IOException {
         return agendaFileService.uploadFile(file) ?
                 ResponseEntity.status(HttpStatus.CREATED).body("Agendamentos salvos com sucesso!") :
                 ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
