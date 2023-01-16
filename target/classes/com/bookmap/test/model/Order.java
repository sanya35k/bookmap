package com.bookmap.test.model;

import java.util.Objects;

public class Order {
    private final int price;
    private int size;
    private final Type type;

    public enum Type {
        BID,
        ASK,
        SPREAD
    }

    public Order(int price, int size, Type type) {
        this.price = price;
        this.size = size;
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return price == order.price
                && size == order.size
                && type == order.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, size, type);
    }
}
