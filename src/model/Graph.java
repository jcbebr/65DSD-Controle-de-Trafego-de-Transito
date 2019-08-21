package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author José Carlos
 */
public class Graph {
    
    private final List<int []> vertices;
    private final List<LinkedList<Integer>> arestas;

    public Graph() {
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
    }

    public void add(int [] n) {
        vertices.add(n);
        arestas.add(new LinkedList<>());

    }

    public int size() {
        return vertices.size();
    }

    public int getX(int i){
        return vertices.get(i)[0];
    }
    
    public int getY(int i){
        return vertices.get(i)[1];
    }
    
}
