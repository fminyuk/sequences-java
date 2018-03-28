package org.nnc.sequences;

import java.util.Map;

/**
 * Интерфейс дерева ключей (Trie) допускающего модификацию.
 *
 * @param <T> Тип узла дерева.
 * @param <E> Тип символов.
 * @param <V> Тип значения.
 */
public interface TrieMutable<T extends TrieMutable<T, E, V>, E, V> extends Trie<E, V> {
    /**
     * Возвращает дочерние узлы.
     *
     * @return Дочерние узлы.
     */
    @Override
    Map<E, T> getChildren();

    /**
     * Устанавливает значение ключа.
     *
     * @param value Значение ключа.
     */
    void setValue(V value);

    /**
     * Устанавливает дочерний узел.
     *
     * @param c Символ ключа.
     * @param child Дочерний узел.
     */
    default void setChild(E c, T child){
        getChildren().put(c, child);
    }
}
