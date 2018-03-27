package org.nnc.sequences.fuzzy;

public class FuzzyMatch<V> {
    private final V value;
    private final int cost;

    public FuzzyMatch(V value, int cost) {
        this.value = value;
        this.cost = cost;
    }

    public V getValue() {
        return value;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "{" + cost + ":" + value + "}";
    }
}
