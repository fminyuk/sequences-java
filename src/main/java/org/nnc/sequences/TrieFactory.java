package org.nnc.sequences;

import java.util.List;

public class TrieFactory {
    private TrieFactory() {
    }

    public static <E, V> Trie<E, V> create(final List<Sequence<E, V>> sequences) {
        final TrieFactoryBase<TrieImpl<E, V>, E, V> factory = new TrieFactoryBase<TrieImpl<E, V>, E, V>() {
            @Override
            protected TrieImpl<E, V> createNode() {
                return new TrieImpl<>();
            }
        };

        return factory.createTrie(sequences);
    }
}
