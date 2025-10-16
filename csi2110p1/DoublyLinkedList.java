package csi2110p1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements Iterable<Node<E>> {
    Node<E> head;
    Node<E> tail;
    int size;
    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public Node<E> getFirst() {
        return head;
    }
    public Node<E> getLast() {
        return tail;
    }
    public void add(Node<E> newNode) {
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }
    public void remove(Node<E> node) {
        if (node == head) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            }
        } else {
            node.prev.next = node.next;
            if (node.next != null) {
                node.next.prev = node.prev;
            }
        }
        size--;
        if (isEmpty()) {
            tail = null;
        }
    }
    @Override
    public Iterator<Node<E>> iterator() {
        return new Iterator<Node<E>>() {
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Node<E> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements");
                }
                Node<E> temp = current;
                current = current.next;
                return temp;
            }
        };
    }
}
