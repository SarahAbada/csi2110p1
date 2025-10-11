    package csi2110p1;
    public class Position<E> {
        E element;
        // reference to the sequence storing it
        Cluster<E> cluster;
        // previous and next references
        Position<E> prev, next;
        // coordinates on the grid
        int x, y;
        Position(E element, Cluster<E> cluster, int x, int y) {
            this.element = element;
            this.cluster = cluster;
            this.prev = null;
            this.next = null;
            this.x = x;
            this.y = y;
        }
        Position(E element, Cluster<E> cluster, Position<E> prev, Position<E> next, int x, int y) {
            this.element = element;
            this.cluster = cluster;
            this.prev = prev;
            this.next = next;
            this.x = x;
            this.y = y;
        }
    }