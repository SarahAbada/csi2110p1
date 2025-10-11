package csi2110p1;

    class Cluster<E>{
        DoublyLinkedList<E> sequence;
        Position<E> leader;
        String id;

        Cluster(Position<E> leader, String id) {
            this.leader = leader;
            this.sequence = new DoublyLinkedList<>();
            this.sequence.add(leader);
            this.id = id;
        }
    }
