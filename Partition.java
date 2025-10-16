//package csi2110p1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Partition<E> {


    public LinkedList<Cluster<E>> clusters; // list of parts in the partition

    public Partition() {
        this.clusters = new LinkedList<>();
        
    }

    public Node<E> makeCluster(E x) {
        Cluster<E> newCluster = new Cluster<E>(new Node<E>(x, null, 0, 0), "unidentified");
        newCluster.sequence.getFirst().cluster = newCluster; // set the cluster reference
        clusters.add(newCluster);
        return newCluster.sequence.getFirst();
    }

    public Node<E> makeCluster(E x, String id, int xCoord, int yCoord) {
        Cluster<E> newCluster = new Cluster<E>(new Node<E>(x, null, xCoord, yCoord), id);
        newCluster.sequence.getFirst().cluster = newCluster; // set the cluster reference
        clusters.add(newCluster);
        return newCluster.sequence.getFirst();
    }

    public Cluster<E> getCluster(Node<E> p) {
        return p.cluster;
    }

    public void union(Node<E> p, Node<E> q){
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
            for(Node<E> pos : Q.sequence){
                pos.cluster = P; // update cluster reference
                P.sequence.add(pos);
            }
            clusters.remove(Q); // remove the merged cluster
        }
    }

    public Node<E> find(Node<E> v) {
        return v.cluster.leader;
    }

    public E element(Node<E> p){
        return p.element;
    }

    public int numberOfClusters(){
        return clusters.size();
    }

    public int clusterSize(Node<E> p){
        return getCluster(p).sequence.size();
    }

    public ArrayList<Node<E>> clusterNodes(Node<E> p){
        Cluster<E> cluster = getCluster(p);
        ArrayList<Node<E>> Nodes = new ArrayList<>();
        for(Node<E> pos : cluster.sequence){
            Nodes.add(pos);
        }
        return Nodes;
    }

    public ArrayList<Integer> clusterSizes(){
        ArrayList<Integer> sizes = new ArrayList<>();
        for(Cluster<E> cluster : clusters){
            sizes.add(cluster.sequence.size());
        }
        Collections.sort(sizes, Collections.reverseOrder());
        return sizes;
    }


}