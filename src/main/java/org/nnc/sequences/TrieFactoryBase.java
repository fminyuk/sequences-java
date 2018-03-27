package org.nnc.sequences;

import java.util.List;

public abstract class TrieFactoryBase<T extends TrieMutable<T, E, V>, E, V> {
    public T createTrie(final List<Sequence<E, V>> sequences) {
        final T root = createNode();
        for (final Sequence<E, V> sequence : sequences) {
            AddSequence(root, sequence);
        }

        return root;
    }

    protected abstract T createNode();

    private void AddSequence(T node, final Sequence<E, V> sequence) {
        for (final E c : sequence.getChars()) {
            T next = node.getChildren().get(c);
            if (next == null) {
                next = createNode();
                node.setChild(c, next);
            }

            node = next;
        }

        node.setValue(sequence.getValue());
    }
}
