import java.util.HashMap;

public class Graph {
    public HashMap<String,Vertex> vertices;
    private HashMap<String,Edge> edges;
    private static Graph related_g;
    Graph() {
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
    }
    //addEdge method implemented from lecture documents.
    public void addEdge(String source, String destination, int weight) {

        if(edges.get(source + "-" + destination) == null)
        {
            Vertex sourceVertex, destinationVertex;

            if(vertices.get(source) == null)
            {
                sourceVertex  = new Vertex(source);
                vertices.put(source, sourceVertex);
            }
            else sourceVertex = vertices.get(source);

            if(vertices.get(destination) == null)
            {
                destinationVertex  = new Vertex(destination);
                vertices.put(destination, destinationVertex );
            }
            else destinationVertex = vertices.get(destination);

            Edge edge = new Edge(sourceVertex, destinationVertex, weight);
            sourceVertex.addEdge(edge);
            destinationVertex.addEdge(edge);
            edges.put(source + "-" + destination, edge);
        }
        else;
    }

    public void print(){

        System.out.println("Source\tDestination\tWeight");
        for (Edge e : edges.values()) {
            System.out.println("" + e.getSource().getName() + "\t" + e.getDestination().getName() + "\t\t" + e.getWeight()+ " ");
        }
    }

    //https://steemit.com/programming/@drifter1/programming-java-graph-maximum-flow-algorithm-ford-fulkerson
    /*I found Ford-Fulkerson algorithm and implemented for edge-vertex type. It is not a copy paste as you can see-
     on site, i've only used algorithm logic*/
    //Ford-fulkerson basically finds possible shortest paths from source to destination, and calculates flow through them.
    public static int FordFulkerson(Graph g, String s, String d){
        if (g.vertices.get(s).equals(g.vertices.get(d)))
            return 0;
        related_g = new Graph();
        HashMap<Edge, Boolean> v_related_g = new HashMap<>();
        Graph rg = new Graph();
        for (Edge i: g.edges()){
            rg.addEdge(i.getSource().getName(), i.getDestination().getName(), i.getWeight());
        }
        HashMap<String,String> parent = new HashMap<>();
        int maxFlow = 0;
        while(isConnected(rg, s, d, parent)){
            int path_flow = Integer.MAX_VALUE;
            for (Vertex i = rg.vertices.get(d); !i.getName().equals(s)
                    ; i = rg.vertices.get(parent.get(i.getName()))){
                Vertex j = rg.vertices.get(parent.get(i.getName()));
                related_g.addEdge(j.getName(),i.getName(),rg.edges.get(j.getName()+"-"+i.getName()).getWeight());
                path_flow = Math.min(path_flow, rg.edges.get(j.getName()+"-"+i.getName()).getWeight());
            }
            for (Vertex i = rg.vertices.get(d); !i.getName().equals(s)
                    ;i = rg.vertices.get(parent.get(i.getName()))){
                Vertex j = rg.vertices.get(parent.get(i.getName()));
                rg.edges.get(j.getName()+"-"+i.getName()).setWeight(rg.edges.get(j.getName()+"-"+i.getName()).getWeight()
                        -path_flow);
                if (rg.edges.get(i.getName()+"-"+j.getName()) == null)
                    rg.addEdge(i.getName(),j.getName(),0);
                rg.edges.get(i.getName()+"-"+j.getName()).setWeight(rg.edges.get(i.getName()+"-"+j.getName()).getWeight()
                        +path_flow);
            }
            maxFlow += path_flow;
        }
        return maxFlow;
    }

    //isConnected method returns if two vertex is connected each other someway or not.
    public static boolean isConnected(Graph g, String s, String d, HashMap parent)
    {
        Queue<Vertex> q = new Queue<>();
        HashMap<String, Boolean> visited = new HashMap<>();
        visited.put(g.vertices.get(s).getName(), true);
        q.add(g.vertices.get(s));
        while (!q.isEmpty())
        {
            Vertex v = q.deQueue();
            for (Edge u : v.getEdges())
            {
                if(visited.get(u.getDestination().getName())==null && u.getWeight() != 0)
                {
                    visited.put(u.getDestination().getName(), true);
                    q.add(u.getDestination());
                    parent.put(u.getDestination().getName(), u.getSource().getName());
                }
            }
        }
        boolean flag = true;
        if (visited.get(d) == null)
            flag = false;
        return flag;
    }

    //This function calculates edges to increase and how much we need to increase
    /*Logic is, when user enters inputs "source" and "destination" graph class automatically creates a
    related graph "related_g". This function takes the edges of related_g and increase them by 1, if maxPackage
    is changed then changed edge can be increased.*/
    public HashMap<String, Integer> edgesToIncrease(int maxFlow, String s, String d){
        HashMap<String, Integer> edges_ti = new HashMap<>();
        Graph rg = new Graph();
        rg = related_g;
        int increase_count = 1;
        for (Edge e: rg.edges()){
            rg.edges.get(e.getSource().getName()+"-"+e.getDestination().getName())
                    .setWeight(e.getWeight()+increase_count);
            if (FordFulkerson(rg,s,d) > maxFlow)
                edges_ti.put(e.getSource().getName()+"-"+e.getDestination().getName(),0);
            rg.edges.get(e.getSource().getName()+"-"+e.getDestination().getName())
                    .setWeight(e.getWeight()-increase_count);
        }
        /*Deciding how much we need to increase logic simply adds "1" to edges come from problem-2
        untill maxpackage not change anymore. */
        int tempFlow, tempWeight;
        for (String e: edges_ti.keySet()){
            tempWeight =rg.edges.get(e).getWeight();
            do{
            tempFlow = maxFlow;
            rg.edges.get(e).setWeight(rg.edges.get(e).getWeight() + increase_count);
            maxFlow = FordFulkerson(rg, s, d);
            }
            while (maxFlow != tempFlow);
            edges_ti.replace(e, rg.edges.get(e).getWeight()-tempWeight);
        }
        return edges_ti;
    }

    public  Iterable<Vertex> vertices()
    {
        return vertices.values();
    }

    public  Iterable<Edge> edges()
    {
        return edges.values();
    }

    public int size()
    {
        return vertices.size();
    }
}
