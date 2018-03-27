package org.nnc.sequences;

import java.util.HashMap;
import java.util.Map;

public class TrieImpl<E, V> implements TrieMutable<TrieImpl<E, V>, E, V> {
    private final Map<E, TrieImpl<E, V>> children = new HashMap<>();
    private V value;

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public void setValue(final V value) {
        this.value = value;
    }

    @Override
    public Map<E, TrieImpl<E, V>> getChildren() {
        return children;
    }
}
