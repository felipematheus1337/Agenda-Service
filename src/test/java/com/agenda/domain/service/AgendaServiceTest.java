package com.agenda.domain.service;

import com.agenda.domain.entity.Agenda;
import com.agenda.domain.entity.Paciente;
import com.agenda.domain.repository.AgendaRepository;
import com.agenda.exception.BusinessException;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;


@ExtendWith(MockitoExtension.class)
class AgendaServiceTest {


    @InjectMocks
    AgendaService service;

    @Mock
    PacienteService pacienteService;

    @Mock
    AgendaRepository repository;

    @Captor
    ArgumentCaptor<Agenda> agendaCaptor;

    @Test
    @DisplayName("Deve salvar agendamento com sucesso")
    void salvarComSucesso() {

        // arrange
        LocalDateTime now = LocalDateTime.now();
        Agenda agenda = new Agenda();
        agenda.setDescricao("Descricao do agendamento -test-");
        agenda.setHorario(now);

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Paciente Teste");

        agenda.setPaciente(paciente);

        Mockito.when(pacienteService.buscarPorId(agenda.getPaciente()
                .getId())).thenReturn(Optional.of(paciente));
        Mockito.when(repository.findByHorario(now))
                .thenReturn(Optional.empty());

        // action
         service.salvar(agenda);

         // assertions

        Mockito.verify(pacienteService).buscarPorId(agenda.getPaciente().getId());
        Mockito.verify(repository).findByHorario(now);

        Mockito.verify(repository).save(agendaCaptor.capture());
        Agenda agendaSalva = agendaCaptor.getValue();

        Assertions.assertThat(agendaSalva.getPaciente()).isNotNull();
        Assertions.assertThat(agendaSalva.getDataCriacao()).isNotNull();

    }

    @Test
    @DisplayName("Não deve salvar agendamento sem paciente")
    void salvarErroPacienteNaoEncontrado() {
        LocalDateTime now = LocalDateTime.now();

        Agenda agenda = new Agenda();
        agenda.setDescricao("Descricao do agendamento -test-");
        agenda.setHorario(now);
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Paciente Teste");

        agenda.setPaciente(paciente);

        Mockito.when(pacienteService.buscarPorId(ArgumentMatchers.any())).thenReturn(Optional.empty());

        // action
        BusinessException businessException = assertThrows(BusinessException.class, () -> {
            service.salvar(agenda);
        });

        // assert

        Assertions.assertThat(businessException.getMessage()).isEqualTo("Paciente não encontrado!");

    }

    @Test
    @DisplayName("Não deve salvar agendamento com horário já existente")
    void salvarErroAgendamentoJaExistente() {
        LocalDateTime now = LocalDateTime.now();

        Agenda agenda = new Agenda();
        agenda.setDescricao("Descricao do agendamento -test-");
        agenda.setHorario(now);
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Paciente Teste");

        agenda.setPaciente(paciente);

        Mockito.when(pacienteService.buscarPorId(any())).thenReturn(Optional.of(paciente));
        Mockito.when(repository.findByHorario(agenda.getHorario())).thenReturn(Optional.of(agenda));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.salvar(agenda);
        });

        // assert

        Assertions.assertThat(exception.getMessage()).isEqualTo("Já existe agendamento para este horário");
        




    }
}