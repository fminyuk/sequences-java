package org.nnc.sequences.fuzzy;

import org.nnc.sequences.Sequence;
import org.nnc.sequences.SequenceFactory;
import org.nnc.sequences.Trie;
import org.nnc.sequences.TrieFactory;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

public class LinearTrieFactoryTest {
    private final LinearTrieFactory<Character, String> factory = new LinearTrieFactory<>();

    @Test(description = "Пустое множество ключей.")
    public void emptySet() {
        final String[] keys = new String[0];
        final LinearTrie<Character, String> linear = factory.create(createTrie(keys));

        assertEquals(linear.getChars().size(), 1);
        assertEquals(linear.getValues().size(), 1);
        assertEquals(linear.getLens().length, 1);
        assertEquals(linear.getChars().get(0), null);
        assertEquals(linear.getValues().get(0), null);
        assertEquals(linear.getLens()[0], 0);
    }

    @Test(description = "Пустой ключ.")
    public void emptyKey() {
        final String[] keys = {""};
        final LinearTrie<Character, String> linear = factory.create(createTrie(keys));

        assertEquals(linear.getChars().size(), 1);
        assertEquals(linear.getValues().size(), 1);
        assertEquals(linear.getLens().length, 1);
        assertEquals(linear.getChars().get(0), null);
        assertEquals(linear.getValues().get(0), "");
        assertEquals(linear.getLens()[0], 0);
    }

    @Test(description = "Ключ из одного символа.")
    public void oneChar() {
        final String[] keys = {"a"};

        final LinearTrie<Character, String> linear = factory.create(createTrie(keys));

        assertEquals(linear.getChars().size(), 2);
        assertEquals(linear.getValues().size(), 2);
        assertEquals(linear.getLens().length, 2);
        assertEquals(linear.getChars().get(0), null);
        assertEquals(linear.getChars().get(1), Character.valueOf(keys[0].charAt(0)));
        assertEquals(linear.getValues().get(0), null);
        assertEquals(linear.getValues().get(1), keys[0]);
        assertEquals(linear.getLens()[0], 1);
        assertEquals(linear.getLens()[1], 0);
    }

    @Test(description = "Несколько ключей.")
    public void manyKeys() {
        final String[] keys = {
                "ab",
                "acc"
        };

        final LinearTrie<Character, String> linear = factory.create(createTrie(keys));

        assertEquals(linear.getChars().size(), 5);
        assertEquals(linear.getValues().size(), 5);
        assertEquals(linear.getLens().length, 5);
        assertEquals(linear.getLens()[0], 4);
        assertEquals(linear.getChars().get(0), null);
        assertEquals(linear.getValues().get(0), null);
    }

    private static Trie<Character, String> createTrie(final String[] keys) {
        final List<Sequence<Character, String>> sequences = Arrays.stream(keys)
                .map(SequenceFactory::create)
                .collect(Collectors.toList());

        return TrieFactory.create(sequences);
    }
}
