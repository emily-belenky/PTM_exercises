package graph;

/// Advanced Programming exercise 1

import java.util.*;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Agent {
    String getName();
    void reset();
    void callback(String topic, Message msg);
    void close();
}