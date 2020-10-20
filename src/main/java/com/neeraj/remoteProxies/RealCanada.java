package com.neeraj.remoteProxies;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class RealCanada implements Canada {
    @Override
    public boolean canGetVisa(String name, boolean married, boolean rich) {
        return married && rich || !married && rich || rich;
        // every country loves rich tourist..... :-)
    }
}
