package utils;

public class ENVUtils {
    public static String get(String key) {
        try {
            return System.getenv(key);
        } catch (RuntimeException error) {
            throw new RuntimeException(error);
        }
    }
}
