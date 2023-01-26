package com.agenda.domain.entity.service;


import com.agenda.domain.entity.Paciente;
import com.agenda.domain.entity.repository.PacienteRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class PacienteService {

    private final PacienteRepository repository;

    public Paciente salvar(Paciente paciente) {
     return repository.save(paciente);
    }

    public void deletar(Long id) {
     repository.deleteById(id);
    }

    public List<Paciente> listarTodos() {
      return repository.findAll();
    }

    public Optional<Paciente> buscarPorId(Long id) {
      return repository.findById(id);
    }



}
