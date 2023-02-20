package avengers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Given a Set of Edges representing Vision's Neural Network, identify all of
 * the
 * vertices that connect to the Mind Stone.
 * List the names of these neurons in the output file.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * MindStoneNeighborNeuronsInputFile name is passed through the command line as
 * args[0]
 * Read from the MindStoneNeighborNeuronsInputFile with the format:
 * 1. v (int): number of neurons (vertices in the graph)
 * 2. v lines, each with a String referring to a neuron's name (vertex name)
 * 3. e (int): number of synapses (edges in the graph)
 * 4. e lines, each line refers to an edge, each line has 2 (two) Strings: from
 * to
 * 
 * Step 2:
 * MindStoneNeighborNeuronsOutputFile name is passed through the command line as
 * args[1]
 * Identify the vertices that connect to the Mind Stone vertex.
 * Output these vertices, one per line, to the output file.
 * 
 * Note 1: The Mind Stone vertex has out degree 0 (zero), meaning that there are
 * no edges leaving the vertex.
 * 
 * Note 2: If a vertex v connects to the Mind Stone vertex m then the graph has
 * an edge v -> m
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 * To read from a file use StdIn:
 * StdIn.setFile(inputfilename);
 * StdIn.readInt();
 * StdIn.readDouble();
 * 
 * To write to a file use StdOut:
 * StdOut.setFile(outputfilename);
 * //Call StdOut.print() for EVERY neuron (vertex) neighboring the Mind Stone
 * neuron (vertex)
 * 
 * Compiling and executing:
 * 1. Make sure you are in the ../InfinityWar directory
 * 2. javac -d bin src/avengers/*.java
 * 3. java -cp bin avengers/MindStoneNeighborNeurons mindstoneneighborneurons.in
 * mindstoneneighborneurons.out
 *
 * @author Yashas Ravi
 * 
 */

public class MindStoneNeighborNeurons {

    public static void main(String[] args) {

        if (args.length < 2) {
            StdOut.println("Execute: java MindStoneNeighborNeurons <INput file> <OUTput file>");
            return;
        }

        String mindStoneNeighborNeuronsInputFile = args[0];
        String mindStoneNeighborNeuronsOutputFile = args[1];

        StdIn.setFile(mindStoneNeighborNeuronsInputFile);

        HashMap<String, HashSet<String>> network = new HashMap<>();

        int n = StdIn.readInt();

        for (int i = 0; i < n; i++) {
            network.put(StdIn.readString(), new HashSet<>());
        }

        int e = StdIn.readInt();

        for (int i = 0; i < e; i++) {
            network.get(StdIn.readString()).add(StdIn.readString());
        }

        String stone = null;
        ArrayList<String> neighborNeurons = new ArrayList<>();

        for (String key : network.keySet()) {
            if (network.get(key).size() == 0) {
                stone = key;
                break;
            }
        }

        for (String key : network.keySet()) {
            if (network.get(key).contains(stone)) {
                neighborNeurons.add(key);
            }
        }

        StdOut.setFile(mindStoneNeighborNeuronsOutputFile);
        for (int i = 0; i < neighborNeurons.size(); i++) {
            StdOut.println(neighborNeurons.get(i));
        }
        // WRITE YOUR CODE HERE

    }
}
