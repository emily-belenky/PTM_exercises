package test;

import java.io.*;
import java.util.*;

public class GenericConfig implements Config {
    private String configFile; // Path to the configuration file
    private final List<Agent> agents = new ArrayList<>();

    @Override
    public void create() {
        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            List<String> lines = new ArrayList<>();
            String line;

            // Read all lines from the configuration file
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            // Validate the configuration file format
            if (lines.size() % 3 != 0) {
                throw new IllegalArgumentException("Invalid configuration file format. Expected sets of 3 lines.");
            }

            // Parse and create agents
            for (int i = 0; i < lines.size(); i += 3) {
                String className = lines.get(i);
                String[] subs = lines.get(i + 1).split(",");
                String[] pubs = lines.get(i + 2).split(",");

                // Create agent dynamically using reflection
                Class<?> clazz = Class.forName(className);
                Agent agent = (Agent) clazz.getConstructor(String[].class, String[].class)
                                           .newInstance((Object) subs, (Object) pubs);

                // Add agent to the list for future management
                agents.add(agent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "Generic Configuration";
    }

    @Override
    public int getVersion() {
        return 1; // Version number for this implementation
    }

    @Override
    public void close() {
        // Close all agents to clean up resources
        for (Agent agent : agents) {
            agent.close();
        }
    }

    // Set the configuration file with fallback to "src/test/simple.conf"
    public void setConfFile(String configFile) {
        File file = new File(configFile);

        // Check if the file exists at the provided location
        if (!file.exists()) {
            // Fallback to "src/test/" directory
            file = new File("src/" + configFile);
        }

        // If the file still doesn't exist, throw an exception
        if (!file.exists()) {
            throw new IllegalArgumentException("Configuration file not found at: " + configFile);
        }

        this.configFile = file.getPath(); // Save the absolute path
    }
}
