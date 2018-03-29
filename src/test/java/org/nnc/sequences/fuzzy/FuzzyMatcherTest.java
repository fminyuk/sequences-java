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
        final Automaton<Character> levenshtein = levenshtein("abca", 2);

        final FuzzyMatcher<Character, String> matcher = new FuzzyMatcher<>();

        final List<FuzzyMatch<String>> matches = matcher.search(trie, levenshtein);
    }

    @Test
    public void lt() throws Exception {
        final List<String> words = readWords();
        final Trie<Character, String> trie = trie(words);

        final FuzzyMatcher<Character, String> matcher = new FuzzyMatcher<>();
        final Automaton<Character> levenshtein = levenshtein("проффесор", 2);

        final StopWatch stopWatch = StopWatch.createStarted();
        final List<FuzzyMatch<String>> matches = matcher.search(trie, levenshtein);
        stopWatch.stop();

        System.out.println("Time:    " + stopWatch.getNanoTime());
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
