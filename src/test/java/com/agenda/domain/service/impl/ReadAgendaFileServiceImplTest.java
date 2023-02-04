package com.agenda.domain.service.impl;

import com.agenda.domain.entity.Agenda;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReadAgendaFileServiceImplTest {


    @InjectMocks
    ReadAgendaFileServiceImpl readAgendaFileService;

    @Test
    void readArquivoComSucesso() throws IOException {
        // setup
        BufferedReader reader = mock(BufferedReader.class);
        List<String> lines = Arrays.asList("1,Consulta,2022-01-01T10:00:00", "2,Retorno,2022-02-01T10:00:00");
        when(reader.readLine()).thenReturn(lines.get(0), lines.get(1), null);

        // call method under test
        List<Agenda> agendas = readAgendaFileService.readArquivo(reader);

        // assertions
        assertNotNull(agendas);
        assertEquals(2, agendas.size());
        Agenda firstAgenda = agendas.get(0);
        assertEquals(Long.valueOf(1), firstAgenda.getPaciente().getId());
        assertEquals("Consulta", firstAgenda.getDescricao());
        assertEquals(LocalDateTime.parse("2022-01-01T10:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), firstAgenda.getHorario());

        Agenda secondAgenda = agendas.get(1);
        assertEquals(Long.valueOf(2), secondAgenda.getPaciente().getId());
        assertEquals("Retorno", secondAgenda.getDescricao());
        assertEquals(LocalDateTime.parse("2022-02-01T10:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), secondAgenda.getHorario());
    }
}