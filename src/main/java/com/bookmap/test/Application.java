package com.bookmap.test;

import com.bookmap.test.service.StrategyManager;
import com.bookmap.test.service.impl.OrderManagerServiceServiceImpl;
import com.bookmap.test.service.impl.OutputServiceImpl;
import com.bookmap.test.service.impl.StrategyManagerServiceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    private static final String INPUT_FILE_PATH = "input.txt";
    private static final String OUTPUT_FILE_PATH = "output.txt";

    public static void main(String[] args) {
        writeOutputFile();
        readInputFile();
    }

    private static void readInputFile() {
        String line;
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(INPUT_FILE_PATH));
             BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(OUTPUT_FILE_PATH))) {
            StrategyManager strategyManager = new StrategyManagerServiceImpl(
                    new OrderManagerServiceServiceImpl(),
                    new OutputServiceImpl(bufferedWriter));
            while ((line = bufferedReader.readLine()) != null) {
                strategyManager.lineStrategy(line);
            }
        } catch (IOException exception) {
            throw new RuntimeException("The file hasn't been read!", exception);
        }
    }

    private static void writeOutputFile() {
        File outputFile = new File(Application.OUTPUT_FILE_PATH);
        try {
            if (!outputFile.exists()) {
                outputFile.createNewFile();
                System.out.println("The file has been created!");
            } else {
                System.out.println("The file already exists!");
            }
        } catch (IOException exception) {
            throw new RuntimeException("The file hasn't been created!", exception);
        }
    }
}
