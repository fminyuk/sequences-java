package org.nnc.sequences.ahocorasick;

import org.nnc.sequences.Sequence;
import org.nnc.sequences.SequenceFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UniversalTest {
    @Test(description = "Пустое множество ключей.")
    public void emptySet() {
        final String[] keys = new String[0];
        final Automaton<Character, String> automaton = createAutomaton(keys);

        final AtomicBoolean success = new AtomicBoolean(false);
        automaton.Search('a', s -> success.set(true));
        assertFalse(success.get());
    }

    @Test(description = "Пустой ключ.")
    public void emptyKey() {
        final String[] keys = {""};
        final Automaton<Character, String> automaton = createAutomaton(keys);

        final AtomicBoolean success = new AtomicBoolean(false);
        automaton.Search('a', s -> success.set(true));
        assertFalse(success.get());
    }

    @DataProvider
    public Object[][] oneCharDataProvider() {
        return new Object[][]{
                {"a", "a"},
                {"a", "abbb"},
                {"a", "bacd"},
                {"a", "bbba"},
                {"aab", "aab"},
                {"aab", "aabbb"},
                {"aab", "bbaabc"},
                {"aab", "bbbaab"},
        };
    }

    @Test(description = "Один ключ.", dataProvider = "oneCharDataProvider")
    public void one(String key, String text) {
        final String[] keys = {key};
        final Automaton<Character, String> automaton = createAutomaton(keys);

        final AtomicBoolean success = new AtomicBoolean(false);
        for (final char c : text.toCharArray()) {
            automaton.Search(c, s -> success.set(true));
        }
        assertTrue(success.get());
    }

    @Test(description = "Множество ключей без вхождений.")
    public void manyDifferent() {
        final String text = "daabcdaaca";
        final String[] keys = {
                "aab",
                "bcda",
                "daac",
        };
        final Automaton<Character, String> automaton = createAutomaton(keys);

        final Map<String, Boolean> success = new HashMap<>();
        for (final char c : text.toCharArray()) {
            automaton.Search(c, s -> success.put(s, true));
        }
        assertTrue(Arrays.stream(keys).allMatch(success::containsKey));
    }

    @Test(description = "Множество ключей с вхождениями.")
    public void manyIncludes() {
        final String text = "cbaab";
        final String[] keys = {
                "b",
                "ab",
                "aab",
                "baab",
                "cbaab",
                "cbaa",
                "c",
        };
        final Automaton<Character, String> automaton = createAutomaton(keys);

        final Map<String, Boolean> success = new HashMap<>();
        for (final char c : text.toCharArray()) {
            automaton.Search(c, s -> success.put(s, true));
        }
        assertTrue(Arrays.stream(keys).allMatch(success::containsKey));
    }

    private static Automaton<Character, String> createAutomaton(final String[] keys) {
        final List<Sequence<Character, String>> sequences = Arrays.stream(keys)
                .map(SequenceFactory::create)
                .collect(Collectors.toList());

        return new Automaton<>(Preprocessor.create(sequences));
    }
}
