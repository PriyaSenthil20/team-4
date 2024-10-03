package com.techelevator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private LocalDate fileDate= LocalDate.now();
    private File inputFile;
    public Logger() throws IOException {
        inputFile=getLogFile(fileDate+"-log.log");
    }
    public File getLogFile(String path) throws IOException {
        File inputFile=new File(path);
        if(!inputFile.exists()){
            inputFile.createNewFile();
            return inputFile;
        }
        else if (!inputFile.isFile()){
            System.out.println(path+" is not a file.");
            return null;
        }
        return inputFile;
    }
    public void writeLogEntry(String message)throws IOException{
        LocalDate messageDate=LocalDate.now();
        LocalTime currentTime=LocalTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH.mm.ss");
        String timeString=currentTime.format(formatter);
        try(FileWriter fileWriter=new FileWriter(this.inputFile.getName(),true)){
            PrintWriter writer=new PrintWriter(fileWriter);
            writer.append(messageDate+" - "+message+" - "+timeString+"\n");
            writer.flush();
        }
        catch (Exception e){
            throw new IOException();
        }
    }
}
