package org.nnc.sequences.ahocorasick;

import org.apache.commons.lang3.time.StopWatch;
import org.nnc.sequences.Sequence;
import org.nnc.sequences.SequenceFactory;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class PerformanceTest {
    @Test
    public void test() {
        final Random random = new Random(12345);

        final String alphabet = "abcd";
        final String text = DataGenerator.genText(10000, alphabet.toCharArray(), random);
        final String[] keys = DataGenerator.genKeys(text, 10000, 10, random);

        final StopWatch naive = StopWatch.createStarted();
        for (int i = 0; i < keys.length; i++) {
            text.indexOf(keys[i]);
        }
        naive.stop();

        final StopWatch pre = StopWatch.createStarted();
        final Automaton<Character, String> automaton = createAutomaton(keys);
        pre.stop();

        final StopWatch aho = StopWatch.createStarted();
        final AtomicBoolean success = new AtomicBoolean(false);
        for (final char c : text.toCharArray()) {
            automaton.Search(c, s -> success.set(true));
        }
        aho.stop();

        System.out.println("Naive:    " + naive.getNanoTime());
        System.out.println("A-C Pre:  " + pre.getNanoTime());
        System.out.println("A-C Exec: " + aho.getNanoTime());
    }

    private static Automaton<Character, String> createAutomaton(final String[] keys) {
        final List<Sequence<Character, String>> sequences = Arrays.stream(keys)
                .map(SequenceFactory::create)
                .collect(Collectors.toList());

        return new Automaton<>(Preprocessor.create(sequences));
    }
}
