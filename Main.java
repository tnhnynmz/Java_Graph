import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Graph g = new Graph();
        String path = "graph.txt";
        FileRead f = new FileRead(path);
        f.read_put(g);
        Scanner sc = new Scanner(System.in);
        String source, destination;
        do {
            System.out.println("Please Choose a Source : ");
            source = sc.nextLine();
        }
        while (g.vertices.get(source) == null);
        do {
            System.out.println("Please Choose a Destination : ");
            destination = sc.nextLine();
        }
        while (g.vertices.get(destination) == null);
        System.out.println("1-Max Package From "+source+" to "+destination+" = "+Graph.FordFulkerson(g,source,destination));
        System.out.println("2,3-Edges to Increase and Their Max Increase Capacity = "+g.edgesToIncrease(Graph.FordFulkerson(g,source,destination),source,destination));
        System.out.println("--0 increase edges(does not effect max package but bottleneck) not printed--");
    }


}
