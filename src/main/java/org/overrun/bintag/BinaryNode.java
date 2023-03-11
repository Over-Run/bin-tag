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
     * Reads a binary node from the given input.
     *
     * @param in the input.
     * @return the binary node.
     * @throws IOException if an I/O error occurs.
     */
    static BinaryNode read(DataInput in) throws IOException {
        final BinaryDataType type = readType(in);
        return type == BinaryDataType.TAG ? BinaryTag.read(in) : BinaryData.read(in, type);
    }

    /**
     * Reads a type flag from the given input.
     *
     * @param in the input.
     * @return the data type.
     * @throws IOException if an I/O error occurs.
     */
    static BinaryDataType readType(DataInput in) throws IOException {
        return BinaryDataType.byId(in.readByte());
    }

    private static ClassCastException castException() {
        return new ClassCastException("Couldn't cast BinaryTag to BinaryData");
    }

    /**
     * Writes this node to the given output.
     *
     * @param out the output.
     * @throws IOException if an I/O error occurs.
     */
    void write(DataOutput out) throws IOException;

    /**
     * Writes the type flag this node to the given output.
     *
     * @param out the output.
     * @throws IOException if an I/O error occurs.
     */
    default void writeType(DataOutput out) throws IOException {
        out.writeByte(type().serialId());
    }

    /**
     * Gets the data type of this node.
     *
     * @return the data type of this node.
     */
    BinaryDataType type();

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
     * Gets the value as byte array.
     *
     * @return the byte array value.
     */
    default byte[] asByteArray() {
        throw castException();
    }

    /**
     * Gets the value as short array.
     *
     * @return the short array value.
     */
    default short[] asShortArray() {
        throw castException();
    }

    /**
     * Gets the value as int array.
     *
     * @return the int array value.
     */
    default int[] asIntArray() {
        throw castException();
    }

    /**
     * Gets the value as long array.
     *
     * @return the long array value.
     */
    default long[] asLongArray() {
        throw castException();
    }

    /**
     * Gets the value as float array.
     *
     * @return the float array value.
     */
    default float[] asFloatArray() {
        throw castException();
    }

    /**
     * Gets the value as double array.
     *
     * @return the double array value.
     */
    default double[] asDoubleArray() {
        throw castException();
    }

    /**
     * Gets the value as string array.
     *
     * @return the string array value.
     */
    default String[] asStringArray() {
        throw castException();
    }

    /**
     * Gets the value as tag array.
     *
     * @return the tag array value.
     */
    default BinaryTag[] asTagArray() {
        throw castException();
    }
}
