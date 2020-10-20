package com.neeraj.remoteProxies;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class ServicePublisher extends AbstractHandler {

    private static final Pattern PATTERN = Pattern.compile("/canGetVisa/" +
            "(?<name>[^/]+)/" +
            "(?<married>[^/]+)/" +
            "(?<rich>[^/]+)");

    private final Canada canada;

    public ServicePublisher(Canada canada) {
        this.canada = canada;
    }

    @Override
    public void handle(String target, Request jettyRequest, HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException, ServletException {
        System.out.println("Request Received........ and target is " + target);
        jettyRequest.setHandled(true);
        var matcher = PATTERN.matcher(httpServletRequest.getRequestURI());
        if (matcher.matches()) {
            var name = matcher.group("name");
            var married = Boolean.valueOf(matcher.group("married"));
            var rich = Boolean.valueOf(matcher.group("rich"));

            var canGetVisa = canada.canGetVisa(name, married, rich);
            httpServletResponse.getOutputStream().print(canGetVisa);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static void main(String[] args) throws Exception {
        Canada canada = new RealCanada();
        var server = new Server(8080);
        server.setHandler(new ServicePublisher(canada));
        server.start();
    }
}
