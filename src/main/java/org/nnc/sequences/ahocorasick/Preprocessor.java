package org.nnc.sequences.ahocorasick;

import org.nnc.sequences.Sequence;

import java.util.*;

public class Preprocessor {
    private Preprocessor() {
    }

    /**
     * Строит дерево ключей для алгоритма Ахо-корасика.
     *
     * @param sequences Ключи.
     * @param <E>       Тип элемента ключа.
     * @param <V>       Тип значения ключа.
     * @return Дерево ключей для алгоритма Ахо-корасика
     */
    public static <E, V> Node<E, V> create(final List<Sequence<E, V>> sequences) {
        final PreprocessorBase<NodeImpl<E, V>, E, V> preprocessor = new PreprocessorBase<NodeImpl<E, V>, E, V>() {
            @Override
            protected NodeImpl<E, V> createNode() {
                return new NodeImpl<>();
            }
        };

        return preprocessor.createNode(sequences);
    }
}
