package com.neeraj.dynamicProxy;

import com.neeraj.Proxies;

import java.text.ParseException;
import java.time.LocalDate;

/**
 * @author neeraj on 21/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class DynamicProxies {

    public static void main(String[] args) throws ParseException {
        ISODateParser isoDateParser = Proxies.simpleProxy(
                ISODateParser.class, new RealISODateParser()
        );

        LocalDate palindrome = isoDateParser.parse("2020-02-02");
        System.out.println("Palindrome = " + palindrome);
        System.out.println(isoDateParser);
        LocalDate funnyDate = isoDateParser .parse("2020-04-31");
    }
}
