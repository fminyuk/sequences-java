package org.nnc.sequences.levenshtein;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

class FuzzyTestUtils {
    private FuzzyTestUtils() {
    }

    public static State state(int i, final Position... positions) {
        return State.create(i, new HashSet<>(Arrays.asList(positions)));
    }

    public static State state(int i, final Collection<Position> positions) {
        return State.create(i, positions);
    }

    public static Position position(int i, int e) {
        return new Position(i, e);
    }
}
