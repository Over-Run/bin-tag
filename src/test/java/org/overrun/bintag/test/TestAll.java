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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.overrun.bintag.BinaryData;
import org.overrun.bintag.BinaryTag;

import java.io.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A simple test
 *
 * @author squid233
 * @since 0.1.0
 */
final class TestAll {
    private final BinaryTag tag = BinaryTag.of(
        Map.entry("byte", BinaryData.of((byte) 42)),
        Map.entry("short", BinaryData.of((short) 43)),
        Map.entry("int", BinaryData.of(44)),
        Map.entry("long", BinaryData.of(45L)),
        Map.entry("float", BinaryData.of(46F)),
        Map.entry("double", BinaryData.of(47.0)),
        Map.entry("string", BinaryData.of("48")),
        Map.entry("tag", BinaryTag.of(
            Map.entry("byteArray", BinaryData.ofArray((byte) 49, (byte) 50)),
            Map.entry("shortArray", BinaryData.ofArray((short) 51, (short) 52)),
            Map.entry("intArray", BinaryData.ofArray(53, 54)),
            Map.entry("longArray", BinaryData.ofArray(55L, 56L)),
            Map.entry("floatArray", BinaryData.ofArray(57F, 58F)),
            Map.entry("doubleArray", BinaryData.ofArray(59.0, 60.0)),
            Map.entry("stringArray", BinaryData.ofArray("61", "62")),
            Map.entry("tagArray", BinaryData.ofArray(
                BinaryTag.of(Map.of("tag1", BinaryData.of("63"))),
                BinaryTag.of(Map.of("tag2", BinaryData.of("64")))
            ))
        ))
    );

    @Test
    void testOf() {
        assertDoesNotThrow(() -> BinaryData.of((byte) 42).asByte());
        assertDoesNotThrow(() -> BinaryData.ofArray("aa", "bb").asStringArray());
        assertThrows(ClassCastException.class, () -> BinaryData.of(42).asTag());
        assertThrows(ClassCastException.class, () -> BinaryData.of(42).asIntArray());
    }

    private void testTag(BinaryTag tag) {
        tag.map().forEach((name, node) -> {
            switch (node) {
                case BinaryData.OfByte of -> {
                    assertEquals("byte", name);
                    assertEquals((byte) 42, of.asByte());
                }
                case BinaryData.OfShort of -> {
                    assertEquals("short", name);
                    assertEquals((short) 43, of.asShort());
                }
                case BinaryData.OfInt of -> {
                    assertEquals("int", name);
                    assertEquals(44, of.asInt());
                }
                case BinaryData.OfLong of -> {
                    assertEquals("long", name);
                    assertEquals(45L, of.asLong());
                }
                case BinaryData.OfFloat of -> {
                    assertEquals("float", name);
                    assertEquals(46F, of.asFloat());
                }
                case BinaryData.OfDouble of -> {
                    assertEquals("double", name);
                    assertEquals(47.0, of.asDouble());
                }
                case BinaryData.OfString of -> {
                    assertEquals("string", name);
                    assertEquals("48", of.asString());
                }
                case BinaryTag of -> {
                    assertEquals("tag", name);
                    of.map().forEach((name1, node1) -> {
                        switch (node1) {
                            case BinaryData.OfByteArray of1 -> {
                                assertEquals("byteArray", name1);
                                assertArrayEquals(new byte[]{49, 50}, of1.asByteArray());
                            }
                            case BinaryData.OfShortArray of1 -> {
                                assertEquals("shortArray", name1);
                                assertArrayEquals(new short[]{51, 52}, of1.asShortArray());
                            }
                            case BinaryData.OfIntArray of1 -> {
                                assertEquals("intArray", name1);
                                assertArrayEquals(new int[]{53, 54}, of1.asIntArray());
                            }
                            case BinaryData.OfLongArray of1 -> {
                                assertEquals("longArray", name1);
                                assertArrayEquals(new long[]{55, 56}, of1.asLongArray());
                            }
                            case BinaryData.OfFloatArray of1 -> {
                                assertEquals("floatArray", name1);
                                assertArrayEquals(new float[]{57, 58}, of1.asFloatArray());
                            }
                            case BinaryData.OfDoubleArray of1 -> {
                                assertEquals("doubleArray", name1);
                                assertArrayEquals(new double[]{59, 60}, of1.asDoubleArray());
                            }
                            case BinaryData.OfStringArray of1 -> {
                                assertEquals("stringArray", name1);
                                assertArrayEquals(new String[]{"61", "62"}, of1.asStringArray());
                            }
                            case BinaryData.OfTagArray of1 -> {
                                assertEquals("tagArray", name1);
                                final BinaryTag[] array = of1.asTagArray();
                                final BinaryTag tag1 = array[0];
                                final BinaryTag tag2 = array[1];
                                assertEquals("63", assertDoesNotThrow(() -> tag1.getString("tag1")));
                                assertEquals("64", assertDoesNotThrow(() -> tag2.getString("tag2")));
                            }
                            default -> throw new MatchException("Unexpected value: " + node1, null);
                        }
                    });
                }
                default -> throw new MatchException("Unexpected value: " + node, null);
            }
        });
    }

    @Test
    void testSwitchPattern() {
        final BinaryTag copy = tag.copy();
        assertEquals(tag, copy);
        testTag(copy);
    }

    @Test
    void testReadWrite(@TempDir File tempDir) throws IOException {
        final File file = new File(tempDir, "test.dat");
        BinaryTag copy = tag.copy();
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            copy.write(dos);
        }
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            copy = BinaryTag.readRoot(dis);
        }
        assertEquals(tag, copy);
        testTag(copy);
    }
}
