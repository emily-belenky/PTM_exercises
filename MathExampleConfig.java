package test;

/**
 * Example configuration for mathematical operations in a computational graph.
 */
public class MathExampleConfig implements Config {

    @Override
    public void create() {
        // Pass input and output topics as individual strings
        new BinOpAgent("plus", "A", "B", "R1", (x, y) -> x + y);
        new BinOpAgent("minus", "A", "B", "R2", (x, y) -> x - y);
        new BinOpAgent("mul", "R1", "R2", "R3", (x, y) -> x * y);
    }

    @Override
    public String getName() {
        return "Math Example";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void close() {
        // Optional cleanup
    }
}
