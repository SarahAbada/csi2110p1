import java.util.LinkedList;

public class Partition<E> {
    public LinkedList<LinkedList<E>> parts; // list of parts in the partition
    public int size; // number of parts in the partition
    public Partition() {
        parts = new LinkedList<>();
        size = 0;
    }
    public Partition(E x){
        parts = new LinkedList<>();
        LinkedList<E> part = new LinkedList<>();
        part.add(x);
        parts.add(part);
        size = 1;
    }
}