package com.neeraj.designPatternCousins;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author neeraj on 14/10/20
 * Copyright (c) 2019, dynamic-proxies-in-java.
 * All rights reserved.
 * <p>
 * About Section :
 * <p>
 * We create a FileOutputStream to the the file data.bin.gz. We decorate this with
 * GZIPOutputStream, BufferedOutputStream, and DataOutputStream. Each link in the chain adds functionality.
 * We then write 10 million random integers between zero and 1,000.
 */
public class DecoratorPatternSample {

    public static void main(String[] args) throws IOException {

        /**
         * Writing to a Data Output Stream
         *
         * Listing 1.1 )
         * We create a FileOutputStream to the the file data.bin.gz. We decorate this with
         * GZIPOutputStream, BufferedOutputStream, and DataOutputStream. Each link in the chain adds functionality.
         * We then write 10 million random integers between zero and 1,000.
         */

        try (var out = new DataOutputStream(
                new BufferedOutputStream(
                        new GZIPOutputStream(
                                new FileOutputStream("data.bin.gz"))))) {
            // Write ten million random ints between 0 and 1000 to
            // data.bin.gz, compressing the file with GZIP on the fly
            ThreadLocalRandom.current().ints(10_000_000, 0, 1_000)
                    .forEach(i -> {
                        try {
                            out.writeInt(i);
                        } catch (IOException ex) {
                            throw new UncheckedIOException(ex);
                        }
                    });
            out.writeInt(-1); // our EOF marker
        }

        /**
         * Listing 1.2 Reading from DataInputStream
         *
         * We read from a DataInputStream which in turn reads from
         * {@link BufferedInputStream}, {@link GZIPInputStream} and {@link FileInputStream}
         */

        try (
                // declared "fis" separately to ensure it is closed,
                // even if the file does not contain a proper GZIP header.
                var fis = new FileInputStream("data.bin.gz");
                var in = new DataInputStream(
                        new BufferedInputStream(
                                new GZIPInputStream(fis)))) {

            long total = 0;
            int value = 0;
            // Keep reading until value == -1, our EOF marker
            while ((value = in.readInt()) != -1) {
                total += value;
            }

            // We expect a total to be approximately 5 billion
            System.out.println("total = " + total);
        }
    }
}
