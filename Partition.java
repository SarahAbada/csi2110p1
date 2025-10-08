import java.util.LinkedList;

public class Partition<E> {

    class Cluster{
        LinkedList<Position> sequence;
        Position leader;
        Cluster(Position leader){
            this.leader = leader;
            this.sequence = new LinkedList<>();
            this.sequence.add(leader);
        }
    }

    class Position {
        E element;
        // reference to the sequence storing it
        Cluster cluster;
        Position(E element, Cluster cluster) {
            this.element = element;
            this.cluster = cluster;
        }
    }
    public LinkedList<Cluster> clusters; // list of parts in the partition

    public Partition() {
        this.clusters = new LinkedList<>();
        
    }

    public Position makeCluster(E x) {
        Cluster newCluster = new Cluster(new Position(x, null));
        newCluster.sequence.getFirst().cluster = newCluster; // set the cluster reference
        clusters.add(newCluster);
        return newCluster.sequence.getFirst();
    }

    public Cluster getCluster(Position p) {
        return p.cluster;
    }

    public void union(Position p, Position q){
        Cluster P = getCluster(p);
        Cluster Q = getCluster(q);
        if(P != Q){
            // merge smaller cluster into larger one
            if(P.sequence.size() < Q.sequence.size()){
                Cluster temp = P;
                P = Q;
                Q = temp;
            }
            // now P is guaranteed to be the larger cluster
            for(Position pos : Q.sequence){
                pos.cluster = P; // update cluster reference
                P.sequence.add(pos);
            }
            clusters.remove(Q); // remove the merged cluster
        }
    }

    public Position find(Position v) {
        return v.cluster.leader;
    }
public static void main(String[] args) {
    Partition<Integer> partition = new Partition<>();

    Partition<Integer>.Position a = partition.makeCluster(7);
    Partition<Integer>.Position b = partition.makeCluster(8);
    Partition<Integer>.Position c = partition.makeCluster(9);

    // Each should be its own leader
    System.out.println(partition.find(a).element); // Should print 7
    System.out.println(partition.find(b).element); // Should print 8

    partition.union(a, b);
    // Now a and b should have the same leader
    System.out.println(partition.find(a).element); // Should print 7 or 8
    System.out.println(partition.find(b).element); // Should print same as above

    partition.union(a, c);
    // Now all should have the same leader
    System.out.println(partition.find(a).element); // Should print 7 or 8 or 9
    System.out.println(partition.find(b).element); // Should print same as above
    System.out.println(partition.find(c).element); // Should print same as above
}

 

}