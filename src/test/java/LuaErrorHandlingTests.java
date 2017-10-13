import nz.co.jammehcow.lukkit.environment.LuaEnvironment;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author jammehcow
 */

public class LuaErrorHandlingTests {
    @Test
    public void checkStack() {
        // Check that the stream is empty (returns Optional.empty when Stream is empty)
        Assert.assertEquals(LuaEnvironment.getErrors(), Optional.empty());

        // Create a new Exception and add it to the stack
        @SuppressWarnings("ThrowableNotThrown")
        Exception e = new Exception("Test");
        LuaEnvironment.addError(e);

        // Get the last exception (the one we just pushed)
        Optional<Exception> result = LuaEnvironment.getLastError();

        // Check the exception exists and is the same as the one we pushed
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().getMessage(), e.getMessage());

        // Add a new exception with a different message
        LuaEnvironment.addError(new Exception("Test2"));
        result = LuaEnvironment.getLastError();

        // Check the previous exception is returned and not the one we added first
        Assert.assertNotSame(result, "Test");

        // Get the Error stream
        Optional<Stream<Exception>> stream = LuaEnvironment.getErrors();

        // Check the Stream returned is now 2 items long
        Assert.assertTrue(stream.isPresent());
        Assert.assertEquals(stream.get().count(), 2L);
    }
}
