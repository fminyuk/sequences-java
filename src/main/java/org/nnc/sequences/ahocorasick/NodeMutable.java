package org.nnc.sequences.ahocorasick;

import org.nnc.sequences.TrieMutable;

public interface NodeMutable<T extends NodeMutable<T, E, V>, E, V> extends TrieMutable<T, E, V>, Node<E, V> {
    @Override
    T getNeg();

    @Override
    T getOut();

    void setNeg(T neg);

    void setOut(T out);
}
