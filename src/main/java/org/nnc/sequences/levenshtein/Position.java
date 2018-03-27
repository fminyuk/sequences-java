package org.nnc.sequences.levenshtein;

import java.util.Objects;

import static java.lang.Math.abs;

/**
 * Позиция - состояние недетерминированного автомата Левенштейна.
 */
public class Position {
    private final int i;
    private final int e;

    public Position(int i, int e) {
        this.i = i;
        this.e = e;
    }

    public int getI() {
        return i;
    }

    public int getE() {
        return e;
    }

    public boolean isSubsumes(final Position p) {
        return getE() < p.getE() && abs(getI() - p.getI()) <= p.getE() - getE();
    }

    @Override
    public String toString() {
        return i + "#" + e;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Position position = (Position) o;
        return i == position.i && e == position.e;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, e);
    }
}
