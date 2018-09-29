package nz.co.jammehcow.lukkit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author jammehcow
 */

public class UpdateCheckerTests {
    @Test
    public void checkLowerVersion() {
        Assertions.assertTrue(UpdateChecker.isOutOfDate("1.1.12", "1.1.13"));
        Assertions.assertTrue(UpdateChecker.isOutOfDate("1.1.12", "1.2.12"));
        Assertions.assertTrue(UpdateChecker.isOutOfDate("1.1.12", "2.1.12"));
        Assertions.assertTrue(UpdateChecker.isOutOfDate("1.1.12", "2.0.0"));
    }

    @Test
    public void checkHigherVersion() {
        // These will only happen if you run a dev snapshot, but it's enough to annoy me when I'm reloading so it's included.
        Assertions.assertFalse(UpdateChecker.isOutOfDate("1.1.13", "1.1.12"));
        Assertions.assertFalse(UpdateChecker.isOutOfDate("1.2.12", "1.1.12"));
        Assertions.assertFalse(UpdateChecker.isOutOfDate("2.1.12", "1.1.12"));
        Assertions.assertFalse(UpdateChecker.isOutOfDate("2.0.0", "1.1.12"));
    }

    @Test
    public void checkSameVersion() {
        Assertions.assertFalse(UpdateChecker.isOutOfDate("1.1.12", "1.1.12"));
        Assertions.assertFalse(UpdateChecker.isOutOfDate("1.2.12", "1.2.12"));
        Assertions.assertFalse(UpdateChecker.isOutOfDate("2.1.12", "2.1.12"));
        Assertions.assertFalse(UpdateChecker.isOutOfDate("2.0.0", "2.0.0"));
    }
}
