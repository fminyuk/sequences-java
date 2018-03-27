package org.nnc.sequences.ahocorasick;

import java.util.function.Consumer;

public class Automaton<E, V> {
    private final Node<E, V> root;
    private Node<E, V> cur;

    public Automaton(final Node<E, V> root) {
        this.root = root;
        Reset();
    }

    public void Reset() {
        cur = root;
    }

    public void Search(final E c, final Consumer<V> success) {
        boolean attempt = true;
        while (attempt) {
            final Node<E, V> next = cur.getChildren().get(c);
            if (next != null) {
                attempt = false;
                cur = next;
                if (cur.getValue() != null) {
                    success.accept(cur.getValue());
                }

                Node<E, V> seqNodeOut = cur.getOut();
                while (seqNodeOut != null) {
                    success.accept(seqNodeOut.getValue());
                    seqNodeOut = seqNodeOut.getOut();
                }
            } else {
                attempt = cur != root;
                cur = cur.getNeg();
            }
        }
    }
}
