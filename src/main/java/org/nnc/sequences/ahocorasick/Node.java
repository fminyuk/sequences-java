package org.nnc.sequences.ahocorasick;

import org.nnc.sequences.Trie;

import java.util.Map;

/**
 * Дерево ключей (Trie) с функциями выхода и неудач.
 *
 * @param <E> Тип элемента ключа.
 * @param <V> Тип значения ключа.
 */
public interface Node<E, V> extends Trie<E, V> {
    @Override
    Map<E, ? extends Node<E, V>> getChildren();

    Node<E, V> getNeg();

    Node<E, V> getOut();
}
