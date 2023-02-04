package com.agenda.domain.service.impl;

import com.agenda.domain.entity.Agenda;
import com.agenda.domain.service.AgendaService;
import com.agenda.domain.service.SaveAgendaFileService;
import com.agenda.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaveAgendaFileServiceImpl  implements SaveAgendaFileService {

    private final AgendaService agendaService;

    @Override
    public List<Agenda> saveAgendamentos(List<Agenda> agendas) throws IOException {
        List<Agenda> agendasSalvas = new ArrayList<>();
        for (Agenda agenda : agendas) {
            try {
                Agenda agendaSalva = agendaService.salvar(agenda);
                agendasSalvas.add(agendaSalva);
            } catch (BusinessException e) {
                log.error("Agendamento passado invalido na linha:  " + agendas.indexOf(agenda));
            }
        }
        return agendasSalvas;
    }
}
