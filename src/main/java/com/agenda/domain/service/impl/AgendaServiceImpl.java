package com.agenda.domain.service.impl;

import com.agenda.domain.entity.Agenda;
import com.agenda.domain.entity.Paciente;
import com.agenda.domain.repository.AgendaRepository;
import com.agenda.domain.service.AgendaService;
import com.agenda.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository agendaRepository;
    private final PacienteServiceImpl pacienteService;

    public List<Agenda> listarTodos() {
        return agendaRepository.findAll();
    }

    public Optional<Agenda> buscarPorId(Long id) {
        return agendaRepository.findById(id);
    }

    public Agenda salvar(Agenda agenda) {
        Optional<Paciente> optPaciente = pacienteService.buscarPorId(agenda.getPaciente().getId());

        if(optPaciente.isEmpty()) {
            throw new BusinessException("Paciente não encontrado!");
        }

        var optHorario = agendaRepository.findByHorario(agenda.getHorario());

        if (optHorario.isPresent()) {
            throw new BusinessException("Já existe agendamento para este horário");
        }

        agenda.setPaciente(optPaciente.get());
        agenda.setDataCriacao(LocalDateTime.now());
        return agendaRepository.save(agenda);
    }
}
