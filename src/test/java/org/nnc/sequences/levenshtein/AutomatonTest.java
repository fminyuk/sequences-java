package org.nnc.sequences.levenshtein;

import org.testng.annotations.Test;

import static org.apache.commons.lang3.ArrayUtils.toObject;
import static org.testng.Assert.assertEquals;

public class AutomatonTest {
    @Test
    public void emptyN0() {
        final Automaton<Character> automaton = automaton("", 0);

        // Совпадение
        assertEquals(cost(automaton, ""), 0);

        // Один лишний символ
        assertEquals(cost(automaton, "a"), -1);
    }

    @Test
    public void emptyN1() {
        final Automaton<Character> automaton = automaton("", 1);

        // Совпадение
        assertEquals(cost(automaton, ""), 0);

        // Один лишний символ
        assertEquals(cost(automaton, "a"), 1);

        // Два лишних символа
        assertEquals(cost(automaton, "aa"), -1);
        assertEquals(cost(automaton, "ab"), -1);
    }

    @Test
    public void emptyN2() {
        final Automaton<Character> automaton = automaton("", 2);

        // Совпадение
        assertEquals(cost(automaton, ""), 0);

        // Один лишний символ
        assertEquals(cost(automaton, "a"), 1);

        // Два лишних символа
        assertEquals(cost(automaton, "aa"), 2);
        assertEquals(cost(automaton, "ab"), 2);

        // Три лишних символа
        assertEquals(cost(automaton, "aaa"), -1);
        assertEquals(cost(automaton, "abc"), -1);
    }

    @Test
    public void test() {
        final Automaton<Character> automaton = automaton("abcdefg", 2);

        assertEquals(cost(automaton, "abcdefg"), 0);

        // Удаление символа
        assertEquals(cost(automaton, "bcdefg"), 1);
        assertEquals(cost(automaton, "abcefg"), 1);
        assertEquals(cost(automaton, "abcdef"), 1);

        // Добавление символа
        assertEquals(cost(automaton, "zabcdefg"), 1);
        assertEquals(cost(automaton, "abczdefg"), 1);
        assertEquals(cost(automaton, "abcdefgz"), 1);

        // Перестановка
        assertEquals(cost(automaton, "bacdefg"), 2);
        assertEquals(cost(automaton, "abcedfg"), 2);
        assertEquals(cost(automaton, "abcdegf"), 2);

        // Ошибочные строки
        assertEquals(cost(automaton, "abfg"), -1);
        assertEquals(cost(automaton, "zzzz"), -1);
        assertEquals(cost(automaton, "abczzzefg"), -1);
    }

    private static Automaton<Character> automaton(final String string, final int n) {
        return new Automaton<>(toObject(string.toCharArray()), n, new UniTablesFactoryImpl());
    }

    private static int cost(Automaton<Character> automaton, final String string) {
        final Character[] items = toObject(string.toCharArray());

        AutomatonPointer pointer = automaton.start();
        for (final Character item : items) {
            pointer = automaton.next(pointer, item);
        }

        return automaton.cost(pointer);
    }
}
