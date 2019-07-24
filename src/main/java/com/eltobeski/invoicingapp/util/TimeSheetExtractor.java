package com.eltobeski.invoicingapp.util;

import com.eltobeski.invoicingapp.model.Timesheet;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimeSheetExtractor {

    public List<Timesheet> readCsvFile(String csvPath) throws IOException {
        ICsvBeanReader beanReader = null;
        List<Timesheet> timesheetList = new ArrayList<>();
        try {
            beanReader = new CsvBeanReader(new FileReader(csvPath), CsvPreference.STANDARD_PREFERENCE);

            // the header elements are used to map the values to the bean (names must match)
            beanReader.getHeader(false);
            final String[] header = new String[]{"employeeId", "billableRate", "project", "workDate", "startTime", "endTime"};
            final CellProcessor[] processors = getProcessors();

            Timesheet timesheet;
            while ((timesheet = beanReader.read(Timesheet.class, header, processors)) != null) {
                timesheetList.add(timesheet);
            }

        } finally {
            if (beanReader != null) {
                beanReader.close();
            }
        }
        return timesheetList;
    }


    private static CellProcessor[] getProcessors() {


        final CellProcessor[] processors = new CellProcessor[]{
                new ParseLong(), // EmployeeId
                new ParseLong(), // Billable Rate(per hour)
                new NotNull(), // Project
                new ParseDate("yyyy-MM-dd"), // Date
                new ParseDate("HH:mm"), // Start Time
                new ParseDate("HH:mm"), // End Time

        };

        return processors;
    }
}
