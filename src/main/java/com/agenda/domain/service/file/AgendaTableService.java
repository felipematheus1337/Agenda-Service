package com.agenda.domain.service.file;

import com.agenda.domain.entity.Agenda;
import com.itextpdf.text.BaseColor;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AgendaTableService {
    public PdfPTable createAgendaTable(List<Agenda> agendas) {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);

        PdfPCell descricaoHeader = new PdfPCell(new Phrase("Descrição"));
        descricaoHeader.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(descricaoHeader);

        PdfPCell dataHoraHeader = new PdfPCell(new Phrase("Data e Hora"));
        dataHoraHeader.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(dataHoraHeader);

        PdfPCell pacienteHeader = new PdfPCell(new Phrase("Paciente"));
        pacienteHeader.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(pacienteHeader);

        agendas.stream()
                .forEach(agenda -> {
                    table.addCell(agenda.getDescricao());
                    table.addCell(agenda.getHorario().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                    table.addCell(agenda.getPaciente().toString());
                });


        return table;

    }
}
