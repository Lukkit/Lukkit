import nz.co.jammehcow.lukkit.UpdateChecker;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author jammehcow
 */

public class UpdateCheckerTests {
    @Test
    public void checkLowerVersion() {
        Assert.assertTrue(UpdateChecker.isOutOfDate("1.1.12", "1.1.13"));
        Assert.assertTrue(UpdateChecker.isOutOfDate("1.1.12", "1.2.12"));
        Assert.assertTrue(UpdateChecker.isOutOfDate("1.1.12", "2.1.12"));
        Assert.assertTrue(UpdateChecker.isOutOfDate("1.1.12", "2.0.0"));
    }

    @Test
    public void checkHigherVersion() {
        // These will only happen if you run a dev snapshot, but it's enough to annoy me when I'm reloading so it's included.
        Assert.assertFalse(UpdateChecker.isOutOfDate("1.1.13", "1.1.12"));
        Assert.assertFalse(UpdateChecker.isOutOfDate("1.2.12", "1.1.12"));
        Assert.assertFalse(UpdateChecker.isOutOfDate("2.1.12", "1.1.12"));
        Assert.assertFalse(UpdateChecker.isOutOfDate("2.0.0", "1.1.12"));
    }
}
