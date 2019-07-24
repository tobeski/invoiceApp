package com.eltobeski.invoicingapp.util;

import com.eltobeski.invoicingapp.model.Timesheet;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TimeSheetExtractorTest {

    @Test
    public void readCsvFile() throws Exception {
        TimeSheetExtractor timeSheetExtractor = new TimeSheetExtractor();
      List<Timesheet> timesheetList = timeSheetExtractor.readCsvFile("/Users/aoluwatobi/Documents/Personal/invoicingapp/src/test/testData/testCsv.csv");
        Assert.assertNotNull(timesheetList.get(0));
    }

}