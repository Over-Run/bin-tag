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

import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * The binary data, stored a primitive value or array.
 * <p>
 * This is a value class.
 *
 * @author squid233
 * @since 0.1.0
 */
public sealed abstract /* value */ class BinaryData implements BinaryNode {
    private final byte type;
    private final Object value;
    private final int size;

    private BinaryData(byte type, Object value, int size) {
        this.type = type;
        this.value = value;
        this.size = size;
    }

    public static BinaryData of(byte value) {
        return new OfByte(value);
    }

    public static BinaryData of(short value) {
        return new OfShort(value);
    }

    public static BinaryData of(int value) {
        return new OfInt(value);
    }

    public static BinaryData of(long value) {
        return new OfLong(value);
    }

    public static BinaryData of(float value) {
        return new OfFloat(value);
    }

    public static BinaryData of(double value) {
        return new OfDouble(value);
    }

    public static BinaryData of(String value) {
        return new OfString(value);
    }

    public static BinaryData ofArray(byte... value) {
        return new OfByteArray(value);
    }

    public static BinaryData ofArray(short... value) {
        return new OfShortArray(value);
    }

    public static BinaryData ofArray(int... value) {
        return new OfIntArray(value);
    }

    public static BinaryData ofArray(long... value) {
        return new OfLongArray(value);
    }

    public static BinaryData ofArray(float... value) {
        return new OfFloatArray(value);
    }

    public static BinaryData ofArray(double... value) {
        return new OfDoubleArray(value);
    }

    public static BinaryData ofArray(String... value) {
        return new OfStringArray(value);
    }

    public static BinaryData ofArray(BinaryTag... value) {
        return new OfTagArray(value);
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfByte extends BinaryData {
        private OfByte(byte value) {
            super(BYTE, value, 1);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeByte(asByte());
        }

        @Override
        public byte asByte() {
            return (byte) value();
        }

        @Override
        public @NotNull OfByte copy() {
            return new OfByte(asByte());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfByte that)) return false;
            return type() == that.type() && size() == that.size() && Objects.equals(value(), that.value());
        }
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfShort extends BinaryData {
        private OfShort(short value) {
            super(SHORT, value, 1);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeShort(asShort());
        }

        @Override
        public short asShort() {
            return (short) value();
        }

        @Override
        public @NotNull OfShort copy() {
            return new OfShort(asShort());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfShort that)) return false;
            return type() == that.type() && size() == that.size() && Objects.equals(value(), that.value());
        }
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfInt extends BinaryData {
        private OfInt(int value) {
            super(INT, value, 1);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeInt(asInt());
        }

        @Override
        public int asInt() {
            return (int) value();
        }

        @Override
        public @NotNull OfInt copy() {
            return new OfInt(asInt());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfInt that)) return false;
            return type() == that.type() && size() == that.size() && Objects.equals(value(), that.value());
        }
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfLong extends BinaryData {
        private OfLong(long value) {
            super(LONG, value, 1);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeLong(asLong());
        }

        @Override
        public long asLong() {
            return (long) value();
        }

        @Override
        public @NotNull OfLong copy() {
            return new OfLong(asLong());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfLong that)) return false;
            return type() == that.type() && size() == that.size() && Objects.equals(value(), that.value());
        }
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfFloat extends BinaryData {
        private OfFloat(float value) {
            super(FLOAT, value, 1);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeFloat(asFloat());
        }

        @Override
        public float asFloat() {
            return (float) value();
        }

        @Override
        public @NotNull OfFloat copy() {
            return new OfFloat(asFloat());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfFloat that)) return false;
            return type() == that.type() && size() == that.size() && Objects.equals(value(), that.value());
        }
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfDouble extends BinaryData {
        private OfDouble(double value) {
            super(DOUBLE, value, 1);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeDouble(asDouble());
        }

        @Override
        public double asDouble() {
            return (double) value();
        }

        @Override
        public @NotNull OfDouble copy() {
            return new OfDouble(asDouble());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfDouble that)) return false;
            return type() == that.type() && size() == that.size() && Objects.equals(value(), that.value());
        }
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfString extends BinaryData {
        private OfString(String value) {
            super(STRING, value, 1);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeUTF(asString());
        }

        @Override
        public String asString() {
            return (String) value();
        }

        @Override
        public @NotNull OfString copy() {
            return new OfString(asString());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfString that)) return false;
            return type() == that.type() && size() == that.size() && Objects.equals(value(), that.value());
        }
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfByteArray extends BinaryData {
        private OfByteArray(byte[] value) {
            super(BYTE_ARRAY, value, value.length);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeInt(size());
            for (byte v : asByteArray()) {
                out.writeByte(v);
            }
        }

        @Override
        public byte[] asByteArray() {
            return (byte[]) value();
        }

        @Override
        public @NotNull OfByteArray copy() {
            return new OfByteArray(asByteArray().clone());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfByteArray that)) return false;
            return type() == that.type() && size() == that.size() && Arrays.equals(asByteArray(), that.asByteArray());
        }
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfShortArray extends BinaryData {
        private OfShortArray(short[] value) {
            super(SHORT_ARRAY, value, value.length);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeInt(size());
            for (short v : asShortArray()) {
                out.writeShort(v);
            }
        }

        @Override
        public short[] asShortArray() {
            return (short[]) value();
        }

        @Override
        public @NotNull OfShortArray copy() {
            return new OfShortArray(asShortArray().clone());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfShortArray that)) return false;
            return type() == that.type() && size() == that.size() && Arrays.equals(asShortArray(), that.asShortArray());
        }
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfIntArray extends BinaryData {
        private OfIntArray(int[] value) {
            super(INT_ARRAY, value, value.length);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeInt(size());
            for (int v : asIntArray()) {
                out.writeInt(v);
            }
        }

        @Override
        public int[] asIntArray() {
            return (int[]) value();
        }

        @Override
        public @NotNull OfIntArray copy() {
            return new OfIntArray(asIntArray().clone());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfIntArray that)) return false;
            return type() == that.type() && size() == that.size() && Arrays.equals(asIntArray(), that.asIntArray());
        }
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfLongArray extends BinaryData {
        private OfLongArray(long[] value) {
            super(LONG_ARRAY, value, value.length);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeInt(size());
            for (long v : asLongArray()) {
                out.writeLong(v);
            }
        }

        @Override
        public long[] asLongArray() {
            return (long[]) value();
        }

        @Override
        public @NotNull OfLongArray copy() {
            return new OfLongArray(asLongArray().clone());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfLongArray that)) return false;
            return type() == that.type() && size() == that.size() && Arrays.equals(asLongArray(), that.asLongArray());
        }
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfFloatArray extends BinaryData {
        private OfFloatArray(float[] value) {
            super(FLOAT_ARRAY, value, value.length);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeInt(size());
            for (float v : asFloatArray()) {
                out.writeFloat(v);
            }
        }

        @Override
        public float[] asFloatArray() {
            return (float[]) value();
        }

        @Override
        public @NotNull OfFloatArray copy() {
            return new OfFloatArray(asFloatArray().clone());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfFloatArray that)) return false;
            return type() == that.type() && size() == that.size() && Arrays.equals(asFloatArray(), that.asFloatArray());
        }
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfDoubleArray extends BinaryData {
        private OfDoubleArray(double[] value) {
            super(DOUBLE_ARRAY, value, value.length);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeInt(size());
            for (double v : asDoubleArray()) {
                out.writeDouble(v);
            }
        }

        @Override
        public double[] asDoubleArray() {
            return (double[]) value();
        }

        @Override
        public @NotNull OfDoubleArray copy() {
            return new OfDoubleArray(asDoubleArray().clone());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfDoubleArray that)) return false;
            return type() == that.type() && size() == that.size() && Arrays.equals(asDoubleArray(), that.asDoubleArray());
        }
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfStringArray extends BinaryData {
        private OfStringArray(String[] value) {
            super(STRING_ARRAY, value, value.length);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeInt(size());
            for (String v : asStringArray()) {
                out.writeUTF(v);
            }
        }

        @Override
        public String[] asStringArray() {
            return (String[]) value();
        }

        @Override
        public @NotNull OfStringArray copy() {
            return new OfStringArray(asStringArray().clone());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfStringArray that)) return false;
            return type() == that.type() && size() == that.size() && Arrays.equals(asStringArray(), that.asStringArray());
        }
    }

    /**
     * @author squid233
     * @since 0.2.0
     */
    public static final class OfTagArray extends BinaryData {
        private OfTagArray(BinaryTag[] value) {
            super(TAG_ARRAY, value, value.length);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            writeType(out);
            out.writeInt(size());
            for (BinaryTag v : asTagArray()) {
                v.writeContent(out);
            }
        }

        @Override
        public BinaryTag[] asTagArray() {
            return (BinaryTag[]) value();
        }

        @Override
        public @NotNull OfTagArray copy() {
            final BinaryTag[] array = asTagArray();
            final BinaryTag[] newArray = new BinaryTag[array.length];
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i].copy();
            }
            return new OfTagArray(newArray);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfTagArray that)) return false;
            return type() == that.type() && size() == that.size() && Arrays.equals(asTagArray(), that.asTagArray());
        }
    }

    /**
     * Reads binary data from the given input with the given data type.
     *
     * @param in   the input.
     * @param type the data type. Must not be tag.
     * @return the binary data.
     * @throws IOException if an I/O error occurs.
     * @since 0.2.0
     */
    public static BinaryData read(DataInput in, byte type) throws IOException {
        int size = 1;
        if (BinaryNode.isArrayType(type)) {
            size = in.readInt();
        }
        return switch (type) {
            case BYTE -> of(in.readByte());
            case SHORT -> of(in.readShort());
            case INT -> of(in.readInt());
            case LONG -> of(in.readLong());
            case FLOAT -> of(in.readFloat());
            case DOUBLE -> of(in.readDouble());
            case STRING -> of(in.readUTF());
            case BYTE_ARRAY -> {
                final var arr = new byte[size];
                for (int i = 0; i < size; i++) {
                    arr[i] = in.readByte();
                }
                yield ofArray(arr);
            }
            case SHORT_ARRAY -> {
                final var arr = new short[size];
                for (int i = 0; i < size; i++) {
                    arr[i] = in.readShort();
                }
                yield ofArray(arr);
            }
            case INT_ARRAY -> {
                final var arr = new int[size];
                for (int i = 0; i < size; i++) {
                    arr[i] = in.readInt();
                }
                yield ofArray(arr);
            }
            case LONG_ARRAY -> {
                final var arr = new long[size];
                for (int i = 0; i < size; i++) {
                    arr[i] = in.readLong();
                }
                yield ofArray(arr);
            }
            case FLOAT_ARRAY -> {
                final var arr = new float[size];
                for (int i = 0; i < size; i++) {
                    arr[i] = in.readFloat();
                }
                yield ofArray(arr);
            }
            case DOUBLE_ARRAY -> {
                final var arr = new double[size];
                for (int i = 0; i < size; i++) {
                    arr[i] = in.readDouble();
                }
                yield ofArray(arr);
            }
            case STRING_ARRAY -> {
                final var arr = new String[size];
                for (int i = 0; i < size; i++) {
                    arr[i] = in.readUTF();
                }
                yield ofArray(arr);
            }
            case TAG_ARRAY -> {
                final var arr = new BinaryTag[size];
                for (int i = 0; i < size; i++) {
                    arr[i] = BinaryTag.read(in);
                }
                yield ofArray(arr);
            }
            default ->
                throw new IllegalArgumentException("Expected binary data type, got " + BinaryNode.typeName(type));
        };
    }

    @Override
    public abstract void write(DataOutput out) throws IOException;

    @Override
    public BinaryData asData() {
        return this;
    }

    @Override
    public byte[] asByteArray() {
        return (byte[]) value;
    }

    @Override
    public short[] asShortArray() {
        return (short[]) value;
    }

    @Override
    public int[] asIntArray() {
        return (int[]) value;
    }

    @Override
    public long[] asLongArray() {
        return (long[]) value;
    }

    @Override
    public float[] asFloatArray() {
        return (float[]) value;
    }

    @Override
    public double[] asDoubleArray() {
        return (double[]) value;
    }

    @Override
    public String[] asStringArray() {
        return (String[]) value;
    }

    @Override
    public BinaryTag[] asTagArray() {
        return (BinaryTag[]) value;
    }

    @Override
    public byte type() {
        return type;
    }

    @Override
    public abstract @NotNull BinaryData copy();

    /**
     * Gets the raw value of this binary data.
     *
     * @return the raw value of this binary data.
     */
    public Object value() {
        return value;
    }

    /**
     * Gets the size of the value.
     *
     * @return the size of the value.
     */
    public int size() {
        return size;
    }

    @Override
    public abstract boolean equals(Object o);

    @Override
    public int hashCode() {
        return Objects.hash(type, value, size);
    }

    private static String arrayToString(byte[] array) {
        final StringBuilder sb = new StringBuilder();
        final int iMax = array.length - 1;
        sb.append('[').append('B').append(';');
        for (int i = 0; ; i++) {
            sb.append(array[i]).append('b');
            if (i == iMax) return sb.append(']').toString();
            sb.append(',');
        }
    }

    private static String arrayToString(short[] array) {
        final StringBuilder sb = new StringBuilder();
        final int iMax = array.length - 1;
        sb.append('[').append('S').append(';');
        for (int i = 0; ; i++) {
            sb.append(array[i]).append('s');
            if (i == iMax) return sb.append(']').toString();
            sb.append(',');
        }
    }

    private static String arrayToString(int[] array) {
        final StringBuilder sb = new StringBuilder();
        final int iMax = array.length - 1;
        sb.append('[').append('I').append(';');
        for (int i = 0; ; i++) {
            sb.append(array[i]);
            if (i == iMax) return sb.append(']').toString();
            sb.append(',');
        }
    }

    private static String arrayToString(long[] array) {
        final StringBuilder sb = new StringBuilder();
        final int iMax = array.length - 1;
        sb.append('[').append('L').append(';');
        for (int i = 0; ; i++) {
            sb.append(array[i]).append('L');
            if (i == iMax) return sb.append(']').toString();
            sb.append(',');
        }
    }

    private static String arrayToString(float[] array) {
        final StringBuilder sb = new StringBuilder();
        final int iMax = array.length - 1;
        sb.append('[').append('F').append(';');
        for (int i = 0; ; i++) {
            sb.append(array[i]).append('F');
            if (i == iMax) return sb.append(']').toString();
            sb.append(',');
        }
    }

    private static String arrayToString(double[] array) {
        final StringBuilder sb = new StringBuilder();
        final int iMax = array.length - 1;
        sb.append('[').append('D').append(';');
        for (int i = 0; ; i++) {
            sb.append(array[i]);
            if (i == iMax) return sb.append(']').toString();
            sb.append(',');
        }
    }

    private static String arrayToString(String[] array) {
        final StringBuilder sb = new StringBuilder();
        final int iMax = array.length - 1;
        sb.append('[').append('J').append(';');
        for (int i = 0; ; i++) {
            sb.append('\'').append(array[i]).append('\'');
            if (i == iMax) return sb.append(']').toString();
            sb.append(',');
        }
    }

    private static String arrayToString(BinaryTag[] array) {
        final StringBuilder sb = new StringBuilder();
        final int iMax = array.length - 1;
        sb.append('[');
        for (int i = 0; ; i++) {
            sb.append(array[i]);
            if (i == iMax) return sb.append(']').toString();
            sb.append(',');
        }
    }

    @Override
    public String toString() {
        return switch (type) {
            case BYTE -> value + "b";
            case SHORT -> value + "s";
            case LONG -> value + "L";
            case FLOAT -> value + "F";
            case STRING -> "'" + value + "'";
            case BYTE_ARRAY -> arrayToString(asByteArray());
            case SHORT_ARRAY -> arrayToString(asShortArray());
            case INT_ARRAY -> arrayToString(asIntArray());
            case LONG_ARRAY -> arrayToString(asLongArray());
            case FLOAT_ARRAY -> arrayToString(asFloatArray());
            case DOUBLE_ARRAY -> arrayToString(asDoubleArray());
            case STRING_ARRAY -> arrayToString(asStringArray());
            case TAG_ARRAY -> arrayToString(asTagArray());
            default -> String.valueOf(value);
        };
    }
}
