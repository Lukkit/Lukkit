package lua_api.storage;

/**
 * Allows unification of tests across all Storage types by using a standard set of methods to match a standard set of
 *  requirements
 */
interface StorageTestBase {
    /**
     * Verify that a known good file loads fine
     */
    void validFileFormatTest();

    /**
     * Verify that a known bad file doesn't load and throws
     */
    void invalidFileFormatTest();

    /**
     * Verify that valid paths resolve
     */
    void getValidPathTest();

    /**
     * Verify that invalid paths don't resolve and throw
     */
    void getInvalidPathTest();

    /**
     * Verify that invalid paths don't resolve but return the default specified
     */
    void getInvalidPathDefaultTest();

    /**
     * Verify that arrays at a valid path can be resolved and returned
     */
    void getValidArrayTest();

    /**
     * Verify that arrays at an invalid path cannot be resolved and throw
     */
    void getInvalidArrayTest();

    /**
     * Verify that arrays at an invalid path cannot be resolved but return the default specified
     */
    void getInvalidArrayDefaultTest();

    /**
     * Verify that strings at a valid path can be resolved and returned
     */
    void getValidStringTest();

    /**
     * Verify that strings at an invalid path cannot be resolved and throw
     */
    void getInvalidStringTest();

    /**
     * Verify that strings at a valid path cannot be resolved but return the default specified
     */
    void getInvalidStringDefaultTest();

    /**
     * Verify that numbers (int, float, double) at a valid path can be resolved and returned
     */
    void getValidNumberTest();

    /**
     * Verify that numbers (int, float, double) at an invalid path cannot be resolved and throw
     */
    void getInvalidNumberTest();

    /**
     * Verify that numbers (int, float, double) at a valid path cannot be resolved but return the default specified
     */
    void getInvalidNumberDefaultTest();

    /**
     * Verify that booleans at a valid path can be resolved and returned
     */
    void getValidBooleanTest();

    /**
     * Verify that strings at an invalid path cannot be resolved and throw
     */
    void getInvalidBooleanTest();

    /**
     * Verify that booleans at a valid path cannot be resolved but return the default specified
     */
    void getInvalidBooleanDefaultTest();
}
