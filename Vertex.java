import java.util.ArrayList;

public class Vertex {
    private String name;
    private ArrayList<Edge> edges;

    public Vertex(String name) {
        this.name = name;
        edges = new ArrayList();
    }

    public void addEdge(Edge e)
    {
        edges.add(e);
    }

    public ArrayList<Edge> getEdges()
    {
        return this.edges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
