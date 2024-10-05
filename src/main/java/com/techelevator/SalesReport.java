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
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh-mm-ssa");
            String timeString = localTime.format(dateTimeFormatter);

            inputFile=getReportFile(fileDate+"_"+timeString+"_SalesReport");
        }
        private File getReportFile(String path) {
            return super.getInputFile(path);
        }
        public void writeSalesReport(List<InventoryItem> inventoryItemList, BigDecimal totalSales){
            try(FileWriter fileWriter=new FileWriter(this.inputFile.getName())){
                PrintWriter writer=new PrintWriter(fileWriter);
                for(InventoryItem item:inventoryItemList){
                    writer.write(item.getName() + "|" + item.getNumbersSold() + "\n");
                }
                writer.write("\n**TOTAL SALES** $" + totalSales);
                writer.flush();
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }



