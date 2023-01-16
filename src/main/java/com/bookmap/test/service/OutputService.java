package com.bookmap.test.service;

import com.bookmap.test.model.Order;

public interface OutputService {
    void saveOperationToFile(Order order);
    void saveValueToFile(int value);
}
