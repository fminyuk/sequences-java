package org.nnc.sequences.fuzzy;

import org.nnc.sequences.Trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LinearTrieFactory<E, V> {
    public LinearTrie<E, V> create(final Trie<E, V> trie) {
        final List<E> chars = new ArrayList<>();
        final List<V> values = new ArrayList<>();
        final List<Integer> lens = new ArrayList<>();

        fill(chars, values, lens, null, trie);

        return new LinearTrie<>(chars, values, lens.stream().mapToInt(i -> i).toArray());
    }

    private int fill(final List<E> chars, final List<V> values, final List<Integer> lens, final E c, final Trie<E, V> trie) {
        final int cur = lens.size();
        chars.add(c);
        values.add(trie.getValue());
        lens.add(0);

        int len = 0;
        for (final Map.Entry<E, ? extends Trie<E, V>> child : trie.getChildren().entrySet()) {
            len += fill(chars, values, lens, child.getKey(), child.getValue());
        }

        lens.set(cur, len);

        return len + 1;
    }
}
