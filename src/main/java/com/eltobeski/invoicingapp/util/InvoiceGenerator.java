package com.eltobeski.invoicingapp.util;

import com.eltobeski.invoicingapp.model.Invoice;
import com.eltobeski.invoicingapp.model.Timesheet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Calendar.*;

public class InvoiceGenerator {


    private void csvInvoiceGenerator(List<Invoice> invoices, String path) throws IOException {
        ICsvBeanWriter beanWriter = null;
        try {
            beanWriter = new CsvBeanWriter(new FileWriter(path),
                    CsvPreference.STANDARD_PREFERENCE);

            // the header elements are used to map the bean values to each column (names must match)
            final String[] header = new String[]{"employeeID", "numberOfHours", "unitPrice", "cost"};
            final CellProcessor[] processors = getProcessors();

            // write the header
            // beanWriter.writeHeader(header);

            // write the beans
            for (final Invoice invoice : invoices) {
                beanWriter.write(invoice, header, processors);
            }

        } finally {
            if (beanWriter != null) {
                beanWriter.close();
            }
        }
    }

    public List<File> generateInvoices(List<Timesheet> timesheets,String dataFolderPath) {
        Map<String, List<Timesheet>> groupedTimeSheets = timesheets.stream().
                collect(Collectors.groupingBy(timeSheet -> timeSheet.getProject().toUpperCase()));
        List<File> files = new ArrayList<>();
        try {
            for (Map.Entry<String, List<Timesheet>> entry : groupedTimeSheets.entrySet()) {
                Instant t = Instant.now();
                String fileName = entry.getKey() + "-INVOICE-" + t.toEpochMilli() + ".csv";
                String filePath = dataFolderPath + File.separator + fileName;
                File f = new File(filePath);
                f.createNewFile();
                csvInvoiceGenerator(generateInvoiceForSingleCompany(entry.getValue(), entry.getKey()), f.getPath());


                files.add(f);


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public List<Invoice> generateInvoiceForSingleCompany(List<Timesheet> timesheets, String company) {

        List<Invoice> invoices = new ArrayList<>();
        final Long[] total = {0L};
        invoices.add(generateCompanyHeaderInvoice(company));
        invoices.add(generateHeaderInvoice());
        enrichTimeSheet(timesheets);


        Collection<Invoice> invoiceCollection = timesheets.stream().collect(Collectors.groupingBy(Timesheet::getEmployeeId))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> {

                    Long totalWorkHours = x.getValue().stream().mapToLong(Timesheet::getWorkDuration).sum();
                    Long billableCost = x.getValue().stream().collect(Collectors.averagingLong(Timesheet::getBillableRate)).longValue();
                    Long cost = totalWorkHours * billableCost;
                    total[0] += cost;

                    return new Invoice(x.getKey().toString(),
                            totalWorkHours.toString(),
                            billableCost.toString(),
                            cost.toString());

                })).values();
        invoices.addAll(invoiceCollection);
        invoices.add(generateFooterInvoice(new ArrayList<>(invoiceCollection)));
        return invoices;
    }

    private List<Timesheet> enrichTimeSheet(List<Timesheet> timesheets) {

        timesheets.forEach(timesheet -> calculateDuration(timesheet));
        return timesheets;


    }

    private Timesheet calculateDuration(Timesheet timesheet) {
        Long diffInMillies = Math.abs(timesheet.getEndTime().getTime() - timesheet.getStartTime().getTime());
        Long numberOfHours = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        timesheet.setWorkDuration(numberOfHours);
        return timesheet;

    }

    private Invoice generateCompanyHeaderInvoice(String company) {
        String header = "Company: " + company;
        Invoice invoice = new Invoice();
        invoice.setEmployeeID(header);
        invoice.setCost("");
        invoice.setNumberOfHours("");
        invoice.setUnitPrice("");
        return invoice;
    }

    private Invoice generateHeaderInvoice() {
        Invoice invoice = new Invoice();
        invoice.setEmployeeID("Employee ID");
        invoice.setCost("Cost");
        invoice.setNumberOfHours("Number Of Hours");
        invoice.setUnitPrice("Unit Price");
        return invoice;
    }

    private Invoice generateFooterInvoice(List<Invoice> invoices) {
        Long total = invoices.stream().mapToLong(invoice -> Long.valueOf(invoice.getCost())).sum();
        Invoice invoice = new Invoice();
        invoice.setEmployeeID("");
        invoice.setCost(total.toString());
        invoice.setNumberOfHours("");
        invoice.setUnitPrice("Total");
        return invoice;
    }

    private static CellProcessor[] getProcessors() {


        final CellProcessor[] processors = new CellProcessor[]{
                new Optional(), // EmployeeId
                new Optional(), // Billable Rate(per hour)
                new Optional(), // Project
                new Optional() // Date

        };

        return processors;
    }


}
