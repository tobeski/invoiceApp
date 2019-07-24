package com.eltobeski.invoicingapp.controller;

import com.eltobeski.invoicingapp.exception.FileUploadException;
import com.eltobeski.invoicingapp.model.FileRequest;
import com.eltobeski.invoicingapp.model.StringResult;
import com.eltobeski.invoicingapp.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    InvoiceService invoiceService;

    @Value("${timesheet.data.path}")
    private String timeSheetDataFolder;

    @GetMapping(value = "/downloadFile")
    public void exportTest(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
        File f = new File(timeSheetDataFolder+File.separator+fileName);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename=\"INVOICE-1564004178965.zip\"");
        response.setStatus(HttpServletResponse.SC_OK);
        FileInputStream fis = new FileInputStream(f);
        StreamUtils.copy(fis,response.getOutputStream());
        response.flushBuffer();

    }

    @PostMapping(value = "/")
    public StringResult exportInvoices(@ModelAttribute("fileRequest") FileRequest fileRequest) throws IOException, FileUploadException, JSONException {
       String filePath = invoiceService.generateInvoices(fileRequest.getMultipartFile());
        // the response variable is just a standard HttpServletResponse
        File f = new File(filePath);
       return new StringResult(f.getName());
//        Instant t = Instant.now();
//        String fileName = "INVOICE-" + t.toEpochMilli() + ".zip";
//
//
//
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition","attachment;filename=\""+fileName+"\"");
//        response.setStatus(HttpServletResponse.SC_OK);
//        FileInputStream fis = new FileInputStream(f);
//        StreamUtils.copy(fis,response.getOutputStream());
//        response.flushBuffer();

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition","attachment;filename=\""+fileName+"\"");
//        Path path = Paths.get(f.getAbsolutePath());
//        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));


//        byte[] arBytes = new byte[32768];
//        FileInputStream is = new FileInputStream(f);
//        ServletOutputStream op = response.getOutputStream();
//        int count;
//        while ((count = is.read(arBytes)) > 0)
//        {
//            op.write(arBytes, 0, count);
//        }
//        op.flush();
    }

}
