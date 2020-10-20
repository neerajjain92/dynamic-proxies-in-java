package com.neeraj.designPatternCousins.chainOfResponsibility;

import java.util.function.Consumer;

/**
 * @author neeraj on 14/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About :
 * <p>
 * Instead of the multiconditional if-else statement, we create an abstract Handler class. The default
 * implementation of handle() is to pass the call down the chain to the next handler
 */
public abstract class Handler {

    private final Handler next;

    public Handler(Handler next) {
        this.next = next;
    }

    public void handle(String key, Consumer<String> processor) {
        if (next != null) {
            next.handle(key, processor);
        }
    }
}
