package org.nnc.sequences.fuzzy;

import org.nnc.sequences.SequenceFactory;
import org.nnc.sequences.Trie;
import org.nnc.sequences.TrieFactory;
import org.nnc.sequences.levenshtein.Automaton;
import org.nnc.sequences.levenshtein.UniTablesFactoryImpl;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ArrayUtils.toObject;

public class FuzzyMatcherTest {
    @Test
    public void test() {
        final Trie<Character, String> trie = trie(Arrays.asList(
                "abbba",
                "abcde",
                "abcg",
                "defg"
        ));
        final Automaton<Character> levenshtein = levenshtein("abca", 2);

        final FuzzyMatcher<Character, String> matcher = new FuzzyMatcher<>();

        final List<FuzzyMatch<String>> matches = matcher.search(trie, levenshtein);
    }

    private static Trie<Character, String> trie(final List<String> keys) {
        return TrieFactory.create(keys.stream().map(SequenceFactory::create).collect(Collectors.toList()));
    }

    private static Automaton<Character> levenshtein(final String string, final int n) {
        return new Automaton<>(toObject(string.toCharArray()), n, new UniTablesFactoryImpl());
    }
}
