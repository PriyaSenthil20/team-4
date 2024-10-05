package com.techelevator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class FileGenerator {
    public LocalDate fileDate = LocalDate.now();
    public File inputFile;

    public File getInputFile(String path) {
        File inputFile = new File(path);
        try {
            if (!inputFile.exists()) {
                inputFile.createNewFile();
                return inputFile;
            } else if (!inputFile.isFile()) {
                System.out.println(path + " is not a file.");
                return null;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return inputFile;
    }

    public void writeMessageToFile(String message) {
        try(FileWriter fileWriter=new FileWriter(this.inputFile.getName(),true)){
            PrintWriter writer=new PrintWriter(fileWriter);
            writer.append(message);
            writer.flush();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
