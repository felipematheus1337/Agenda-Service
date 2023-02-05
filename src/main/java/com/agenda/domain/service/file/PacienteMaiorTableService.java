package com.agenda.domain.service.file;


import com.agenda.api.response.PacientePDFResponse;
import com.agenda.domain.entity.Paciente;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import org.springframework.stereotype.Service;


import java.awt.*;
import java.util.List;
import java.util.Map;

@Service
public class PacienteMaiorTableService {
    public PdfPTable createPacienteTable(List<PacientePDFResponse> pacientes) {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        PdfPCell vezes = new PdfPCell(new Phrase("Agendamentos"));
        vezes.setBackgroundColor(Color.ORANGE);
        table.addCell(vezes);

        PdfPCell nome = new PdfPCell(new Phrase("Nome"));
        nome.setBackgroundColor(Color.ORANGE);
        table.addCell(nome);

        PdfPCell sobrenome = new PdfPCell(new Phrase("Sobrenome"));
        sobrenome.setBackgroundColor(Color.ORANGE);
        table.addCell(sobrenome);

        PdfPCell email = new PdfPCell(new Phrase("E-mail"));
        email.setBackgroundColor(Color.ORANGE);
        table.addCell(email);

        pacientes.stream()
                .forEach(paciente -> {
            table.addCell(paciente.getCount().toString());
            table.addCell(paciente.getNome());
            table.addCell(paciente.getSobrenome());
            table.addCell(paciente.getEmail());
        });

        return table;
    }
}
