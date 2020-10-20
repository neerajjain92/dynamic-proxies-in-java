package com.neeraj.designPatternCousins.chainOfResponsibility;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author neeraj on 14/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class ChainDemo {

    private Map<String, String> map1 = new HashMap<>();
    private Map<String, String> map2 = new HashMap<>();
    private Map<String, String> map3 = new HashMap<>();

    /**
     * This is the chain of responsibility.
     */
    private final Handler chain =
            new MapHandler(map1,
                    new MapHandler(map2,
                            new MapHandler(map3,
                                    null)));

    public static void main(String[] args) {
        ChainDemo chainDemo = new ChainDemo();

//        chainDemo.map2.put("test", "Hello-2");
//        chainDemo.map3.put("test", "Hello-3");
        chainDemo.handleWithoutChain("test", ChainDemo::process);
        chainDemo.map3.put("test", "Hello-3");
        chainDemo.handleWithChain("test", ChainDemo::process);
    }

    /**
     * Multiple if-else block without Chain of Responsibility.
     */
    private void handleWithoutChain(String key, Consumer<String> processor) {
        var item = map1.get(key);
        if (item != null) {
            processor.accept(item);
        } else {
            item = map2.get(key);
            if (item != null) processor.accept(item);
            else {
                item = map3.get(key);
                if (item != null) processor.accept(item);
            }
        }
    }

    private static void process(String item) {
        System.out.println("Processing : " + item);
    }

    private void handleWithChain(String key, Consumer<String> processor) {
        chain.handle(key, processor);
    }
}
