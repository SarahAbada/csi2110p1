package csi2110p1;

public class TestPartition {
    public static void main(String[] args) {
        Partition<Integer> partition = new Partition<>();

        // Make singleton clusters
        Node<Integer> a = partition.makeCluster(10);
        Node<Integer> b = partition.makeCluster(20);
        Node<Integer> c = partition.makeCluster(30);
        Node<Integer> d = partition.makeCluster(40);

        System.out.println("Element at Node a: " + partition.element(a));
        System.out.println("Leader of a: " + partition.find(a).element);
        System.out.println("Leader of b: " + partition.find(b).element);
        System.out.println("Number of clusters: " + partition.numberOfClusters());

        partition.union(a, b);
        System.out.println("\nAfter union(a, b):");
        System.out.println("Leader of a: " + partition.find(a).element);
        System.out.println("Leader of b: " + partition.find(b).element);
        System.out.println("Number of clusters: " + partition.numberOfClusters());

        partition.union(b, c);
        System.out.println("\nAfter union(b, c):");
        System.out.println("Leader of c: " + partition.find(c).element);
        System.out.println("Cluster size for a: " + partition.clusterSize(a));

        System.out.print("Nodes in a's cluster: ");
        for (Node<Integer> pos : partition.clusterNodes(a)) {
            System.out.print(partition.element(pos) + " ");
        }
        System.out.println();

        partition.union(d, a);
        System.out.println("\nAfter union(d, a):");
        System.out.println("Leader of d: " + partition.find(d).element);
        System.out.println("Number of clusters: " + partition.numberOfClusters());

        System.out.println("Cluster sizes: " + partition.clusterSizes());

        System.out.print("Nodes in d's cluster: ");
        for (Node<Integer> pos : partition.clusterNodes(d)) {
            System.out.print(partition.element(pos) + " ");
        }
        System.out.println();
    }
}