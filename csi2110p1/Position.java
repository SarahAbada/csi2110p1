    package csi2110p1;
    public class Position<E> {
        E element;
        // reference to the sequence storing it
        Cluster<E> cluster;
        // previous and next references
        Position<E> prev, next;
        Position(E element, Cluster<E> cluster) {
            this.element = element;
            this.cluster = cluster;
            this.prev = null;
            this.next = null;
        }
        Position(E element, Cluster<E> cluster, Position<E> prev, Position<E> next) {
            this.element = element;
            this.cluster = cluster;
            this.prev = prev;
            this.next = next;
        }
    }