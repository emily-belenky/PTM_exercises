package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph extends ArrayList<Node> {

    private Integer agentCounter = 1;

    public boolean hasCycles() {
        for (Node node : this) {
            if (node.hasCycles()) {
                return true;
            }
        }
        return false;
    }

    public void createFromTopics() {
        this.clear();
        Map<Agent, Node> agentSeen = new HashMap<>();

        for (Topic topic : TopicManagerSingleton.get().getTopics()) {
            // Set topic node name as "T<topic name>"
            Node topicNode = new Node("T" + topic.name);
            this.add(topicNode);

            // Handle subscribers
            for (Agent agent : topic.getSubscribers()) {
                agentSeen.computeIfAbsent(agent, a -> {
                    String agentName = "A" + a.getName(); // Set name as "A<agent name>"
                    Node agentNode = new Node(agentName);
                    this.add(agentNode);
                    return agentNode;
                });
                topicNode.addEdge(agentSeen.get(agent));
            }

            // Handle publishers
            for (Agent agent : topic.getPublishers()) {
                agentSeen.computeIfAbsent(agent, a -> {
                    String agentName = "A" + a.getName(); // Set name as "A<agent name>"
                    Node agentNode = new Node(agentName);
                    this.add(agentNode);
                    return agentNode;
                });
                agentSeen.get(agent).addEdge(topicNode);
            }
        }
    }
}
