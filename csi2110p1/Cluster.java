package csi2110p1;

    class Cluster<E>{
        DoublyLinkedList<E> sequence;
        Position<E> leader;
        Cluster(Position<E> leader){
            this.leader = leader;
            this.sequence = new DoublyLinkedList<>();
            this.sequence.add(leader);
        }
    }
