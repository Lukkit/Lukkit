package net.lukkit.lukkit.sandbox.api.storage;

public abstract class IStorageModule {
    IStorageModule(String path, Object options) {
        // Stub
    }

    IStorageModule(String path) {
        this(path, null);
    }

    // using objects in case we need to return an error enum
    abstract Object getArray(String path);
    abstract Object getArray(String path, Object defaultReturn);
    abstract Object getString(String path);
    abstract Object getString(String path, Object defaultReturn);
    abstract Object getNumber(String path);
    abstract Object getNumber(String path, Object defaultReturn);
    abstract Object getBoolean(String path);
    abstract Object getBoolean(String path, Object defaultReturn);

    abstract void setArray(String path, Object arrayObject);
    abstract void setString(String path, Object stringObject);
    abstract void setNumber(String path, Object numberObject);
    abstract void setBoolean(String path, Object numberObject);
}
