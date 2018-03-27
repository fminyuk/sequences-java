package org.nnc.sequences.levenshtein;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class State {
    private final int i;
    private final List<Position> positions;

    private State(int i, List<Position> positions) {
        this.i = i;
        this.positions = positions;
    }

    public int getI() {
        return i;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public State addI(int d) {
        final int i = this.i + d;
        final List<Position> positions = this.positions.stream()
                .map(p -> new Position(p.getI() + d, p.getE()))
                .collect(Collectors.toList());

        return new State(i, positions);
    }

    @Override
    public String toString() {
        return i + ":{" + positions.stream().map(Position::toString).collect(Collectors.joining(",")) + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return i == state.i && Objects.equals(positions, state.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, positions);
    }

    public static State create(int i, final Collection<Position> positions) {
        final Position base = new Position(i, 0);
        final Stream<Position> filtered = positions.stream().filter(p -> base.isSubsumes(p) || base.equals(p));
        final Stream<Position> sorted = filtered.sorted((a, b) -> {
            final int de = a.getE() - b.getE();
            if (de != 0) {
                return de;
            }

            return a.getI() - b.getI();
        });

        final List<Position> result = new ArrayList<>();
        sorted.forEachOrdered(position -> {
            if (result.stream().noneMatch(p -> p.isSubsumes(position))) {
                result.add(position);
            }
        });

        return new State(i, result);
    }
}
