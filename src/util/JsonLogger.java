package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class JsonLogger {
    private static final String FILE_PATH = "messages.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static synchronized void logMessage(String sender, String message) {
        File file = new File(FILE_PATH);
        ArrayNode rootNode;

        try {
            if (file.exists() && file.length() > 0) {
                try {
                    rootNode = (ArrayNode) mapper.readTree(file);
                } catch (Exception e) {
                    // Create new array if file is corrupted or not an array
                    rootNode = mapper.createArrayNode();
                }
            } else {
                rootNode = mapper.createArrayNode();
            }

            ObjectNode messageNode = mapper.createObjectNode();
            messageNode.put("sender", sender);
            messageNode.put("message", message);
            rootNode.add(messageNode);

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, rootNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
