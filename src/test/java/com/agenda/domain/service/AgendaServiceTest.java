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
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

    @Captor
    ArgumentCaptor<List<Agenda>> listAgendaCaptor;


    @Test
    @DisplayName("Deve listar todos os agendamentos")
    void deveListarTodosOsAgendamentosComSucesso() {

        List<Agenda> agendaList = Arrays.asList(
                new Agenda(1L,"Agenda 1 teste",
                        LocalDateTime.now(),null,
                        new Paciente(1L,"paciente1 teste",
                                "paciente1 sobrenome teste",
                                "paciente1@mail.com",
                                "305.162.980-46")),
                        new Agenda(2L,"Agenda 2 teste",
                                LocalDateTime.now(),null,
                                new Paciente(1L,"paciente2 teste",
                                        "paciente2 sobrenome teste",
                                        "paciente2@mail.com",
                                        "146.844.230-93")
                        )
        );

        Mockito.when(repository.findAll()).thenReturn(agendaList);


        var lista = service.listarTodos();

        Assertions.assertThat(lista).isNotNull();
        Assertions.assertThat(lista.get(0).getId()).isEqualTo(1L);
        Assertions.assertThat(lista.get(1).getId()).isEqualTo(2L);


    }


    @Test
    @DisplayName("Quando buscar por ID Encontrar um agendamento")
    void buscarPorIdEntaoRetornarUmAgendamento() {
        Long searchId = 1L;
        var optAgenda = Optional.of( new Agenda(1L,"Agenda 1 teste",
                LocalDateTime.now(),null,
                new Paciente(1L,"paciente1 teste",
                        "paciente1 sobrenome teste",
                        "paciente1@mail.com",
                        "305.162.980-46")));

        Mockito.when(repository.findById(searchId)).thenReturn(optAgenda);
        var optAgendaTeste = service.buscarPorId(searchId);

        Assertions.assertThat(optAgendaTeste.isEmpty()).isEqualTo(false);
        Assertions.assertThat(optAgendaTeste.get().getClass()).isEqualTo(Agenda.class);
        Assertions.assertThat(optAgendaTeste.get().getId()).isEqualTo(searchId);








    }




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