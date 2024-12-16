package test;

import java.util.function.BinaryOperator;

/**
 * Represents an agent that performs binary operations on input topics and publishes the result.
 */
public class BinOpAgent implements Agent {

    private String name;
    private String firstInputTopicName;
    private String secondInputTopicName;
    private String outputTopicName;
    private BinaryOperator<Double> operation;

    private Double firstInput;
    private Double secondInput;

    public BinOpAgent(String name, String firstInput, String secondInput, String output, BinaryOperator<Double> operation) {
        this.name = name;
        this.firstInputTopicName = firstInput;
        this.secondInputTopicName = secondInput;
        this.outputTopicName = output;
        this.operation = operation;

        subscribeToInputTopics();
        publishToOutputTopic();
    }

    private void subscribeToInputTopics() {
        Topic firstInput = TopicManagerSingleton.get().getTopic(firstInputTopicName);
        firstInput.subscribe(this);

        Topic secondInput = TopicManagerSingleton.get().getTopic(secondInputTopicName);
        secondInput.subscribe(this);
    }

    private void publishToOutputTopic() {
        Topic output = TopicManagerSingleton.get().getTopic(outputTopicName);
        output.addPublisher(this);
    }

    private void executeMathOperation() {
        if (firstInput != null && secondInput != null) {
            double result = operation.apply(firstInput, secondInput);
            publish(result);
        }
    }

    private void publish(Double result) {
        Topic output = TopicManagerSingleton.get().getTopic(outputTopicName);
        output.publish(new Message(result));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void reset() {
        firstInput = null;
        secondInput = null;
    }

    @Override
    public void callback(String topic, Message msg) {
        if (topic.equals(firstInputTopicName)) {
            firstInput = msg.asDouble;
        } else if (topic.equals(secondInputTopicName)) {
            secondInput = msg.asDouble;
        }

        executeMathOperation();
    }

    @Override
    public void close() {
        // Optional cleanup
    }
}
