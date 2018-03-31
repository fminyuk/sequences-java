package org.nnc.sequences.fuzzy;

import org.nnc.sequences.Trie;
import org.nnc.sequences.levenshtein.Automaton;
import org.nnc.sequences.levenshtein.AutomatonPointer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FuzzyMatcher<E, V> {
    private final Trie<E, V> trie;

    public FuzzyMatcher(Trie<E, V> trie) {
        this.trie = trie;
    }

    public List<FuzzyMatch<V>> search(Automaton<E> pattern) {
        final List<FuzzyMatch<V>> matches = new ArrayList<>();

        search(trie, pattern, pattern.start(), matches);

        return matches;
    }

    private void search(Trie<E, V> node, Automaton<E> automaton, AutomatonPointer pointer, List<FuzzyMatch<V>> matches) {
        final V nodeValue = node.getValue();
        if (nodeValue != null) {
            final int cost = automaton.cost(pointer);
            if (cost >= 0) {
                matches.add(new FuzzyMatch<>(nodeValue, cost));
            }
        }

        for (final Map.Entry<E, ? extends Trie<E, V>> child : node.getChildren().entrySet()) {
            final AutomatonPointer next = automaton.next(pointer, child.getKey());
            search(child.getValue(), automaton, next, matches);
        }
    }
}
