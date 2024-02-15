package com.nobroker.controller;


import com.nobroker.entity.User;
import com.nobroker.service.PdfService;
import com.nobroker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private UserService userService;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPdf() {
        List<User> userList = userService.getAllUsers();

        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ByteArrayOutputStream pdf = pdfService.generatePdf(userList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "users_details.pdf");

        return new ResponseEntity<>(pdf.toByteArray(), headers, HttpStatus.OK);
    }
}