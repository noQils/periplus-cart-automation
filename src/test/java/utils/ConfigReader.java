package utils;

public final class ConfigReader {

    private ConfigReader() {
    }

    public static String getRequired(String key) {
        String value = System.getProperty(key);

        if (value == null || value.isBlank()) {
            value = System.getenv(toEnvKey(key));
        }

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                "Missing required configuration: " + key
                + ". Set it as a JVM property (-D" + key + "=...) or environment variable ("
                + toEnvKey(key) + ")."
            );
        }

        return value;
    }

    private static String toEnvKey(String key) {
        return key.toUpperCase().replace('.', '_');
    }
}