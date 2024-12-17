package test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ParallelAgent implements Agent {
    private final BlockingQueue<Message> queue;
    private final Agent agent;
    private final Thread readThread;
    private static final String divider = "Divider:";
    private volatile boolean running;

    public ParallelAgent(Agent agent, int capacity) {
        this.queue = new ArrayBlockingQueue<>(capacity);
        this.agent = agent;
        this.readThread = new Thread(this::readThreadFunction);
        this.running = true;
        this.readThread.start();
    }

    @Override
    public String getName() {
        return this.agent.getName();
    }

    @Override
    public void reset() {
        this.agent.reset();
    }

    @Override
    public void callback(String topic, Message msg) {
        try {
            Message topicMessageMerge = new Message(String.format("%s%s%s", topic, divider, msg.asText));
            this.queue.put(topicMessageMerge);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        this.running = false;
        this.readThread.interrupt();
        this.agent.close();
    }

    private void readThreadFunction() {
        while (this.running) {
            try {
                Message msg = this.queue.take();
                this.agent.callback(getTopic(msg), getMessage(msg));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private String getTopic(Message msg) {
        return msg.asText.split(divider, 2)[0];
    }

    private Message getMessage(Message msg) {
        return new Message(msg.asText.split(divider, 2)[1]);
    }
}
