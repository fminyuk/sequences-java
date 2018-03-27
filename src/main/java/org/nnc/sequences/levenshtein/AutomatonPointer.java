package org.nnc.sequences.levenshtein;

/**
 * Состояние (позиция) в детерминированном автомате Левенштейна.
 */
public class AutomatonPointer {
    private final int state;
    private final int position;

    public AutomatonPointer(int state, int position) {
        this.state = state;
        this.position = position;
    }

    public int getState() {
        return state;
    }

    public int getPosition() {
        return position;
    }
}
