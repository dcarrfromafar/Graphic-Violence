import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Graph {
    private Boolean isConnected;
    private RedBlackTree<Integer, Integer> adj[];

    public static void main(String[] args) {
        Graph graph = null;
        try {
            graph = new Graph("metrictest");
        } catch(Exception e){}
        //System.out.println(graph.toString());
        int source = 0;
        String paths = graph.shortestPath(source);
//        System.out.println(paths);
//        System.out.println(graph);

        //System.out.println(graph.isMetric());
        //System.out.println(graph.makeMetric());


        System.out.println(graph.apporxTSP());
    }

    Graph(int V){
        adj = new RedBlackTree[V];
        for(int i = 0; i < adj.length; ++i){
            adj[i] = new RedBlackTree<>();
        }
    }
    private Graph(String input, int v) {
        Scanner in = new Scanner(input);
        int V = Integer.parseInt( in.nextLine() );
        adj = new RedBlackTree[V];
        for(int i = 0; i < adj.length; ++i){
            adj[i] = new RedBlackTree<>();
        }
        int firstVert = 0;
        while (in.hasNext()) {
            String nextLine = in.nextLine();
            String[] lines = nextLine.split("\\s+");
            for (int i = 1; i < lines.length; i+=2) {
                try {
//                    System.out.println("line = " + firstVert);
//                    System.out.println("i=" + i);
                    int secondVert = Integer.parseInt(lines[i]);
                    int weight = Integer.parseInt(lines[i+1]);
                    //System.out.println(firstVert + "\t" + secondVert+ "\t" + weight);
                    this.addEdge(firstVert, secondVert, weight);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.print("Improperly formatted file");
                }
            }
            firstVert++;
        }
    }

    Graph(String filename) throws FileNotFoundException {
        Scanner in = new Scanner(new File(filename));
        int V = Integer.parseInt( in.nextLine() );
        adj = new RedBlackTree[V];
        for(int i = 0; i < adj.length; ++i){
            adj[i] = new RedBlackTree<>();
        }
        int firstVert = 0;
        while (in.hasNext()) {
            String nextLine = in.nextLine();
            String[] lines = nextLine.split("\\s+");
            for (int i = 1; i < lines.length; i+=2) {
                try {
//                    System.out.println("line = " + firstVert);
//                    System.out.println("i=" + i);
                    int secondVert = Integer.parseInt(lines[i]);
                    int weight = Integer.parseInt(lines[i+1]);
                    //System.out.println(firstVert + "\t" + secondVert+ "\t" + weight);
                    this.addEdge(firstVert, secondVert, weight);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.print("Improperly formatted file");
                }
            }
            firstVert++;
        }
    }

    public Iterable<Integer> edgeTo(int v){
        return adj[v].keys();
    }

    public int V(){
        return adj.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(adj.length +"\n");
        for (RedBlackTree<Integer, Integer> r: adj) {
            sb.append(r.size()+" ");
            for (Integer i : r.keys()) {
                sb.append(i + " " + r.get(i) + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int weight(int a, int b){
        return adj[a].get(b);
    }

    public void addEdge(int v, int w, int weight){
        adj[v].put(w, weight);
        adj[w].put(v, weight);
    }

    public boolean isConnected() {
        if (isConnected != null) return isConnected;
        DepthFirstSearch dfs = new DepthFirstSearch(this, 0);
        boolean isConnected = true;
        for (int i = 0; i < adj.length && isConnected; i++) {
            if (!dfs.marked(i)) isConnected = false;
        }
        this.isConnected = isConnected;
        return isConnected;
    }

    public String mst() {
        final int INFINITY = Integer.MAX_VALUE;
        int[] key = new int[this.V()];
        Integer[] edgeFrom = new Integer[this.V()];
        boolean[] inQueue = new boolean[this.V()];

        for (int i = 0; i < this.V(); i++) {
            key[i] = INFINITY;
            inQueue[i] = true;
        }
        key[0] = 0;
        edgeFrom[0] = null;

        int current = 0;
        while (containsTrue(inQueue)) {
            // find the minimum value inQueue
            for (int i = 0; i < key.length; i++) {
                if (!inQueue[current]) current = i;
                if (inQueue[i] && key[i] < key[current]) {
                    current = i;
                }
            }
//            System.out.println("Current = " + current + "\t" + Arrays.toString(edgeFrom));
            inQueue[current] = false;
            for (Integer v : this.edgeTo(current)) {
                if (inQueue[v] && weight(current, v) < key[v]) {
                    edgeFrom[v] = current;
                    key[v] = weight(current, v);
                }
            }
        }
        Graph G = new Graph(this.V());

        for (int i = 0; i < G.V(); i++) {
            if (edgeFrom[i] != null) {
                G.addEdge(i, edgeFrom[i], key[i]);
            }
        }
        return G.toString();
    }
    private boolean containsTrue(boolean[] arr) {
        boolean containsTrue = false;
        for (int i = 0; i < arr.length && !containsTrue; i++) {
            if (arr[i]) containsTrue = true;
        }
        return containsTrue;
    }

    public String shortestPath(int v) {
        BreadthFirstSearch search = new BreadthFirstSearch(this, v);
        StringBuilder sb = new StringBuilder("");
        for(int i = 0; i < V(); ++i){
            sb.append(i + ": " + "(" + search.distTo(i) + ")");
            sb.append("\t\t");
            int counter = 0;
            for(Integer j : search.pathTo(i)){
                counter++;
            }
            int arrows = 0;
            for(Integer k: search.pathTo(i)){
                sb.append(k);
                if(arrows < counter-1) sb.append(" -> ");
                arrows++;
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public boolean isMetric() {
        for(int i = 0; i < adj.length; ++i){
            int counter = 0;
            for(Integer k: this.edgeTo(i)){
                counter++;
            }
            if(counter != adj.length - 1){
                return false;
            }
        }
        for(int i = 0; i < adj.length; ++i){
            BreadthFirstSearch search = new BreadthFirstSearch(this, i);
            int counter = 0;
            for(Integer k: search.pathTo(i)){
                counter++;
            }
            if(counter > 2){
                return false;
            }
        }
        return true;
    }

    public String makeMetric() {
        for(int i = 0; i < adj.length; ++i){
            BreadthFirstSearch bfs = new BreadthFirstSearch(this, i);
            for(int j = 0; j < adj.length; ++j){
                if(j != i){
                    try{
                        weight(i, j);
                    } catch (IllegalArgumentException | NullPointerException e){
                        int distance = bfs.distTo(j);
                        addEdge(i, j, distance - 1);
                    }
                }
            }
        }
        return this.toString();
    }

    public String apporxTSP(){
        Graph app = new Graph(this.mst(), 0);
        DepthFirstSearch search = new DepthFirstSearch(app, 0);
        int[] visited = search.getVisited();
        StringBuilder string = new StringBuilder();
        int distance = 0;
        for(int i = 1; i < visited.length; i++){
            int index1 = search(i, visited);
            int index2 = search(i + 1, visited);
            string.append(index1);
            string.append(" -> ");
            if (i == visited.length-1) string.append(index2);
            distance += weight(index1, index2);
        }
        distance += weight(search(1, visited), search(visited.length, visited));
        return distance + ": " + string.toString() + " -> " + search(1, visited);
    }

    public int search(int v, int[] arr){
        int index = -1;
        for (int i = 0 ; i < arr.length && index == -1; i++) {
            if (arr[i] == v) index = i;
        }
        return index;
    }
    
    public String tsp() {
		/*
		int[] vertices = new int[this.V()];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = i;
		}
		Permutations permutation = new Permutations(vertices);

		Integer[] bestRoute = null;
		int bestDistance = Integer.MAX_VALUE;
		Integer[] currentRoute = permutation.getCurrentPermutation();
		while (currentRoute != null) {
			boolean isValid = true;
			int currentDistance = 0;
			for (int i = 0; i < currentRoute.length && isValid; i++) {
				int a,b;
				if (i < currentRoute.length-1) {
					a = i;
					b = i+1;
				}
				else {
					a = i;
					b = 0;
				}
				try {
					currentDistance += this.weight(a, b);
				} catch (NullPointerException e) {
					isValid = false;
				}

			}

			if (currentDistance < bestDistance && isValid) {
				bestRoute = currentRoute;
				bestDistance = currentDistance;
			}
			currentRoute = permutation.getCurrentPermutation();
		}

		if (bestRoute == null ) return "No path exists";
		StringBuilder sb = new StringBuilder();
		sb.append(bestDistance);
		for (Integer i : bestRoute) {
			sb.append(i + " -> ");
		}
		sb.append(0);
		return sb.toString();
		
		 */
		return "Method not implemented.";
	}
}
