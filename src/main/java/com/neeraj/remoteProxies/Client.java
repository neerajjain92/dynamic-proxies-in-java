package com.neeraj.remoteProxies;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class Client {

    public static void main(String[] args) {
        Canada canada = new CanadianEmbassy();
        boolean visaObtained = canada.canGetVisa("Heinz Kabutz", true, false);
        System.out.println("visaObtained = " + visaObtained);
        System.out.println("Wins lottery ...");
        visaObtained = canada.canGetVisa(
                "Heinz Kabutz", true, true);
        System.out.println("visaObtained = " + visaObtained);
    }
}
