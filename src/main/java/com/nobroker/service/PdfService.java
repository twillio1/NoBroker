package com.nobroker.service;


import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nobroker.entity.User;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public ByteArrayOutputStream generatePdf(List<User> userList) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();

            PdfPTable table = new PdfPTable(6); // Number of columns

            // Add table headers
            table.addCell("ID");
            table.addCell("Name");
            table.addCell("Email");
            table.addCell("Password");
            table.addCell("Mobile");
            table.addCell("Email Verified");

            // Add table rows
            for (User user : userList) {
                table.addCell(String.valueOf(user.getId()));
                table.addCell(user.getName());
                table.addCell(user.getEmail());
                table.addCell(user.getPassword());
                table.addCell(user.getMobile());
                table.addCell(String.valueOf(user.isEmailVerified()));
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return baos;
    }
}
