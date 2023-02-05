package com.agenda.domain.service.impl;


import com.agenda.domain.entity.Paciente;
import com.agenda.domain.repository.PacienteRepository;
import com.agenda.domain.service.PacienteService;
import com.agenda.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository repository;

    public Paciente salvar(Paciente paciente) {
     boolean existeCpf = false;
     var optPaciente = repository.findByCpf(paciente.getCpf());

     if(optPaciente.isPresent()) {
         if(!optPaciente.get().getId().equals(paciente.getId())) {
             existeCpf = true;
         }
     }

     if(existeCpf) {
         throw new BusinessException("Cpf já cadastrado!");
     }

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


    public Paciente alterar(Long id, Paciente paciente) {
        var optPaciente = this.buscarPorId(id);

        if(optPaciente.isEmpty()) {
            throw new BusinessException("Paciente não cadastrado!");
        }

        paciente.setId(id);

        return salvar(paciente);
    }
}
