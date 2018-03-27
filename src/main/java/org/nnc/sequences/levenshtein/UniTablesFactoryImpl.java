package org.nnc.sequences.levenshtein;

import java.util.*;

public class UniTablesFactoryImpl implements UniTablesFactory {
    @Override
    public UniTables create(int n) {
        final StateCalc calc = new StateCalc(n);

        final Map<State, State[]> transitionsStates = calcTransitions(calc);
        final Map<State, Integer> state2index = calcState2Index(transitionsStates.keySet());

        // Start
        final int start = state2index.get(calc.first());

        // Переходы
        final int[][] transitions = new int[transitionsStates.size()][1 << (2 * n + 1)];
        for (final Map.Entry<State, State[]> entry : transitionsStates.entrySet()) {
            final int[] stateTransitions = transitions[state2index.get(entry.getKey())];
            for (int z = 0; z < (1 << (2 * n + 1)); z++) {
                stateTransitions[z] = state2index.get(entry.getValue()[z]);
            }
        }

        // Стоимость
        final int[][] cost = new int[transitionsStates.size()][2 * n + 1];
        for (final Map.Entry<State, Integer> entry : state2index.entrySet()) {
            final int[] stateCost = cost[entry.getValue()];
            for (int i = -n; i <= n; i++) {
                stateCost[i + n] = calc.cost(entry.getKey(), i);
            }
        }

        return new UniTables(n, start, transitions, cost);
    }

    private static Map<State, State[]> calcTransitions(final StateCalc calc) {
        final Map<State, State[]> transitions = new HashMap<>();
        final Deque<State> states = new ArrayDeque<>();

        states.push(calc.first());
        while (!states.isEmpty()) {
            final State state = states.poll();
            transitions.put(state, new State[1 << (2 * calc.getN() + 1)]);

            for (int z = 0; z < (1 << (2 * calc.getN() + 1)); z++) {
                final State next = calc.next(state, z).addI(-1);
                transitions.get(state)[z] = next;
                if (!transitions.containsKey(next)) {
                    states.push(next);
                }
            }
        }

        return transitions;
    }

    private static Map<State, Integer> calcState2Index(final Collection<State> states) {
        final Map<State, Integer> state2index = new HashMap<>();
        for (final State state : states) {
            state2index.put(state, state2index.size());
        }

        return state2index;
    }
}
