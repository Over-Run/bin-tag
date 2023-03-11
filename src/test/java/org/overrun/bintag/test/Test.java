/*
 * MIT License
 *
 * Copyright (c) 2023 Overrun Organization
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

package org.overrun.bintag.test;

import org.overrun.bintag.BinaryData;
import org.overrun.bintag.BinaryNode;
import org.overrun.bintag.BinaryTag;

import java.io.*;
import java.util.Map;

/**
 * A simple test
 *
 * @author squid233
 * @since 0.1.0
 */
public final class Test {
    private static void write(BinaryTag tag, String name) {
        try (var os = new DataOutputStream(new FileOutputStream(name))) {
            tag.write(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BinaryTag read(String name) {
        try (var is = new DataInputStream(new FileInputStream(name))) {
            return BinaryNode.read(is).asTag();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        final BinaryTag tag = BinaryTag.of(
            Map.entry("name", BinaryData.of("bin-tag")),
            Map.entry("version", BinaryData.of("1.0.0")),
            Map.entry("number", BinaryData.of(42)),
            Map.entry("subtag", BinaryTag.of(
                Map.entry("name", BinaryData.of("bin-tag")),
                Map.entry("version", BinaryData.of("2.0.0")),
                Map.entry("number", BinaryData.of(43)),
                Map.entry("subtag-array", BinaryData.ofArray(
                    BinaryTag.of(
                        Map.entry("name", BinaryData.of("bin-tag")),
                        Map.entry("version", BinaryData.of("3.0.0")),
                        Map.entry("number", BinaryData.of(44)),
                        Map.entry("subtag", BinaryTag.of(
                        ))
                    ),
                    BinaryTag.of(
                        Map.entry("name", BinaryData.of("bin-tag")),
                        Map.entry("version", BinaryData.of("4.0.0")),
                        Map.entry("number", BinaryData.of(45)),
                        Map.entry("position", BinaryData.ofArray(1f, 0f, 0f, 1f))
                    )
                ))
            ))
        );
        System.out.println(tag);
        write(tag, "helloWorld.bin");
        System.out.println(read("helloWorld.bin"));
    }
}
