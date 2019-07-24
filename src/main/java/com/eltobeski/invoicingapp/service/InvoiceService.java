package com.eltobeski.invoicingapp.service;

import com.eltobeski.invoicingapp.exception.FileUploadException;
import com.eltobeski.invoicingapp.model.Timesheet;
import com.eltobeski.invoicingapp.util.FileUploadingUtil;
import com.eltobeski.invoicingapp.util.InvoiceGenerator;
import com.eltobeski.invoicingapp.util.TimeSheetExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.Instant;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
public class InvoiceService {

    @Value("${timesheet.data.path}")
    private String timeSheetDataFolder;

    @Value("${invoice.data.path}")
    private String dataFolderPath;


    public String generateInvoices(MultipartFile multipartFile) throws FileUploadException, IOException {
        File timeSheetDirectory = new File(timeSheetDataFolder);
        File invoiceDirectory = new File(dataFolderPath);
        if (!timeSheetDirectory.exists()) {
            timeSheetDirectory.mkdir();
        } if (!invoiceDirectory.exists()) {
            invoiceDirectory.mkdir();
        }
        Instant t = Instant.now();
        String fileName = "INVOICE-" + t.toEpochMilli() + ".zip";
        String zipFile = timeSheetDataFolder + File.separator + fileName;
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        TimeSheetExtractor timeSheetExtractor = new TimeSheetExtractor();

        FileOutputStream fos = new FileOutputStream(zipFile);

        ZipOutputStream zos = new ZipOutputStream(fos);

        String path = FileUploadingUtil.uploadMultipartFiles(timeSheetDataFolder, multipartFile);
        List<Timesheet> timesheets = timeSheetExtractor.readCsvFile(path);
        List<File> files = invoiceGenerator.generateInvoices(timesheets,dataFolderPath);
        byte[] buffer = new byte[1024];
        try {
            for (File file : files
                    ) {
                FileInputStream fis = new FileInputStream(file);

                // begin writing a new ZIP entry, positions the stream to the start of the entry data
                zos.putNextEntry(new ZipEntry(file.getName()));

                int length;

                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
//                StreamUtils.copy(fis,zos);

                zos.closeEntry();

                // close the InputStream
                fis.close();

            }

            // close the ZipOutputStream
            zos.finish();


        } catch (IOException ioe)

        {
            System.out.println("Error creating zip file: " + ioe);
        }
        return zipFile;

    }
}





