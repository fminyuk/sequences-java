package org.nnc.sequences.fuzzy;

import org.nnc.sequences.Trie;
import org.nnc.sequences.levenshtein.Automaton;
import org.nnc.sequences.levenshtein.AutomatonPointer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FuzzyMatcher<E, V> {
    public List<FuzzyMatch<V>> search(Trie<E, V> trie, Automaton<E> levenshtein) {
        final List<FuzzyMatch<V>> matches = new ArrayList<>();

        search(trie, levenshtein, levenshtein.start(), matches);

        return matches;
    }

    private void search(Trie<E, V> node, Automaton<E> automaton, AutomatonPointer pointer, List<FuzzyMatch<V>> matches) {
        if (node.getValue() != null) {
            final int cost = automaton.cost(pointer);
            if (cost >= 0) {
                matches.add(new FuzzyMatch<>(node.getValue(), cost));
            }
        }

        for (final Map.Entry<E, ? extends Trie<E, V>> child : node.getChildren().entrySet()) {
            final AutomatonPointer next = automaton.next(pointer, child.getKey());
            search(child.getValue(), automaton, next, matches);
        }
    }
}
