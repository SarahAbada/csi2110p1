
    class Cluster<E>{
        Sequence<E> sequence;
        Node<E> leader;
        String id;

        Cluster(Node<E> leader, String id) {
            this.leader = leader;
            this.sequence = new Sequence<>();
            this.sequence.add(leader);
            this.id = id;
        }
    }
