package com.neeraj.designPatternCousins.chainOfResponsibility;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author neeraj on 14/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About : Our MapHandler contains the implementation that calls process(item) if the item is found in our map;
 * else it calls super.handle(key).
 */
public class MapHandler extends Handler {

    private Map<String, String> map;

    public MapHandler(Map<String, String> map, Handler next) {
        super(next);
        this.map = map;
    }

    @Override
    public void handle(String key, Consumer<String> processor) {
        String value = this.map.get(key);
        if (value != null) {
            processor.accept(value);
        } else {
            super.handle(key, processor);
        }
    }
}
