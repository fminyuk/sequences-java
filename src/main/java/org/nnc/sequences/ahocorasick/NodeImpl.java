package org.nnc.sequences.ahocorasick;

import java.util.HashMap;
import java.util.Map;

public class NodeImpl<E, V> implements NodeMutable<NodeImpl<E, V>, E, V> {
    private final Map<E, NodeImpl<E, V>> children = new HashMap<>();
    private NodeImpl<E, V> neg;
    private NodeImpl<E, V> out;
    private V value;

    @Override
    public Map<E, NodeImpl<E, V>> getChildren() {
        return children;
    }

    @Override
    public NodeImpl<E, V> getNeg() {
        return neg;
    }

    @Override
    public NodeImpl<E, V> getOut() {
        return out;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public void setNeg(final NodeImpl<E, V> neg) {
        this.neg = neg;
    }

    @Override
    public void setOut(final NodeImpl<E, V> out) {
        this.out = out;
    }

    @Override
    public void setValue(final V value) {
        this.value = value;
    }
}
