package org.nnc.sequences.levenshtein;

/**
 * Таблицы для детерминированного автомата Левенштейна, универсальные для заданного n.
 */
public class UniTables {
    private final int n;
    private final int start;
    private final int stop;
    private final int[][] transitions;
    private final int[][] cost;

    public UniTables(int n, int start, int stop, int[][] transitions, int[][] cost) {
        this.n = n;
        this.start = start;
        this.stop = stop;
        this.transitions = transitions;
        this.cost = cost;
    }

    /**
     * Возвращает максимально допустимое редакционное расстояние.
     *
     * @return Максимально допустимое редакционное расстояние.
     */
    public int getN() {
        return n;
    }

    /**
     * Возвращает индекс стартового состояния.
     *
     * @return Индекс стартового состояния
     */
    public int getStart() {
        return start;
    }

    /**
     * Возвращает индекс состояния остановки.
     *
     * @return Индекс состояния остановки.
     */
    public int getStop() {
        return stop;
    }

    /**
     * Возвращает таблицу стоимости состояний.
     *
     * @return Таблица стоимости состояний.
     */
    public int[][] getCost() {
        return cost;
    }

    /**
     * Вовзращает таблицу переходов.
     *
     * @return Таблица переходов.
     */
    public int[][] getTransitions() {
        return transitions;
    }
}
