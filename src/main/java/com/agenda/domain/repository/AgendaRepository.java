package com.agenda.domain.repository;

import com.agenda.api.response.PacientePDFResponse;
import com.agenda.domain.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    Optional<Agenda> findByHorario(LocalDateTime horario);

    List<Agenda> findByHorarioBetween(LocalDateTime inicioSemana, LocalDateTime fimDeSemana);

    @Query("SELECT new com.agenda.api.response.PacientePDFResponse(a.paciente.nome, a.paciente.sobrenome, a.paciente.email, count(a.id)) FROM Agenda a WHERE MONTH(a.dataCriacao) = :mes GROUP BY a.paciente.nome,a.paciente.sobrenome,a.paciente.email ORDER BY count(a.id) DESC")
    List<PacientePDFResponse> findTopPacientesAgendamentosByMonth(@Param("mes") int mes);

}
