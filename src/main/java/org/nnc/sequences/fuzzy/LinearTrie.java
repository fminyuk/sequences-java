package org.nnc.sequences.fuzzy;

import java.util.List;

/**
 * Дерево ключей (Trie) в линейном виде.
 *
 * @param <E> Тип символов.
 * @param <V> Тип значений.
 */
public class LinearTrie<E, V> {
    private final List<E> chars;
    private final List<V> values;
    private final int[] lens;

    public LinearTrie(List<E> chars, List<V> values, int[] lens) {
        this.chars = chars;
        this.values = values;
        this.lens = lens;
    }

    public List<E> getChars() {
        return chars;
    }

    public List<V> getValues() {
        return values;
    }

    public int[] getLens() {
        return lens;
    }
}
