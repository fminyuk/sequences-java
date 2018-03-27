package org.nnc.sequences;

import static org.apache.commons.lang3.ArrayUtils.toObject;

public class SequenceFactory {
    private SequenceFactory() {
    }

    public static Sequence<Character, String> create(String str) {
        return create(str, str);
    }

    public static <V> Sequence<Character, V> create(String str, V value) {
        final Character[] chars = toObject(str.toCharArray());

        return new SequenceImpl<>(chars, value);
    }
}
