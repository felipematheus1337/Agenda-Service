package com.agenda.domain.service.file;


import com.agenda.domain.entity.Agenda;
import com.agenda.domain.entity.Paciente;
import com.agenda.domain.service.ReadAgendaFileService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadAgendaFileServiceImpl implements ReadAgendaFileService {

    @Override
    public List<Agenda> readArquivo(BufferedReader reader) throws IOException {
        String line;
        List<Agenda> agendas = new ArrayList<>();
        while((line = reader.readLine()) != null) {
            String[] attributes = line.split(",");
            Paciente paciente = new Paciente();
            Agenda agenda = new Agenda();
            paciente.setId(Long.parseLong(attributes[0]));
            agenda.setDescricao(attributes[1]);
            agenda.setPaciente(paciente);
            agenda.setHorario(LocalDateTime.parse(attributes[2], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
            agendas.add(agenda);
        }
        return agendas;
    }
}
