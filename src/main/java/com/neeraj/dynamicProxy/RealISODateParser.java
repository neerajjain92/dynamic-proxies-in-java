package com.neeraj.dynamicProxy;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author neeraj on 21/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class RealISODateParser implements ISODateParser {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public LocalDate parse(String date) throws ParseException {
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException ex) {
            throw new ParseException(ex.toString(), ex.getErrorIndex());
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
