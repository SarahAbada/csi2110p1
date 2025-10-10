package csi2110p1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements Iterable<Position<E>> {
    Position<E> head;
    Position<E> tail;
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
    public Position<E> getFirst() {
        return head;
    }
    public Position<E> getLast() {
        return tail;
    }
    public void add(Position<E> newNode) {
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
    public void remove(Position<E> node) {
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
    public Iterator<Position<E>> iterator() {
        return new Iterator<Position<E>>() {
            private Position<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Position<E> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements");
                }
                Position<E> temp = current;
                current = current.next;
                return temp;
            }
        };
    }
}
