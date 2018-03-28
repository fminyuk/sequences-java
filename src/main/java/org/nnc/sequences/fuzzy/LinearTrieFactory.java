package org.nnc.sequences.fuzzy;

import org.nnc.sequences.Trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LinearTrieFactory<E, V> {
    public LinearTrie<E, V> create(final Trie<E, V> trie) {
        final int len = getLen(trie);

        final List<E> chars = new ArrayList<>(len);
        final List<V> values = new ArrayList<>(len);
        final int[] lens = new int[len];

        return new LinearTrie<>(chars, values, lens);
    }

    private int getLen(final Trie<E, V> trie) {
        int len = 0;
        for (final Map.Entry<E, ? extends Trie<E, V>> child : trie.getChildren().entrySet()) {
            len += 1 + getLen(child.getValue());
        }

        return len;
    }
}
