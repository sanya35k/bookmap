package com.bookmap.test.service.impl;

import com.bookmap.test.model.Order;
import com.bookmap.test.service.OutputService;

import java.io.BufferedWriter;
import java.io.IOException;

public class OutputServiceImpl implements OutputService {
    private final BufferedWriter bufferedWriter;

    public OutputServiceImpl(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    @Override
    public void saveOperationToFile(Order order) {
        try {
            bufferedWriter.write(order.getPrice() + "," + order.getSize() + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Can't save operation to file", e);
        }
    }

    @Override
    public void saveValueToFile(int value) {
        try {
            bufferedWriter.write(value + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Can't save value to file", e);
        }
    }
}
