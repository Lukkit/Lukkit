package net.lukkit.lukkit.sandbox.api.storage;

public class StorageError {
    public enum Error {
        UNKNOWN_EXTENSION(0),
        FILE_MISSING(1),
        FILE_INVALID(2),
        UNTRACEABLE_PATH(3);

        public final int id;
        Error(int i) {
            id = i;
        }
    }
}
