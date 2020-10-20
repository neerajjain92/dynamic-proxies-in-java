package com.neeraj.remoteProxies;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CancellationException;

/**
 * @author neeraj on 19/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 */
public class CanadianEmbassy implements Canada {

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    @Override
    public boolean canGetVisa(String name, boolean married, boolean rich) {
        try {
            var encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
            var url = "http://0.0.0.0:8080/canGetVisa/" + encodedName + "/" + married + "/" + rich;
            var req = HttpRequest.newBuilder().uri(URI.create(url)).build();

            var res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            return res.statusCode() == HttpURLConnection.HTTP_OK && Boolean.parseBoolean(res.body());
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new CancellationException("interrupted");
        }
    }
}
