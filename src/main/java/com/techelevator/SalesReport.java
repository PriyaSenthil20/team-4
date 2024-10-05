package com.techelevator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class SalesReport extends FileGenerator {
        public SalesReport() {
            LocalTime localTime = LocalTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh-mm a");
            String timeString = localTime.format(dateTimeFormatter);
            this.inputFile=getReportFile(fileDate+"_"+timeString+"_SalesReport");
        }
        private File getReportFile(String path) {
            return super.getInputFile(path);
        }
        public void writeSalesReport(String message){
            super.writeMessageToFile(message);
        }
    }



