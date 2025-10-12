package csi2110p1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Part2a {
    public static void main(String[] args) {
        Scanner filename = new Scanner(System.in);
        System.out.println("Enter the filename: ");
        String fileName = filename.nextLine();
        filename.close();
        try{
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            // make a 2d list of data of integers
            List<List<Integer>> data = new ArrayList<>();
            Partition<Integer> partition = new Partition<>();
            String firstline = scanner.nextLine(); // skip the first line
            String[] dimensions = firstline.trim().split("\\s+");
            for (String dim : dimensions) {
                System.out.println(dim);
            }
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);
            Position<Integer>[][] positions = new Position[rows][cols];
            for(int i = 0; i < rows; i++){
                String line = scanner.nextLine();
                String[] values = line.split(" ");
                List<Integer> row = new ArrayList<>();
                for(int j = 0; j < cols; j++){
                    row.add(Integer.parseInt(values[j]));
                }
                data.add(row);
            }

            for(int i = 1; i < rows; i++){
                for(int j = 1; j < cols; j++){
                    Position<Integer> pos = partition.makeCluster(data.get(i).get(j), data.get(i).get(j).toString(), i, j);
                    positions[i][j] = pos;
                    if(data.get(i).get(j).equals(data.get(i-1).get(j))){
                        partition.union(positions[i][j], positions[i-1][j]);
                    }
                    if(data.get(i).get(j).equals(data.get(i).get(j-1))){
                        partition.union(positions[i][j], positions[i][j-1]);
                    }
                    
                }
                
            }
            java.util.ListIterator<Cluster<Integer>> it = partition.clusters.listIterator();
            Cluster<Integer> prev = null;
            for(Cluster<Integer> cluster : partition.clusters){
                if(prev != null){
                    if(partition.element(cluster.leader).equals(partition.element(prev.leader))){
                        partition.union(cluster.leader, prev.leader);
                    }
                }
                prev = cluster;
            }
            // get the number of clusters with id 1
            int count = 0;
            for(Cluster<Integer> cluster : partition.clusters){
                if(partition.element(cluster.leader) == 1){
                    count++;
                }
            }
            System.out.println("Number of islands: " + count);

            // print the size of each island in descending order
            List<Integer> sizes = new ArrayList<>();
            for(Cluster<Integer> cluster : partition.clusters){
                if(partition.element(cluster.leader) == 1){
                    sizes.add(cluster.sequence.size());
                }
            }
            sizes.sort((a, b) -> b - a);
            System.out.println("Sizes of islands in descending order: ");
            for(int size : sizes){
                System.out.print(size + " ");
            }
            System.out.println();

            // print the total sum of the sizes of the islands
            int totalSize = 0;
            for(int size : sizes){
                totalSize += size;
            }
            System.out.println("Total size of islands: " + totalSize);


            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        // now i have the 2d list of data of integers
        // i need to make a partition of the data

        
    }
}
