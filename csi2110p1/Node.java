package csi2110p1;

public class Node<E> {
        E element;
        // reference to the sequence storing it
        Cluster<E> cluster;
        // previous and next references
        Node<E> prev, next;
        // coordinates on the grid
        int x, y;
        Node(E element, Cluster<E> cluster, int x, int y) {
            this.element = element;
            this.cluster = cluster;
            this.prev = null;
            this.next = null;
            this.x = x;
            this.y = y;
        }
        Node(E element, Cluster<E> cluster, Node<E> prev, Node<E> next, int x, int y) {
            this.element = element;
            this.cluster = cluster;
            this.prev = prev;
            this.next = next;
            this.x = x;
            this.y = y;
        }
    }