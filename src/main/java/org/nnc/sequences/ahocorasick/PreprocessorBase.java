package org.nnc.sequences.ahocorasick;

import org.nnc.sequences.Sequence;
import org.nnc.sequences.TrieFactoryBase;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Строит дерево ключей для алгоритма Ахо-корасика.
 *
 * @param <T> Тип узла.
 * @param <E> Тип элемента ключа.
 * @param <V> Тип значения ключа.
 */
public abstract class PreprocessorBase<T extends NodeMutable<T, E, V>, E, V> extends TrieFactoryBase<T, E, V> {
    protected T createNode(final List<Sequence<E, V>> sequences) {
        // Построение дерева ключей.
        final T root = createTrie(sequences);

        // Вычисление функций неудач и выхода.
        final Queue<T> queue = new ArrayDeque<>();
        root.setNeg(root);
        for (final Map.Entry<E, T> child : root.getChildren().entrySet()) {
            child.getValue().setNeg(root);
            queue.add(child.getValue());
        }

        while (!queue.isEmpty()) {
            final T node = queue.poll();

            for (final Map.Entry<E, T> child : node.getChildren().entrySet()) {
                final E x = child.getKey();
                final T v = child.getValue();
                T w = node.getNeg();

                while (w != root && !w.getChildren().containsKey(x)) {
                    w = w.getNeg();
                }

                if (w.getChildren().containsKey(x)) {
                    final T ws = w.getChildren().get(x);
                    v.setNeg(ws);
                    v.setOut(ws.getValue() != null ? ws : ws.getOut());
                } else {
                    v.setNeg(root);
                }

                queue.add(v);
            }
        }

        return root;
    }
}
