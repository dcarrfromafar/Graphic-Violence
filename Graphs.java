import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Graphs {

    public static void main(String[] args) {
        String filename = "graphinput.txt";
        Graph graph = null;
        try {
           graph = new Graph(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner in = new Scanner(System.in);
        int choice = 0;
        boolean quit = false;
        while(!quit){
            System.out.println("1. Is Connected\n" +
                    "2. Minimum Spanning Tree\n" +
                    "3. Shortest Path\n" +
                    "4. Is Metric\n" +
                    "5. Make Metric\n" +
                    "6. Traveling Salesman Problem\n" +
                    "7. Approximate TSP\n" +
                    "8. Quit\n" +
                    "Make your choice (1-8): ");
            choice = in.nextInt();
            if(choice < 0 || choice > 8) {
                System.out.println("Please choose a valid option. \n");
            }
            else if(choice == 1){
                if(graph.isConnected()){
                    System.out.println("Graph is connected.\n");
                }
                else{
                    System.out.println("Graph is not connected.\n");
                }
            }
            else if(choice == 2){
                if(!graph.isConnected()){
                    System.out.println("Error: Graph is not connected\n");
                }
                else {
                    System.out.println(graph.mst());
                }
            }
            else if(choice == 3){
                System.out.println("From which node would you like to find the shortest paths (0 - " + (graph.V() - 1) + ") : ");
                int start = in.nextInt();
                System.out.println(graph.shortestPath(start));
            }
            else if(choice == 4){
                if(!graph.isConnected()){
                    System.out.println("Graph is not metric: Graph is not completely connected. \n");
                }
                else if(!graph.isMetric()){
                    System.out.println( "Graph is not metric: Edges do not obey the triangle inequality.\n");
                }
                else{
                    System.out.println("Graph is metric.\n");
                }
            }
            else if(choice == 5){
                if(!graph.isConnected()){
                    System.out.println("Error: Graph is not connected\n");
                }
                else{
                    System.out.println(graph.makeMetric());
                }
            }

            //NEED CHOICE SIX

            else if(choice == 7){
                if(!graph.isConnected()){
                    System.out.println("Graph is not connected. \n");
                }
                else if(!graph.isMetric()){
                    System.out.println("Graph is not metric. \n");
                }
                else{
                    System.out.println(graph.apporxTSP());
                }
            }
            else if(choice == 8){
                quit = true;
            }
            else{
                System.out.println("Please enter a valid choice (1-8). \n");
            }
        }
        System.out.println("Bye SUCKAH!");
    }
}
