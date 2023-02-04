package com.agenda.domain.service;

import com.agenda.domain.entity.Agenda;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public interface ReadAgendaFileService {

    List<Agenda> readArquivo(BufferedReader reader) throws IOException;
}
