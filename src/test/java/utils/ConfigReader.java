package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ConfigReader {
    private static final Map<String, String> DOTENV_VALUES = loadDotEnv();

    private ConfigReader() {
    }

    public static String getRequired(String key) {
        String value = System.getProperty(key);

        if (value == null || value.isBlank()) {
            value = System.getenv(toEnvKey(key));
        }

        if (value == null || value.isBlank()) {
            value = DOTENV_VALUES.get(toEnvKey(key));
        }

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                "Missing required configuration: " + key
                + ". Set it as a JVM property (-D" + key + "=...), environment variable ("
                + toEnvKey(key) + "), or in the project .env file."
            );
        }

        return value;
    }

    private static String toEnvKey(String key) {
        return key.toUpperCase().replace('.', '_');
    }

    private static Map<String, String> loadDotEnv() {
        Map<String, String> values = new HashMap<>();
        Path dotEnvPath = Path.of(".env");

        if (!Files.exists(dotEnvPath)) {
            return values;
        }

        try {
            List<String> lines = Files.readAllLines(dotEnvPath);

            for (String rawLine : lines) {
                String line = rawLine.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                int separatorIndex = line.indexOf('=');
                if (separatorIndex <= 0) {
                    continue;
                }

                String key = line.substring(0, separatorIndex).trim();
                String value = line.substring(separatorIndex + 1).trim();

                if ((value.startsWith("\"") && value.endsWith("\""))
                    || (value.startsWith("'") && value.endsWith("'"))) {
                    value = value.substring(1, value.length() - 1);
                }

                values.put(key, value);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read .env file from project root.", exception);
        }

        return values;
    }
}
