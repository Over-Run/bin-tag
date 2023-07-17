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

package org.overrun.bintag;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * The base binary tags node. The implementations are primitive classes.
 *
 * @author squid233
 * @since 0.1.0
 */
public sealed interface BinaryNode permits BinaryData, BinaryTag {
    /**
     * The data type.
     *
     * @since 0.2.0
     */
    byte BYTE = 0,
        SHORT = 1,
        INT = 2,
        LONG = 3,
        FLOAT = 4,
        DOUBLE = 5,
        STRING = 6,
        TAG = 7,
        BYTE_ARRAY = 8,
        SHORT_ARRAY = 9,
        INT_ARRAY = 10,
        LONG_ARRAY = 11,
        FLOAT_ARRAY = 12,
        DOUBLE_ARRAY = 13,
        STRING_ARRAY = 14,
        TAG_ARRAY = 15;

    /**
     * {@return {@code true} the given type is an array type}
     *
     * @param type the type.
     * @since 0.2.0
     */
    static boolean isArrayType(byte type) {
        return type == BYTE_ARRAY
            || type == SHORT_ARRAY
            || type == INT_ARRAY
            || type == LONG_ARRAY
            || type == FLOAT_ARRAY
            || type == DOUBLE_ARRAY
            || type == STRING_ARRAY
            || type == TAG_ARRAY;
    }

    /**
     * {@return the name of the given type}
     *
     * @param type the type.
     * @since 0.2.0
     */
    static String typeName(byte type) {
        return switch (type) {
            case BYTE -> "BYTE";
            case SHORT -> "SHORT";
            case INT -> "INT";
            case LONG -> "LONG";
            case FLOAT -> "FLOAT";
            case DOUBLE -> "DOUBLE";
            case STRING -> "STRING";
            case TAG -> "TAG";
            case BYTE_ARRAY -> "BYTE_ARRAY";
            case SHORT_ARRAY -> "SHORT_ARRAY";
            case INT_ARRAY -> "INT_ARRAY";
            case LONG_ARRAY -> "LONG_ARRAY";
            case FLOAT_ARRAY -> "FLOAT_ARRAY";
            case DOUBLE_ARRAY -> "DOUBLE_ARRAY";
            case STRING_ARRAY -> "STRING_ARRAY";
            case TAG_ARRAY -> "TAG_ARRAY";
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    /**
     * Reads a binary node from the given input.
     *
     * @param in the input.
     * @return the binary node.
     * @throws IOException if an I/O error occurs.
     */
    static BinaryNode read(DataInput in) throws IOException {
        final byte type = in.readByte();
        return type == TAG ? BinaryTag.read(in) : BinaryData.read(in, type);
    }

    private static ClassCastException castException() {
        return new ClassCastException("Couldn't cast the value");
    }

    /**
     * Writes this node to the given output.
     *
     * @param out the output.
     * @throws IOException if an I/O error occurs.
     */
    void write(DataOutput out) throws IOException;

    /**
     * Writes the type flag of this node to the given output.
     *
     * @param out the output.
     * @throws IOException if an I/O error occurs.
     */
    default void writeType(DataOutput out) throws IOException {
        out.writeByte(type());
    }

    /**
     * Gets the data type of this node.
     *
     * @return the data type of this node.
     * @since 0.2.0
     */
    byte type();

    /**
     * Gets the value as byte.
     *
     * @return the byte value.
     */
    default byte asByte() {
        throw castException();
    }

    /**
     * Gets the value as short.
     *
     * @return the short value.
     */
    default short asShort() {
        throw castException();
    }

    /**
     * Gets the value as int.
     *
     * @return the int value.
     */
    default int asInt() {
        throw castException();
    }

    /**
     * Gets the value as long.
     *
     * @return the long value.
     */
    default long asLong() {
        throw castException();
    }

    /**
     * Gets the value as float.
     *
     * @return the float value.
     */
    default float asFloat() {
        throw castException();
    }

    /**
     * Gets the value as double.
     *
     * @return the double value.
     */
    default double asDouble() {
        throw castException();
    }

    /**
     * Gets the value as string.
     *
     * @return the string value.
     */
    default String asString() {
        throw castException();
    }

    /**
     * Gets this as data.
     *
     * @return the data.
     */
    default BinaryData asData() {
        throw castException();
    }

    /**
     * Gets this as tag.
     *
     * @return the tag.
     */
    default BinaryTag asTag() {
        throw new ClassCastException("Couldn't cast BinaryData to BinaryTag");
    }

    /**
     * Gets the value as a byte array.
     *
     * @return the byte array value.
     */
    default byte[] asByteArray() {
        throw castException();
    }

    /**
     * Gets the value as a short array.
     *
     * @return the short array value.
     */
    default short[] asShortArray() {
        throw castException();
    }

    /**
     * Gets the value as an int array.
     *
     * @return the int array value.
     */
    default int[] asIntArray() {
        throw castException();
    }

    /**
     * Gets the value as a long array.
     *
     * @return the long array value.
     */
    default long[] asLongArray() {
        throw castException();
    }

    /**
     * Gets the value as a float array.
     *
     * @return the float array value.
     */
    default float[] asFloatArray() {
        throw castException();
    }

    /**
     * Gets the value as a double array.
     *
     * @return the double array value.
     */
    default double[] asDoubleArray() {
        throw castException();
    }

    /**
     * Gets the value as a string array.
     *
     * @return the string array value.
     */
    default String[] asStringArray() {
        throw castException();
    }

    /**
     * Gets the value as a tag array.
     *
     * @return the tag array value.
     */
    default BinaryTag[] asTagArray() {
        throw castException();
    }

    /**
     * {@return a <strong>deep</strong> copy of this node}
     *
     * @since 0.2.0
     */
    @Contract(" -> new")
    @NotNull BinaryNode copy();
}
