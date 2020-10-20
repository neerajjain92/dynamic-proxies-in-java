package com.neeraj.dynamicProxy;

import java.text.ParseException;
import java.time.LocalDate;

/**
 * @author neeraj on 20/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 *
 * About :
 * Parses String of the format yyyy-MM-dd and returns LocalDate.
 */
public interface ISODateParser {

    LocalDate parse(String date) throws ParseException;
}
