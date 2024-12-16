package test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class Topic {

    public final String name;
    private final List<Agent> subscribers;
    private final List<Agent> publishers;

    private String result = "";

    Topic(String name) {
        this.name = name;
        this.subscribers = new CopyOnWriteArrayList<>();
        this.publishers = new CopyOnWriteArrayList<>();
    }

    public void subscribe(Agent agent) {
        this.subscribers.add(agent);
    }

    public void unsubscribe(Agent agent) {
        this.subscribers.remove(agent);
    }

    public void publish(Message message) {
        setResult(message.asText);

        for (Agent agent : this.subscribers) {
            agent.callback(this.name, message);
        }
    }

    public void addPublisher(Agent agent) {
        this.publishers.add(agent);
    }

    public void removePublisher(Agent agent) {
        this.publishers.remove(agent);
    }

    public List<Agent> getPublishers() {
        return this.publishers;
    }

    public List<Agent> getSubscribers() {
        return this.subscribers;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String anyResult) {
        this.result = anyResult;
    }
}