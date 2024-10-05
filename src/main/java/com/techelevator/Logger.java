package com.techelevator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Logger extends FileGenerator {
    public Logger() {
        inputFile = getLogFile(fileDate+"-log.log");
    }
    private File getLogFile(String path) {

        return super.getInputFile(path);
    }
    public void writeLogEntry(String message){

        Date date = new Date();
        SimpleDateFormat sFormatter = new SimpleDateFormat("MM/dd/yyyy");
        String messageDate= sFormatter.format(date);
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String timeString = localTime.format(dateTimeFormatter);

        message = messageDate+" "+timeString+" "+message+"\n";

        super.writeMessageToFile(message);
        }
    }
}
