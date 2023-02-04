package com.agenda.domain.service;

import com.agenda.domain.entity.Agenda;

import java.io.IOException;
import java.util.List;

public interface SaveAgendaFileService {

    List<Agenda> saveAgendamentos(List<Agenda> agendas) throws IOException;

}
