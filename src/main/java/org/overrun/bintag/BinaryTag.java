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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The binary tag, stored a mapping of binary nodes.
 * <p>
 * This is a value class.
 *
 * @author squid233
 * @since 0.1.0
 */
public final /* value */ class BinaryTag implements BinaryNode {
    private final Map<String, BinaryNode> map;

    private BinaryTag(int size) {
        map = new HashMap<>(size);
    }

    public static BinaryTag of() {
        return new BinaryTag(16);
    }

    public static BinaryTag of(Map<String, BinaryNode> map) {
        final BinaryTag tag = new BinaryTag(map.size());
        tag.map.putAll(map);
        return tag;
    }

    @SafeVarargs
    public static BinaryTag of(Map.Entry<String, BinaryNode>... entries) {
        final BinaryTag tag = new BinaryTag(entries.length);
        for (var e : entries) {
            tag.map.put(e.getKey(), e.getValue());
        }
        return tag;
    }

    public static BinaryTag read(DataInput in) throws IOException {
        final int size = in.readInt();
        final BinaryTag tag = new BinaryTag(size);
        for (int i = 0; i < size; i++) {
            final String name = in.readUTF();
            final BinaryDataType type = BinaryNode.readType(in);
            tag.map.put(name,
                type == BinaryDataType.TAG
                    ? read(in)
                    : BinaryData.read(in, type));
        }
        return tag;
    }

    public void writeNoType(DataOutput out) throws IOException {
        out.writeInt(map.size());
        for (var e : map.entrySet()) {
            out.writeUTF(e.getKey());
            e.getValue().write(out);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        writeType(out);
        writeNoType(out);
    }

    @Override
    public BinaryDataType type() {
        return BinaryDataType.TAG;
    }

    /**
     * Removes a node.
     *
     * @param name the name of the node.
     * @return the previous node, or {@code null} if there was no mapping for key.
     */
    public BinaryNode remove(String name) {
        return map.remove(name);
    }

    /**
     * Sets the node.
     *
     * @param name      the name of the node.
     * @param node      the new node.
     * @param checkType whether checks the type.
     */
    public void set(String name, BinaryNode node, boolean checkType) {
        if (checkType) {
            final BinaryDataType type = get(name).type();
            if (type != node.type()) {
                throw new IllegalArgumentException("The previous type was " + type + ", which is not match the new type " + node.type());
            }
        }
        map.put(name, node);
    }

    /**
     * Checks type and sets the node.
     *
     * @param name the name of the node.
     * @param node the new node.
     */
    public void set(String name, BinaryNode node) {
        set(name, node, true);
    }

    public void set(String name, byte value) {
        set(name, BinaryData.of(value));
    }

    public void set(String name, short value) {
        set(name, BinaryData.of(value));
    }

    public void set(String name, int value) {
        set(name, BinaryData.of(value));
    }

    public void set(String name, long value) {
        set(name, BinaryData.of(value));
    }

    public void set(String name, float value) {
        set(name, BinaryData.of(value));
    }

    public void set(String name, double value) {
        set(name, BinaryData.of(value));
    }

    public void set(String name, String value) {
        set(name, BinaryData.of(value));
    }

    public void set(String name, BinaryTag value) {
        set(name, (BinaryNode) value);
    }

    public void set(String name, byte... value) {
        set(name, BinaryData.ofArray(value));
    }

    public void set(String name, short... value) {
        set(name, BinaryData.ofArray(value));
    }

    public void set(String name, int... value) {
        set(name, BinaryData.ofArray(value));
    }

    public void set(String name, long... value) {
        set(name, BinaryData.ofArray(value));
    }

    public void set(String name, float... value) {
        set(name, BinaryData.ofArray(value));
    }

    public void set(String name, double... value) {
        set(name, BinaryData.ofArray(value));
    }

    public void set(String name, String... value) {
        set(name, BinaryData.ofArray(value));
    }

    public void set(String name, BinaryTag... value) {
        set(name, BinaryData.ofArray(value));
    }

    /**
     * Gets a node with the given name.
     *
     * @param name the name of the node.
     * @return the new node.
     */
    public BinaryNode get(String name) {
        return map.get(name);
    }

    public BinaryNode get(String name, BinaryDataType type) {
        final BinaryNode node = get(name);
        if (node.type() != type) {
            throw new IllegalArgumentException("Expected type " + type + " for tag '" + name + "', got " + node.type());
        }
        return node;
    }

    public BinaryData getData(String name) {
        final BinaryNode node = get(name);
        if (node.type() == BinaryDataType.TAG) {
            throw new IllegalArgumentException("Expected type not TAG for tag '" + name + "', got TAG");
        }
        return node.asData();
    }

    public BinaryData getData(String name, BinaryDataType type) {
        if (type == BinaryDataType.TAG) {
            throw new IllegalArgumentException("Expected type not TAG for tag '" + name + "', got TAG");
        }
        return get(name, type).asData();
    }

    public BinaryTag getTag(String name) {
        return get(name, BinaryDataType.TAG).asTag();
    }

    public byte getByte(String name) {
        return getData(name, BinaryDataType.BYTE).asByte();
    }

    public short getShort(String name) {
        return getData(name, BinaryDataType.SHORT).asShort();
    }

    public int getInt(String name) {
        return getData(name, BinaryDataType.INT).asInt();
    }

    public long getLong(String name) {
        return getData(name, BinaryDataType.LONG).asLong();
    }

    public float getFloat(String name) {
        return getData(name, BinaryDataType.FLOAT).asFloat();
    }

    public double getDouble(String name) {
        return getData(name, BinaryDataType.DOUBLE).asDouble();
    }

    public String getString(String name) {
        return getData(name, BinaryDataType.STRING).asString();
    }

    public byte[] getByteArray(String name) {
        return getData(name, BinaryDataType.BYTE_ARRAY).asByteArray();
    }

    public short[] getShortArray(String name) {
        return getData(name, BinaryDataType.SHORT_ARRAY).asShortArray();
    }

    public int[] getIntArray(String name) {
        return getData(name, BinaryDataType.INT_ARRAY).asIntArray();
    }

    public long[] getLongArray(String name) {
        return getData(name, BinaryDataType.LONG_ARRAY).asLongArray();
    }

    public float[] getFloatArray(String name) {
        return getData(name, BinaryDataType.FLOAT_ARRAY).asFloatArray();
    }

    public double[] getDoubleArray(String name) {
        return getData(name, BinaryDataType.DOUBLE_ARRAY).asDoubleArray();
    }

    public String[] getStringArray(String name) {
        return getData(name, BinaryDataType.STRING_ARRAY).asStringArray();
    }

    public BinaryTag[] getTagArray(String name) {
        return getData(name, BinaryDataType.TAG_ARRAY).asTagArray();
    }

    @Override
    public BinaryTag asTag() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryTag tag = (BinaryTag) o;
        return Objects.equals(map, tag.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(256);
        sb.append('{');
        for (var e : map.entrySet()) {
            sb.append(e.getKey()).append(": ");
            final BinaryNode value = e.getValue();
            if (value.type() == BinaryDataType.STRING) {
                sb.append('\'').append(value.asString()).append('\'');
            } else {
                sb.append(value);
            }
            sb.append(", ");
        }
        sb.append('}');
        return sb.toString();
    }
}
