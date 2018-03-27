package org.nnc.sequences.ahocorasick;

import org.nnc.sequences.Sequence;
import org.nnc.sequences.SequenceFactory;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class PreprocessorTest {
    @Test(description = "Пустое множество ключей.")
    public void emptySet() {
        final String[] keys = new String[0];
        final NodeImpl<Character, String> root = createNode (keys);

        assertNotNull(root);
        assertEquals(0, root.getChildren().size());
        assertEquals(root, root.getNeg());
        assertNull(root.getValue());
        assertNull(root.getOut());
    }

    @Test(description = "Пустой ключ.")
    public void emptyKey() {
        final String[] keys = {""};
        final NodeImpl<Character, String> root = createNode (keys);

        assertNotNull(root);
        assertEquals(0, root.getChildren().size());
        assertEquals(root, root.getNeg());
        assertEquals("", root.getValue());
        assertNull(root.getOut());
    }

    @Test(description = "Ключ из одного символа.")
    public void oneChar() {
        final String key = "a";
        final String[] keys = {key};
        final NodeImpl<Character, String> root = createNode (keys);

        assertNotNull(root);
        assertEquals(1, root.getChildren().size());
        assertEquals(root, root.getNeg());
        assertNull(root.getValue());
        assertNull(root.getOut());

        final NodeImpl<Character, String> node = root.getChildren().get(key.charAt(0));
        assertNotNull(node);
        assertEquals(0, node.getChildren().size());
        assertEquals(root, node.getNeg());
        assertNull(node.getOut());
        assertEquals(key, node.getValue());

    }

    @Test(description = "Один сложный ключ.")
    public void oneComplex() {
        final String key = "aabaac";
        final String[] keys = {key};
        final NodeImpl<Character, String> root = createNode (keys);

        final List<NodeImpl<Character, String>> nodes = new ArrayList<>();
        nodes.add(root);
        for (int i = 0; i < key.length(); i++) {
            nodes.add(nodes.get(i).getChildren().get(key.charAt(i)));
        }

        // Функции выхода.
        assertTrue(nodes.stream().allMatch(n -> n.getOut() == null));

        // Функции неудач.
        assertEquals(nodes.get(0), nodes.get(1).getNeg());
        assertEquals(nodes.get(1), nodes.get(2).getNeg());
        assertEquals(nodes.get(0), nodes.get(3).getNeg());
        assertEquals(nodes.get(1), nodes.get(4).getNeg());
        assertEquals(nodes.get(2), nodes.get(5).getNeg());
        assertEquals(nodes.get(0), nodes.get(6).getNeg());

        // Значения.
        assertTrue(nodes.stream().limit(nodes.size() - 1).allMatch(n -> n.getValue() == null));
        assertEquals(key, nodes.get(nodes.size() - 1).getValue());
    }

    @Test(description = "Несколько ключей (без ключей-подстрок).")
    public void many() {
        final String[] keys = {
                "abc",
                "edfabe",
                "bedfedfabg"
        };
        final NodeImpl<Character, String> root = createNode (keys);

        // Функции выхода.
        assertTrue(traverse(root).stream().allMatch(n -> n.getOut() == null));

        // Функции неудач.
        assertEquals(getNode(root, "ab"), getNode(root, "edfab").getNeg());
        assertEquals(getNode(root, "edfab"), getNode(root, "bedfedfab").getNeg());
        assertEquals(getNode(root, "edf"), getNode(root, "bedf").getNeg());
    }

    @Test(description = "Несколько ключей (с ключами-подстроками).")
    public void manySubkeys() {
        final String[] keys = {
                "abc",
                "eabc",
                "heabc"
        };
        final NodeImpl<Character, String> root = createNode (keys);

        // Функции выхода.
        assertEquals(getNode(root, "abc"), getNode(root, "eabc").getOut());
        assertEquals(getNode(root, "eabc"), getNode(root, "heabc").getOut());
    }

    private static NodeImpl<Character, String> getNode(NodeImpl<Character, String> root, final String prefix) {
        for (final char c : prefix.toCharArray()) {
            root = root.getChildren().get(c);
        }

        return root;
    }

    private static List<NodeImpl<Character, String>> traverse(NodeImpl<Character, String> root) {
        final List<NodeImpl<Character, String>> list = new ArrayList<>();

        final Deque<NodeImpl<Character, String>> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            final NodeImpl<Character, String> current = stack.pop();
            list.add(current);
            for (final NodeImpl<Character, String> child : current.getChildren().values()) {
                stack.push(child);
            }
        }

        return list;
    }

    private static NodeImpl<Character, String> createNode(final String[] keys) {
        final List<Sequence<Character, String>> sequences = Arrays.stream(keys)
                .map(SequenceFactory::create)
                .collect(Collectors.toList());

        return (NodeImpl<Character, String>) Preprocessor.create(sequences);
    }
}
