package com.dbs.junitGenerator.Utils;

public class TestTemplateUtil {
    public static String generateTestTemplate(String className) {
        return """
                import org.junit.jupiter.api.Test;
                import static org.junit.jupiter.api.Assertions.*;

                public class %sTest {

                    @Test
                    public void testGettersAndSetters() {
                        // Add tests for getters and setters here
                        assertTrue(true); // Placeholder test
                    }
                }
                """.formatted(className);
    }
}
