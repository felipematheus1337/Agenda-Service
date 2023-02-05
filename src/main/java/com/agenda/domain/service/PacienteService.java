package com.agenda.domain.service;

import com.agenda.domain.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteService {
    Paciente salvar(Paciente paciente);
    void deletar(Long id);
    List<Paciente> listarTodos();

    Optional<Paciente> buscarPorId(Long id);

    Paciente alterar(Long id, Paciente paciente);

}
