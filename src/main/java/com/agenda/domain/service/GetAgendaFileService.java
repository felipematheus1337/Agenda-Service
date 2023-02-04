package com.agenda.domain.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GetAgendaFileService {

    boolean uploadFile(MultipartFile file) throws IOException;
}
