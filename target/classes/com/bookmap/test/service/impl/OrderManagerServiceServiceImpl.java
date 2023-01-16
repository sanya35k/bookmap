package com.bookmap.test.service.impl;

import com.bookmap.test.model.Order;
import com.bookmap.test.service.OrderManagerService;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class OrderManagerServiceServiceImpl implements OrderManagerService {
    private final TreeMap<Integer, Order> bidTree;
    private final TreeMap<Integer, Order> askTree;

    public OrderManagerServiceServiceImpl() {
        bidTree = new TreeMap<>();
        askTree = new TreeMap<>();
    }

    @Override
    public void updateBid(int price, int size) {
        bidTree.put(price, new Order(price, size, Order.Type.BID));
        askTree.remove(price);
    }

    @Override
    public void updateAsk(int price, int size) {
        askTree.put(price, new Order(price, size, Order.Type.ASK));
        bidTree.remove(price);
    }

    @Override
    public Order getBestBid() {
        Map.Entry<Integer, Order> entry = bidTree.lastEntry();
        Order biggestValue = entry.getValue();
        while (biggestValue.getSize() == 0
                && (entry = bidTree.lowerEntry(entry.getKey())) != null) {
            biggestValue = entry.getValue();
        }
        if (entry == null) {
            return bidTree.lastEntry().getValue();
        }
        return biggestValue;
    }

    @Override
    public Order getBestAsk() {
        Map.Entry<Integer, Order> entry = askTree.firstEntry();
        Order biggestValue = entry.getValue();
        while (biggestValue.getSize() == 0
                && (entry = askTree.higherEntry(entry.getKey())) != null) {
            biggestValue = entry.getValue();
        }
        if (entry == null) {
            return askTree.lastEntry().getValue();
        }
        return biggestValue;
    }

    @Override
    public Order getOperationWithPrice(int price) {
        Order order1 = askTree.get(price);
        Order order2 = bidTree.get(price);
        if (order1 != null) { return order1; }
        else return Objects.requireNonNullElseGet(order2, () -> new Order(price, 0, Order.Type.SPREAD));
    }

    @Override
    public void buyOrder(int size) {
        Order bestAsk = getBestAsk();
        while (size != 0) {
            int newSize = bestAsk.getSize() - size;
            if (newSize <= 0) {
                size = Math.abs(newSize);
                askTree.remove(bestAsk.getPrice());
                bestAsk = getBestAsk();
            } else {
                size = 0;
                bestAsk.setSize(newSize);
            }
        }
    }

    @Override
    public void sellOrder(int size) {
        Order bestBid = getBestBid();
        while (size != 0) {
            int newSize = bestBid.getSize() - size;
            if (newSize <= 0) {
                size = Math.abs(newSize);
                bidTree.remove(bestBid.getPrice());
                bestBid = getBestBid();
            } else {
                size = 0;
                bestBid.setSize(newSize);
            }
        }
    }
}
