package com.agenda.domain.service.impl;

import com.agenda.domain.entity.Agenda;
import com.agenda.domain.entity.Paciente;
import com.agenda.domain.service.AgendaService;
import com.agenda.domain.service.GetAgendaFileService;
import com.agenda.exception.BusinessException;
import com.agenda.exception.WrongValuesOnFileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class GetAgendaFileServiceImpl implements GetAgendaFileService {

    private final AgendaService agendaService;
    private final ReadAgendaFileServiceImpl readAgendaFileService;

    private final SaveAgendaFileServiceImpl saveAgendaFileService;

    @Override
    public boolean uploadFile(MultipartFile file) throws IOException {
        try {
       List<Agenda> agendas = new ArrayList<>();
       BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
       var agendamentos = readAgendaFileService.readArquivo(reader);
       var agendamentosSalvos = saveAgendaFileService.saveAgendamentos(agendamentos);

       if(agendamentosSalvos.size() <= 0) {
           throw new WrongValuesOnFileException("Erros no CSV!");
       }
       return true;

        } catch(Exception e) {
            return false;
        }

    }






}
