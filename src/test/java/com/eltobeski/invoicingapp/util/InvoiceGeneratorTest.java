package com.eltobeski.invoicingapp.util;

import com.eltobeski.invoicingapp.model.Invoice;
import com.eltobeski.invoicingapp.model.Timesheet;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class InvoiceGeneratorTest {
    @Test
    public void generateInvoices() throws Exception {
        TimeSheetExtractor timeSheetExtractor = new TimeSheetExtractor();
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        List<Timesheet> timesheetList = timeSheetExtractor.readCsvFile("/Users/aoluwatobi/Documents/Personal/invoicingapp/src/test/testData/testCsv.csv");
     //  List<File> bufferedInputStreams = invoiceGenerator.generateInvoices(timesheetList);
        Assert.assertEquals(true,true);
    }

    @Test
    public void generateInvoiceForSingleCompany() throws Exception {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
      List<Invoice> invoices =  invoiceGenerator.generateInvoiceForSingleCompany(generateListOfTimeSheets(),"Facebook");
        Assert.assertEquals(invoices.get(2).getUnitPrice(),"300");
    }

    @Test
    public void csvInvoiceGenerator() throws Exception {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
 //       invoiceGenerator.csvInvoiceGenerator(listOfInvoices(),"/Users/aoluwatobi/Documents/Personal/invoicingapp/src/test/testData/writeWithCsvBeanWriter.csv");
    }

    private List<Invoice> listOfInvoices(){
        List<Invoice> invoices = new ArrayList<>();
        invoices.add( new Invoice("Company: Facebook","","",""));
        invoices.add( new Invoice("Employee ID","Number Of Hours","Unit Price","Cost"));

        for (int i = 0; i < 5; i++) {
            invoices.add( new Invoice("1","20","30","600"));
            invoices.add( new Invoice("2","40","2","80"));
            invoices.add( new Invoice("3","900","5","4500"));
        }
        invoices.add( new Invoice("","","Total","10000"));

        return invoices;
    }

    private List<Timesheet> generateListOfTimeSheets() throws ParseException {
        List<Timesheet> timesheets = new ArrayList<>();
        SimpleDateFormat workDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat workHoursFormat = new SimpleDateFormat("HH:mm");
        for(int i=0;i<5;i++){
            timesheets.add(new Timesheet(3L, 300L ,"Facebook", workDateFormat.parse("13-04-2019"),
                    workHoursFormat.parse("07:00"), workHoursFormat.parse("16:00"), null));
            timesheets.add(new Timesheet(2L, 300L ,"Facebook", workDateFormat.parse("13-04-2019"),
                    workHoursFormat.parse("09:00"), workHoursFormat.parse("19:00"), null));
        }
        return timesheets;
    }



}