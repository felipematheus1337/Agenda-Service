package com.agenda.domain.service.file;


import com.agenda.domain.entity.Agenda;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PDFGeneratorService {

    private final AgendaTableService agendaTableService;


    public byte[] generatePDF(List<Agenda> agendas) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        var document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,out);
        document.open();

        PdfPTable table = agendaTableService.createAgendaTable(agendas);
        document.add(table);

        document.close();
        writer.close();
        return out.toByteArray();
    }
}
