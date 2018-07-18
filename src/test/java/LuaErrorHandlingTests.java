import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author jammehcow
 */

public class LuaErrorHandlingTests {
    @Test
    public void checkStack() {
        // Check that the stream is empty (returns Optional.empty when Stream is empty)
        Assertions.assertEquals(LuaEnvironment.getErrors(), Optional.empty());

        // Create a new Exception and add it to the stack
        @SuppressWarnings("ThrowableNotThrown")
        Exception e = new Exception("Test");
        LuaEnvironment.addError(e);

        // Get the last exception (the one we just pushed)
        Optional<Exception> result = LuaEnvironment.getLastError();

        // Check the exception exists and is the same as the one we pushed
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get().getMessage(), e.getMessage());

        // Add a new exception with a different message
        LuaEnvironment.addError(new Exception("Test2"));
        result = LuaEnvironment.getLastError();

        // Check the previous exception is returned and not the one we added first
        Assertions.assertNotSame(result, "Test");

        // Get the Error stream
        Optional<Stream<Exception>> stream = LuaEnvironment.getErrors();

        // Check the Stream returned is now 2 items long
        Assertions.assertTrue(stream.isPresent());
        Assertions.assertEquals(stream.get().count(), 2L);
    }
}
