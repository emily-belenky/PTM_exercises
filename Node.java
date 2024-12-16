package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a node in a computational graph.
 */
public class Node {
    private String name;
    private List<Node> edges;
    private Message msg;

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getEdges() {
        return edges;
    }

    public void setEdges(List<Node> edges) {
        this.edges = edges;
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public void addEdge(Node newNode) {
        this.edges.add(newNode);
    }

    /**
     * Checks if this node is part of a cycle in the graph.
     *
     * @return true if a cycle is detected, otherwise false.
     */
    public boolean hasCycles() {
        Set<Node> visited = new HashSet<>();
        Set<Node> recursionStack = new HashSet<>();
        return dfs(this, visited, recursionStack);
    }

    private boolean dfs(Node currentNode, Set<Node> visited, Set<Node> recursionStack) {
        visited.add(currentNode);
        recursionStack.add(currentNode);

        for (Node neighbor : currentNode.getEdges()) {
            if (!visited.contains(neighbor) && dfs(neighbor, visited, recursionStack)) {
                return true;
            } else if (recursionStack.contains(neighbor)) {
                return true;
            }
        }

        recursionStack.remove(currentNode);
        return false;
    }
}
