package org.nnc.sequences.fuzzy;

import org.nnc.sequences.levenshtein.Automaton;
import org.nnc.sequences.levenshtein.AutomatonPointer;

import java.util.ArrayList;
import java.util.List;

public class LinearFuzzyMatcher<E, V> {
    private final LinearTrie<E, V> linear;

    public LinearFuzzyMatcher(LinearTrie<E, V> linear) {
        this.linear = linear;
    }

    public List<FuzzyMatch<V>> search(Automaton<E> pattern) {
        final List<FuzzyMatch<V>> matches = new ArrayList<>();

        search(0, pattern, pattern.start(), matches);

        return matches;
    }

    private int search(int pos, Automaton<E> pattern, AutomatonPointer pointer, List<FuzzyMatch<V>> matches) {
        final V nodeValue = linear.getValues().get(pos);
        if (nodeValue != null) {
            final int cost = pattern.cost(pointer);
            if (cost >= 0) {
                matches.add(new FuzzyMatch<>(nodeValue, cost));
            }
        }

        final int size = 1 + linear.getLens()[pos];
        if(!pattern.isStop(pointer)) {
            int offset = 1;
            while (offset < size) {
                final AutomatonPointer next = pattern.next(pointer, linear.getChars().get(pos + offset));
                offset += search(pos + offset, pattern, next, matches);
            }
        }

        return size;
    }
}
