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
import java.util.Arrays;
import java.util.Objects;

/**
 * The binary data, stored a primitive value or array.
 * <p>
 * This is a primitive class.
 *
 * @author squid233
 * @since 0.1.0
 */
public final /* primitive */ class BinaryData implements BinaryNode {
    private final BinaryDataType type;
    private final Object value;
    private final int size;

    private BinaryData(BinaryDataType type, Object value, int size) {
        this.type = type;
        this.value = value;
        this.size = size;
    }

    public static BinaryData of(byte value) {
        return new BinaryData(BinaryDataType.BYTE, value, 1);
    }

    public static BinaryData of(short value) {
        return new BinaryData(BinaryDataType.SHORT, value, 1);
    }

    public static BinaryData of(int value) {
        return new BinaryData(BinaryDataType.INT, value, 1);
    }

    public static BinaryData of(long value) {
        return new BinaryData(BinaryDataType.LONG, value, 1);
    }

    public static BinaryData of(float value) {
        return new BinaryData(BinaryDataType.FLOAT, value, 1);
    }

    public static BinaryData of(double value) {
        return new BinaryData(BinaryDataType.DOUBLE, value, 1);
    }

    public static BinaryData of(String value) {
        return new BinaryData(BinaryDataType.STRING, value, 1);
    }

    public static BinaryData ofArray(byte... value) {
        return new BinaryData(BinaryDataType.BYTE_ARRAY, value, value.length);
    }

    public static BinaryData ofArray(short... value) {
        return new BinaryData(BinaryDataType.SHORT_ARRAY, value, value.length);
    }

    public static BinaryData ofArray(int... value) {
        return new BinaryData(BinaryDataType.INT_ARRAY, value, value.length);
    }

    public static BinaryData ofArray(long... value) {
        return new BinaryData(BinaryDataType.LONG_ARRAY, value, value.length);
    }

    public static BinaryData ofArray(float... value) {
        return new BinaryData(BinaryDataType.FLOAT_ARRAY, value, value.length);
    }

    public static BinaryData ofArray(double... value) {
        return new BinaryData(BinaryDataType.DOUBLE_ARRAY, value, value.length);
    }

    public static BinaryData ofArray(String... value) {
        return new BinaryData(BinaryDataType.STRING_ARRAY, value, value.length);
    }

    public static BinaryData ofArray(BinaryTag... value) {
        return new BinaryData(BinaryDataType.TAG_ARRAY, value, value.length);
    }

    /**
     * Reads a binary data from the given input with the given data type.
     *
     * @param in   the input.
     * @param type the data type. Must not be tag.
     * @return the binary data.
     * @throws IOException if an I/O error occurs.
     */
    public static BinaryData read(DataInput in, BinaryDataType type) throws IOException {
        int size = 1;
        if (type.isArray()) {
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
            default -> throw new IllegalArgumentException("Expected binary data type, got " + type);
        };
    }

    @Override
    public void write(DataOutput out) throws IOException {
        writeType(out);
        if (type.isArray()) {
            out.writeInt(size);
        }
        switch (type) {
            case BYTE -> out.writeByte((byte) value);
            case SHORT -> out.writeShort((short) value);
            case INT -> out.writeInt((int) value);
            case LONG -> out.writeLong((long) value);
            case FLOAT -> out.writeFloat((float) value);
            case DOUBLE -> out.writeDouble((double) value);
            case STRING -> out.writeUTF((String) value);
            case BYTE_ARRAY -> {
                final var arr = asByteArray();
                for (var v : arr) {
                    out.writeByte(v);
                }
            }
            case SHORT_ARRAY -> {
                final var arr = asShortArray();
                for (var v : arr) {
                    out.writeShort(v);
                }
            }
            case INT_ARRAY -> {
                final var arr = asIntArray();
                for (var v : arr) {
                    out.writeInt(v);
                }
            }
            case LONG_ARRAY -> {
                final var arr = asLongArray();
                for (var v : arr) {
                    out.writeLong(v);
                }
            }
            case FLOAT_ARRAY -> {
                final var arr = asFloatArray();
                for (var v : arr) {
                    out.writeFloat(v);
                }
            }
            case DOUBLE_ARRAY -> {
                final var arr = asDoubleArray();
                for (var v : arr) {
                    out.writeDouble(v);
                }
            }
            case STRING_ARRAY -> {
                final var arr = asStringArray();
                for (var v : arr) {
                    out.writeUTF(v);
                }
            }
            case TAG_ARRAY -> {
                final var arr = asTagArray();
                for (var v : arr) {
                    v.writeNoType(out);
                }
            }
        }
    }

    @Override
    public byte asByte() {
        return (byte) value;
    }

    @Override
    public short asShort() {
        return (short) value;
    }

    @Override
    public int asInt() {
        return (int) value;
    }

    @Override
    public long asLong() {
        return (long) value;
    }

    @Override
    public float asFloat() {
        return (float) value;
    }

    @Override
    public double asDouble() {
        return (double) value;
    }

    @Override
    public String asString() {
        return (String) value;
    }

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
    public BinaryDataType type() {
        return type;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryData that = (BinaryData) o;
        if (size == that.size && type == that.type) {
            if (type.isArray()) return Arrays.equals((Object[]) value, (Object[]) that.value);
            return Objects.equals(value, that.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value, size);
    }

    @Override
    public String toString() {
        if (type.isArray()) {
            return switch (type) {
                case BYTE_ARRAY -> Arrays.toString(asByteArray());
                case SHORT_ARRAY -> Arrays.toString(asShortArray());
                case INT_ARRAY -> Arrays.toString(asIntArray());
                case LONG_ARRAY -> Arrays.toString(asLongArray());
                case FLOAT_ARRAY -> Arrays.toString(asFloatArray());
                case DOUBLE_ARRAY -> Arrays.toString(asDoubleArray());
                case STRING_ARRAY -> Arrays.toString(asStringArray());
                case TAG_ARRAY -> Arrays.toString(asTagArray());
                default -> throw new AssertionError("should not reach here");
            };
        } else {
            return String.valueOf(value);
        }
    }
}
