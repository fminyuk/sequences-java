package org.nnc.sequences.levenshtein;

import java.util.*;

import static java.lang.Math.abs;

public class StateCalc {
    private final int n;

    public StateCalc(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public State first() {
        return State.create(0, Collections.singletonList(new Position(0, 0)));
    }

    public State next(final State state, final int z) {
        final Set<Position> positions = new HashSet<>();
        for (final Position position : state.getPositions()) {
            positions.addAll(getElementary(position, z >> (state.getI() - position.getI() + position.getE())));
        }

        return State.create(state.getI() + 1, positions);
    }

    public int cost(final State state, final int i) {
        int stateCost = n + 1;
        for (final Position position : state.getPositions()) {
            final int positionCost = position.getE() + abs(i - position.getI());
            if (positionCost < stateCost) {
                stateCost = positionCost;
            }
        }

        return stateCost;
    }

    private Collection<Position> getElementary(final Position position, final int z) {
        final Set<Position> positions = new HashSet<>();
        final int j = getJ(z, n - position.getE());

        if (j == 0) {
            positions.add(new Position(position.getI() + 1, position.getE()));
        } else {
            if (position.getE() < n) {
                positions.add(new Position(position.getI(), position.getE() + 1));
                positions.add(new Position(position.getI() + 1, position.getE() + 1));
            }
            if (j > 0) {
                positions.add(new Position(position.getI() + j + 1, position.getE() + j));
            }
        }

        return positions;
    }

    private int getJ(int z, int k) {
        for (int i = k; i >= 0; i--) {
            if ((z & (1 << i)) != 0) {
                return k - i;
            }
        }

        return -1;
    }
}
