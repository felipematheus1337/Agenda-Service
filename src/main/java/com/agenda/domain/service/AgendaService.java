package com.agenda.domain.service;

import com.agenda.domain.entity.Agenda;

import java.util.List;
import java.util.Optional;

public interface AgendaService {

    List<Agenda> listarTodos();
    Optional<Agenda> buscarPorId(Long id);
    Agenda salvar(Agenda agenda);
}
