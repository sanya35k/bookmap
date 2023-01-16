package com.bookmap.test.service;

import com.bookmap.test.model.Order;

public interface OrderManagerService {
    void updateBid(int price, int size);
    void updateAsk(int price, int size);

    Order getBestBid();
    Order getBestAsk();
    Order getOperationWithPrice(int price);

    void buyOrder(int size);
    void sellOrder(int size);
}
