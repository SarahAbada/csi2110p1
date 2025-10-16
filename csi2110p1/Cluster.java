package csi2110p1;

    class Cluster<E>{
        DoublyLinkedList<E> sequence;
        Node<E> leader;
        String id;

        Cluster(Node<E> leader, String id) {
            this.leader = leader;
            this.sequence = new DoublyLinkedList<>();
            this.sequence.add(leader);
            this.id = id;
        }
    }
