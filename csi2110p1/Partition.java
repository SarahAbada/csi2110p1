package csi2110p1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Partition<E> {


    public LinkedList<Cluster<E>> clusters; // list of parts in the partition

    public Partition() {
        this.clusters = new LinkedList<>();
        
    }

    public Position<E> makeCluster(E x) {
        Cluster<E> newCluster = new Cluster(new Position(x, null));
        newCluster.sequence.getFirst().cluster = newCluster; // set the cluster reference
        clusters.add(newCluster);
        return newCluster.sequence.getFirst();
    }

    public Cluster<E> getCluster(Position<E> p) {
        return p.cluster;
    }

    public void union(Position<E> p, Position<E> q){
        Cluster<E> P = getCluster(p);
        Cluster<E> Q = getCluster(q);
        if(P != Q){
            // merge smaller cluster into larger one
            if(P.sequence.size() < Q.sequence.size()){
                Cluster<E> temp = P;
                P = Q;
                Q = temp;
            }
            // now P is guaranteed to be the larger cluster
            for(Position<E> pos : Q.sequence){
                pos.cluster = P; // update cluster reference
                P.sequence.add(pos);
            }
            clusters.remove(Q); // remove the merged cluster
        }
    }

    public Position<E> find(Position<E> v) {
        return v.cluster.leader;
    }

    public E element(Position<E> p){
        return p.element;
    }

    public int numberOfClusters(){
        return clusters.size();
    }

    public int clusterSize(Position<E> p){
        return getCluster(p).sequence.size();
    }

    public ArrayList<Position<E>> clusterPositions(Position<E> p){
        Cluster<E> cluster = getCluster(p);
        ArrayList<Position<E>> positions = new ArrayList<>();
        for(Position<E> pos : cluster.sequence){
            positions.add(pos);
        }
        return positions;
    }

    public ArrayList<Integer> clusterSizes(){
        ArrayList<Integer> sizes = new ArrayList<>();
        for(Cluster<E> cluster : clusters){
            sizes.add(cluster.sequence.size());
        }
        Collections.sort(sizes, Collections.reverseOrder());
        return sizes;
    }

public static void main(String[] args) {
    Partition<Integer> partition = new Partition<>();

    // Create singleton clusters
    Partition<Integer>.Position a = partition.makeCluster(10);
    Partition<Integer>.Position b = partition.makeCluster(20);
    Partition<Integer>.Position c = partition.makeCluster(30);
    Partition<Integer>.Position d = partition.makeCluster(40);

    // Test element() and initial leaders
    System.out.println("Element at position a: " + partition.element(a)); // 10
    System.out.println("Leader of a: " + partition.find(a).element); // 10
    System.out.println("Leader of b: " + partition.find(b).element); // 20

    // Test numberOfClusters()
    System.out.println("Number of clusters: " + partition.numberOfClusters()); // 4

    // Test union(a, b)
    partition.union(a, b);
    System.out.println("\nAfter union(a, b):");
    System.out.println("Leader of a: " + partition.find(a).element);
    System.out.println("Leader of b: " + partition.find(b).element);
    System.out.println("Number of clusters: " + partition.numberOfClusters()); // 3

    // Test union(b, c)
    partition.union(b, c);
    System.out.println("\nAfter union(b, c):");
    System.out.println("Leader of c: " + partition.find(c).element);
    System.out.println("Cluster size for a: " + partition.clusterSize(a)); // Should be 3

    // Test clusterPositions(a)
    System.out.print("Positions in a's cluster: ");
    for (Partition<Integer>.Position pos : partition.clusterPositions(a)) {
        System.out.print(partition.element(pos) + " ");
    }
    System.out.println();

    // Test union(d, a) (should merge the biggest with the smallest)
    partition.union(d, a);
    System.out.println("\nAfter union(d, a):");
    System.out.println("Leader of d: " + partition.find(d).element);
    System.out.println("Number of clusters: " + partition.numberOfClusters()); // 1

    // Test clusterSizes()
    System.out.println("Cluster sizes (should be [4]): " + partition.clusterSizes());

    // Test clusterPositions(d)
    System.out.print("Positions in d's cluster: ");
    for (Partition<Integer>.Position pos : partition.clusterPositions(d)) {
        System.out.print(partition.element(pos) + " ");
    }
    System.out.println();
}

}