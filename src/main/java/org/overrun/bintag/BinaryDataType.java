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

/**
 * The type of binary data.
 *
 * @author squid233
 * @since 0.1.0
 */
@Deprecated(forRemoval = true)
public enum BinaryDataType {
    BYTE(false),
    SHORT(false),
    INT(false),
    LONG(false),
    FLOAT(false),
    DOUBLE(false),
    STRING(false),
    TAG(false),
    BYTE_ARRAY(true),
    SHORT_ARRAY(true),
    INT_ARRAY(true),
    LONG_ARRAY(true),
    FLOAT_ARRAY(true),
    DOUBLE_ARRAY(true),
    STRING_ARRAY(true),
    TAG_ARRAY(true);

    private static final BinaryDataType[] VALUES = values();
    private final boolean array;

    BinaryDataType(boolean array) {
        this.array = array;
    }

    /**
     * Gets the data type with the given id.
     *
     * @param id the id.
     * @return the data type.
     */
    public static BinaryDataType byId(int id) {
        return VALUES[id];
    }

    /**
     * Gets the number id for serialization.
     *
     * @return the number id for serialization.
     */
    public int serialId() {
        return ordinal();
    }

    /**
     * Returns {@code true} if this is an array type.
     *
     * @return {@code true} if this is an array type.
     */
    public boolean isArray() {
        return array;
    }
}
