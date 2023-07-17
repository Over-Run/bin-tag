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
import org.jetbrains.annotations.UnmodifiableView;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
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
    private Map<String, BinaryNode> mapView = null;

    private BinaryTag(int size) {
        this.map = HashMap.newHashMap(size);
    }

    public static BinaryTag of() {
        return of(16);
    }

    /**
     * Creates an empty tag with the given initial capacity.
     *
     * @param size the initial capacity.
     * @return the tag.
     * @since 0.2.0
     */
    public static BinaryTag of(int size) {
        return new BinaryTag(size);
    }

    public static BinaryTag of(Map<String, ? extends BinaryNode> map) {
        final BinaryTag tag = new BinaryTag(map.size());
        tag.map.putAll(map);
        return tag;
    }

    @SafeVarargs
    public static BinaryTag of(Map.Entry<String, ? extends BinaryNode>... entries) {
        return of(Map.ofEntries(entries));
    }

    public static BinaryTag read(DataInput in) throws IOException {
        final int size = in.readInt();
        final BinaryTag tag = new BinaryTag(size);
        for (int i = 0; i < size; i++) {
            final String name = in.readUTF();
            final byte type = in.readByte();
            tag.map.put(name,
                type == TAG
                    ? read(in)
                    : BinaryData.read(in, type));
        }
        return tag;
    }

    /**
     * Reads a root tag from the given input.
     *
     * @param in the input.
     * @return the root tag.
     * @throws IOException if an I/O error occurs.
     * @since 0.2.0
     */
    public static BinaryTag readRoot(DataInput in) throws IOException {
        return BinaryNode.read(in).asTag();
    }

    /**
     * Writes the content.
     *
     * @param out the output
     * @throws IOException if an I/O error occurs.
     * @since 0.2.0
     */
    public void writeContent(DataOutput out) throws IOException {
        out.writeInt(map.size());
        for (var e : map.entrySet()) {
            out.writeUTF(e.getKey());
            e.getValue().write(out);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        writeType(out);
        writeContent(out);
    }

    @Override
    public byte type() {
        return TAG;
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
            final byte type = get(name).type();
            if (type != node.type()) {
                throw new IllegalArgumentException("The previous type was " + BinaryNode.typeName(type) + ", which does not match the new type " + BinaryNode.typeName(node.type()));
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

    /**
     * Gets a value from this tag with the given name and check the type.
     *
     * @param name the name.
     * @param type the type.
     * @return the value.
     * @since 0.2.0
     */
    public BinaryNode get(String name, byte type) {
        final BinaryNode node = get(name);
        if (node.type() != type) {
            throw new IllegalArgumentException("Expected type " + BinaryNode.typeName(type) + " for tag '" + name + "', got " + BinaryNode.typeName(node.type()));
        }
        return node;
    }

    public BinaryData getData(String name) {
        final BinaryNode node = get(name);
        if (node.type() == TAG) {
            throw new IllegalArgumentException("Expected type not TAG for tag '" + name + "', got TAG");
        }
        return node.asData();
    }

    /**
     * Gets a {@link BinaryData} value from this tag with the given name and check the type.
     *
     * @param name the name.
     * @param type the type.
     * @return the value.
     * @since 0.2.0
     */
    public BinaryData getData(String name, byte type) {
        if (type == TAG) {
            throw new IllegalArgumentException("Expected type not TAG for tag '" + name + "', got TAG");
        }
        return get(name, type).asData();
    }

    public BinaryTag getTag(String name) {
        return get(name, TAG).asTag();
    }

    public byte getByte(String name) {
        return getData(name, BYTE).asByte();
    }

    public short getShort(String name) {
        return getData(name, SHORT).asShort();
    }

    public int getInt(String name) {
        return getData(name, INT).asInt();
    }

    public long getLong(String name) {
        return getData(name, LONG).asLong();
    }

    public float getFloat(String name) {
        return getData(name, FLOAT).asFloat();
    }

    public double getDouble(String name) {
        return getData(name, DOUBLE).asDouble();
    }

    public String getString(String name) {
        return getData(name, STRING).asString();
    }

    public byte[] getByteArray(String name) {
        return getData(name, BYTE_ARRAY).asByteArray();
    }

    public short[] getShortArray(String name) {
        return getData(name, SHORT_ARRAY).asShortArray();
    }

    public int[] getIntArray(String name) {
        return getData(name, INT_ARRAY).asIntArray();
    }

    public long[] getLongArray(String name) {
        return getData(name, LONG_ARRAY).asLongArray();
    }

    public float[] getFloatArray(String name) {
        return getData(name, FLOAT_ARRAY).asFloatArray();
    }

    public double[] getDoubleArray(String name) {
        return getData(name, DOUBLE_ARRAY).asDoubleArray();
    }

    public String[] getStringArray(String name) {
        return getData(name, STRING_ARRAY).asStringArray();
    }

    public BinaryTag[] getTagArray(String name) {
        return getData(name, TAG_ARRAY).asTagArray();
    }

    /**
     * {@return an unmodifiable view of the map}
     *
     * @since 0.2.0
     */
    public @UnmodifiableView Map<String, BinaryNode> map() {
        if (mapView == null) {
            mapView = Collections.unmodifiableMap(map);
        }
        return mapView;
    }

    @Override
    public @NotNull BinaryTag copy() {
        final var newMap = HashMap.<String, BinaryNode>newHashMap(map.size());
        map.forEach((name, node) -> newMap.put(name, node.copy()));
        return of(newMap);
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

    private static void appendString(StringBuilder b, Map.Entry<String, BinaryNode> e) {
        b.append('\'').append(e.getKey()).append("': ").append(e.getValue());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(256);
        sb.append('{');
        var it = map.entrySet().iterator();
        if (it.hasNext()) {
            appendString(sb, it.next());
            while (it.hasNext()) {
                sb.append(", ");
                appendString(sb, it.next());
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
