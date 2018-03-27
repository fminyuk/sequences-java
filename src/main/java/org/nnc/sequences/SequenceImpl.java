package org.nnc.sequences;

public class SequenceImpl<E, V> implements Sequence<E, V> {
    private final E[] chars;
    private final V value;

    public SequenceImpl(final E[] chars, final V value) {
        this.chars = chars;
        this.value = value;
    }

    public E[] getChars() {
        return chars;
    }

    public V getValue() {
        return value;
    }
}
