package com.bookmap.test.service.impl;

import com.bookmap.test.service.OrderManagerService;
import com.bookmap.test.service.OutputService;
import com.bookmap.test.service.StrategyManager;

public class StrategyManagerServiceImpl implements StrategyManager {
    private final OrderManagerService orderManagerService;
    private final OutputService outputService;

    public StrategyManagerServiceImpl(OrderManagerService orderManagerService, OutputService outputService) {
        this.orderManagerService = orderManagerService;
        this.outputService = outputService;
    }

    @Override
    public void lineStrategy(String line) {
        char ch = line.charAt(0);
        switch (ch) {
            case 'u' -> updateStrategy(line);
            case 'q' -> queriesStrategy(line);
            case 'o' -> orderStrategy(line);
            default -> throw new RuntimeException("Wrong line format: " + line);
        }
    }

    private void orderStrategy(String line) {
        if (line.charAt(2) == 'b') {
            orderManagerService.buyOrder(Integer.parseInt(line.substring(6)));
        } else if (line.charAt(2) == 's') {
            orderManagerService.sellOrder(Integer.parseInt(line.substring(7)));
        }
    }

    private void queriesStrategy(String line) {
        if (line.charAt(7) == 'b') {
            outputService.saveOperationToFile(orderManagerService.getBestBid());
        } else if (line.charAt(7) == 'a') {
            outputService.saveOperationToFile(orderManagerService.getBestAsk());
        } else if (line.charAt(2) == 's') {
            outputService.saveValueToFile(orderManagerService
                    .getOperationWithPrice(Integer.parseInt(line.substring(7))).getSize());
        } else {
            throw new RuntimeException("Wrong line format: " + line);
        }
    }

    private void updateStrategy(String line) {
        int lastIndex = line.lastIndexOf(",");
        String substring = line.substring(line.indexOf(",") + 1, lastIndex);
        int index = substring.indexOf(",");
        int firstInt = Integer.parseInt(substring, 0, index, 10);
        int secondInt = Integer.parseInt(substring, index + 1, substring.length(), 10);
        switch (line.substring(lastIndex + 1).charAt(0)) {
            case 'b' -> orderManagerService.updateBid(firstInt, secondInt);
            case 'a' -> orderManagerService.updateAsk(firstInt, secondInt);
            default -> throw new RuntimeException("Wrong line format: " + line);
        }
    }
}
