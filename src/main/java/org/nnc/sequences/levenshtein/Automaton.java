package org.nnc.sequences.levenshtein;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Детерминированный автомат Левенштейна для заданной последовательности.
 *
 * @param <E> Тип элемента последовательности.
 */
public class Automaton<E> {
    private final int n;
    private final E[] sequence;
    private final UniTables uni;

    public Automaton(E[] sequence, int n, UniTablesFactory uniTablesFactory) {
        this.n = n;
        this.sequence = sequence;
        this.uni = uniTablesFactory.create(n);
    }

    public AutomatonPointer start() {
        return new AutomatonPointer(uni.getStart(), 0);
    }

    public AutomatonPointer next(final AutomatonPointer pointer, E item) {
        final int z = calcZ(item, pointer.getPosition());
        final int nextState = uni.getTransitions()[pointer.getState()][z];

        return new AutomatonPointer(nextState, pointer.getPosition() + 1);
    }

    public int cost(final AutomatonPointer pointer) {
        final int diff = sequence.length - pointer.getPosition();
        int cost = n + 1;

        if (diff >= -n && diff <= n) {
            cost = uni.getCost()[pointer.getState()][diff + n];
        }

        return cost == n + 1 ? -1 : cost;
    }

    private int calcZ(E item, int position) {
        int z = 0;
        for (int i = max(0, position - n); i <= min(sequence.length - 1, position + n); i++) {
            if (item.equals(sequence[i])) {
                z |= 1 << (n - (i - position));
            }
        }

        return z;
    }
}
