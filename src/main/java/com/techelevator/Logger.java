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

        try(FileWriter fileWriter=new FileWriter(this.inputFile.getName(),true)){
            PrintWriter writer=new PrintWriter(fileWriter);
            writer.append(messageDate+" "+timeString+" "+message+"\n");
            writer.flush();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
