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
            String[] dimensions = firstline.split(" ");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    String line = scanner.nextLine();
                    String[] numbers = line.split(" ");
                    List<Integer> row = new ArrayList<>();
                    for(String num : numbers){
                        row.add(Integer.parseInt(num));
                    }
                    data.add(row);
                }
            }

            for(int i = 1; i < rows; i++){
                for(int j = 1; j < cols; j++){
                    Position<Integer> pos = partition.makeCluster(data.get(i).get(j), data.get(i).get(j).toString(), i, j);
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
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        // now i have the 2d list of data of integers
        // i need to make a partition of the data

        
    }
}
