package com.neeraj.dynamicProxy.logging;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author neeraj on 20/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About :
 * <p>
 * Once we have our dynamic proxy object cast to Map, all the methods we call on it are logged,
 * together with their execution time.
 */
public class LoggingProxyDemo {

    public static void main(String[] args) {

        // We can enable "fine" logging for the global logger by
        // setting the level in the root ConsoleHandler to also
        // be "fine".
        Logger log = Logger.getGlobal();

        for (Handler handler : log.getParent().getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                handler.setLevel(Level.FINE);
            }
        }

        log.setLevel(Level.FINE);

        var handler = new LoggingInvocationHandler(
                Logger.getGlobal(), new ConcurrentHashMap<>());

        var map = (Map<String, Integer>) Proxy.newProxyInstance(
                Map.class.getClassLoader(),
                new Class<?>[]{Map.class},
                handler
        );

        map.put("Hello", 1);
        map.put("World", 1);
        System.out.println(map);
        map.clear();
    }
}
