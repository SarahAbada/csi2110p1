package csi2110p1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class IslandLakeSurvey {
    public static void main(String[] args) {
        Scanner filename = new Scanner(System.in);
        System.out.println("Enter the filename: ");
        String fileName = filename.nextLine();
        filename.close();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            // make a 2d list of data of integers
            List<List<Integer>> data = new ArrayList<>();
            Partition<Integer> partition = new Partition<>();
            String firstline = scanner.nextLine(); // read the first line (dimensions)
            String[] dimensions = firstline.trim().split("\\s+");
            for (String dim : dimensions) {
                System.out.println(dim);
            }
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);
            Node<Integer>[][] Nodes = new Node[rows][cols];
            int numberOfLakes = 0;
            int areaOfLakes = 0;
            for (int i = 0; i < rows; i++){
                String line = scanner.nextLine().trim();
                String[] values = line.split(" ");
                List<Integer> row = new ArrayList<>();

                 
                if (line.contains(" ")) {
                    String[] tokens = line.trim().split("\\s+");
                    for(int j = 0; j < cols; j++){
                        row.add(Integer.parseInt(tokens[j]));
                    }
                } else {
                    // line is like "10001101", parse each character
                    if (line.length() < cols) {
                        // handle short/malformed line gracefully by padding zeros
                        for (int j = 0; j < cols; j++) {
                            if (j < line.length() && Character.isDigit(line.charAt(j))) {
                                row.add(Character.getNumericValue(line.charAt(j)));
                            } else {
                                row.add(0);
                            }
                        }
                    } else {
                        for (int j = 0; j < cols; j++) {
                            char c = line.charAt(j);
                            row.add(Character.isDigit(c) ? Character.getNumericValue(c) : 0);
                        }
                    }
                }


                
                data.add(row);
            }
            // create a cluster/Node for every cell in the 2d list
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int val = data.get(i).get(j);
                    // create a cluster for this Node
                    // if the value is 0, make a cluster with id "water"
                    // if the value is 1, make a cluster with id "land"
                    // if the value is anything else, make a cluster with id "unidentified"
                    String clusterId;
                    if (val == 0) {
                        clusterId = "water";
                    } else if (val == 1) {
                        clusterId = "land";
                    } else {
                        clusterId = "unidentified";
                    }
                    Node<Integer> pos = partition.makeCluster(val, clusterId, i, j);
                    Nodes[i][j] = pos;
                }
            }
            // union adjacent cells with the same value
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Node<Integer> curNode = Nodes[i][j];
                    int curValue = data.get(i).get(j);
                    if (i > 0 && data.get(i - 1).get(j).equals(curValue)) {
                        partition.union(curNode, Nodes[i - 1][j]);
                    }
                    if (j > 0 && data.get(i).get(j - 1).equals(curValue)) {
                        partition.union(curNode, Nodes[i][j - 1]);
                    }
                    
                }
                
            }
            //List<Node<Integer>[]> unionsToDo = new ArrayList<>();
            //Cluster<Integer> prev = null;
            //for(Cluster<Integer> cluster : partition.clusters){
            //    if(prev != null){
            //        if(partition.element(cluster.leader).equals(partition.element(prev.leader))){
            //            unionsToDo.add(new Node[]{cluster.leader, prev.leader});
            //        }
            //    }
            //    prev = cluster;
            //}
    
            //for(Node<Integer>[] pair : unionsToDo){
            //    partition.union(pair[0], pair[1]);
            //}
            // get the number of clusters with id 1 (islands)

            // next step is to get the lakes
            // for each cluster of water
            // check if it is completely surrounded by land clusters
            // it cannot even have the corners of the edge touching another water cluster
            // if it is, change its id to "lake"
            for (Cluster<Integer> cluster : new ArrayList<>(partition.clusters)) {
                if (partition.element(cluster.leader).equals(0)) {
                    boolean isLake = true;
                    for (Node<Integer> pos : cluster.sequence) {
                        int x = pos.x;
                        int y = pos.y;
                        int[][] directions = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
                        for (int[] dir : directions) {
                            int newX = x + dir[0];
                            int newY = y + dir[1];
                            if (newX < 0 || newX >= rows || newY < 0 || newY >= cols) {
                                isLake = false;
                                break;
                            }
                            if (data.get(newX).get(newY).equals(0)) continue;
                        }
                        if (!isLake) break;
                    }

                    if (isLake) {
                        numberOfLakes++;
                        areaOfLakes += cluster.sequence.size();
                        cluster.id = "lake";

                        // find out which island this lake is inside of
                        List<Node<Integer>> NodesSnapshot = new ArrayList<>();
                        for (Node<Integer> poss : cluster.sequence) {
                            int xx = poss.x;
                            int yy = poss.y;
                            int[][] directionsa = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
                            for (int[] dir : directionsa) {
                                int newX = xx + dir[0];
                                int newY = yy + dir[1];
                                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols) {
                                    if (data.get(newX).get(newY).equals(1)) {
                                        Node<Integer> landPos = Nodes[newX][newY];
                                        Cluster<Integer> landCluster = partition.getCluster(landPos);
                                        NodesSnapshot.add(cluster.leader);
                                        NodesSnapshot.add(landCluster.leader);
                                        //partition.union(cluster.leader, landCluster.leader);
                                        break;
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < NodesSnapshot.size(); i += 2) {
                            partition.union(NodesSnapshot.get(i), NodesSnapshot.get(i + 1));
                        }
                    }
                }
            }

            int count = 0;
            for(Cluster<Integer> ccluster : partition.clusters){
                if(partition.element(ccluster.leader).equals(1)){
                    count++;
                }
            }
            System.out.println(count > 0 ? count : -1);

            // print the size of each island in descending order
            List<Integer> sizes = new ArrayList<>();
            for(Cluster<Integer> cccluster : partition.clusters){
                if(partition.element(cccluster.leader).equals(1)){
                    sizes.add(cccluster.sequence.size());
                }
            }
            sizes.sort((a, b) -> b - a);
            //System.out.println("Sizes of islands in descending order: ");
            for (int size : sizes){
                System.out.print(size + " ");
            }
            System.out.println();

            // print the total sum of the sizes of the islands
            int totalSize = 0;
            for (int size : sizes){
                totalSize += size;
            }
            System.out.println(totalSize);
            int phasesOfRecovery = Integer.parseInt(scanner.nextLine());
            for(int phase = 1; phase <= phasesOfRecovery; phase++){
                String skip = scanner.nextLine(); // skip the next line
                String line = scanner.nextLine().trim();
                String[] coords = line.split(" ");
                for (int i=0; i<coords.length/2; i++){
                    int x = Integer.parseInt(coords[i*2]);
                    int y = Integer.parseInt(coords[i*2+1]);
                    // change the value at data[x][y] to 1 (land)
                    data.get(x).set(y, 1);
                    // create a new cluster for this Node
                    Node<Integer> newPos = partition.makeCluster(1, "land", x, y);
                    Nodes[x][y] = newPos;

                    // union with adjacent land cells
                    if (x > 0 && data.get(x - 1).get(y).equals(1)) {
                        partition.union(newPos, Nodes[x - 1][y]);
                    }
                    if (x < rows - 1 && data.get(x + 1).get(y).equals(1)) {
                        partition.union(newPos, Nodes[x + 1][y]);
                    }
                    if (y > 0 && data.get(x).get(y - 1).equals(1)) {
                        partition.union(newPos, Nodes[x][y - 1]);
                    }
                    if (y < cols - 1 && data.get(x).get(y + 1).equals(1)) {
                        partition.union(newPos, Nodes[x][y + 1]);
                    }
                }
                // recount the number of islands
                count = 0;
                for(Cluster<Integer> clusterr : partition.clusters){
                    if(partition.element(clusterr.leader).equals(1)){
                        count++;
                    }
                }
                System.out.println();
                // print the total number of islands
                System.out.println(count > 0 ? count : -1);
                // print the size of each island in descending order
                sizes = new ArrayList<>();
                for(Cluster<Integer> clluster : partition.clusters){
                    if(partition.element(clluster.leader).equals(1)){
                        sizes.add(clluster.sequence.size());
                    }
                }
                sizes.sort((a, b) -> b - a);
                //System.out.println("Sizes of islands in descending order: ");
                for (int size : sizes){
                    System.out.print(size + " ");
                }
                System.out.println();
                // print the total sum of the sizes of the islands
                totalSize = 0;
                for (int size : sizes){
                    totalSize += size;
                }
                System.out.println("Total size of islands: " + totalSize);
                System.out.println("Number of lakes: " + numberOfLakes);
                System.out.println("Total area of lakes: " + areaOfLakes);
            }
            scanner.close();
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}