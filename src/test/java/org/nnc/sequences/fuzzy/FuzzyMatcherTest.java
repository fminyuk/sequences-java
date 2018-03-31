package org.nnc.sequences.fuzzy;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.nnc.sequences.SequenceFactory;
import org.nnc.sequences.Trie;
import org.nnc.sequences.TrieFactory;
import org.nnc.sequences.levenshtein.Automaton;
import org.nnc.sequences.levenshtein.UniTablesFactoryImpl;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        final Automaton<Character> pattern = levenshtein("abca", 2);

        final FuzzyMatcher<Character, String> matcher = new FuzzyMatcher<>(trie);

        final List<FuzzyMatch<String>> matches = matcher.search(pattern);
    }

    @Test(enabled = false)
    public void lt() throws Exception {
        final List<String> words = readWords();
        final Automaton<Character> pattern = levenshtein("проффесор", 2);

        final Trie<Character, String> trie = trie(words);
        final FuzzyMatcher<Character, String> matcher = new FuzzyMatcher<>(trie);

        final StopWatch trieStopWatch = StopWatch.createStarted();
        final List<FuzzyMatch<String>> tmatches = matcher.search(pattern);
        trieStopWatch.stop();

        System.out.println("Trie:   " + trieStopWatch.getNanoTime());

        final LinearTrieFactory<Character, String> linearTrieFactory = new LinearTrieFactory<>();
        final LinearTrie<Character, String> linear = linearTrieFactory.create(trie);
        final LinearFuzzyMatcher<Character, String> lmatcher = new LinearFuzzyMatcher<>(linear);

        final StopWatch linearStopWatch = StopWatch.createStarted();
        final List<FuzzyMatch<String>> lmatches = lmatcher.search(pattern);
        linearStopWatch.stop();

        System.out.println("Linear: " + linearStopWatch.getNanoTime());
    }

    private static List<String> readWords() throws IOException {
        final String filename = FuzzyMatcherTest.class.getClassLoader().getResource("words.txt").getFile();
        return Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
    }

    private static Trie<Character, String> trie(final List<String> keys) {
        return TrieFactory.create(keys.stream().map(SequenceFactory::create).collect(Collectors.toList()));
    }

    private static Automaton<Character> levenshtein(final String string, final int n) {
        return new Automaton<>(toObject(string.toCharArray()), n, new UniTablesFactoryImpl());
    }
}
