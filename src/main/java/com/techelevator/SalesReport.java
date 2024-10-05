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

public class SalesReport {
        private LocalDate fileDate= LocalDate.now();
        private static int SaleCount=1;
        private File inputFile;
        public SalesReport() {
            inputFile=getReportFile(fileDate+"-Sale-"+SaleCount+"-SalesReport");
            SaleCount++;
        }
        public File getReportFile(String path) {

            File inputFile=new File(path);
            try{
                if(!inputFile.exists()){
                    inputFile.createNewFile();
                    return inputFile;
                }
                else if (!inputFile.isFile()){
                    System.out.println(path+" is not a file.");
                    return null;
                }
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
            return inputFile;
        }
        public void writeSalesReport(String message){
            try(FileWriter fileWriter=new FileWriter(this.inputFile.getName(),true)){
                PrintWriter writer=new PrintWriter(fileWriter);
                writer.append(message+"\n");
                writer.flush();
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }



