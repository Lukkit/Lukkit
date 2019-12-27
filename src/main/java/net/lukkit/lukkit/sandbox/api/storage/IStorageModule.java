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
    abstract Object getArrayOrDefault(String path, Object defaultReturn);
    abstract Object getString(String path);
    abstract Object getStringOrDefault(String path, Object defaultReturn);
    abstract Object getNumber(String path);
    abstract Object getNumberOrDefault(String path, Object defaultReturn);
    abstract Object getBoolean(String path);
    abstract Object getBooleanOrDefault(String path, Object defaultReturn);

    abstract void setArray(String path, Object arrayObject);
    abstract void setString(String path, Object stringObject);
    abstract void setNumber(String path, Object numberObject);
    abstract void setBoolean(String path, Object numberObject);
}
