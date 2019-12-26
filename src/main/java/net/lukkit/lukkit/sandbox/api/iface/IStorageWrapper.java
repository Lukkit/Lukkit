package net.lukkit.lukkit.sandbox.api.iface;

public interface IStorageWrapper {
    // using objects in case we need to return an error enum
    Object getArray(String path);
    Object getArrayOrDefault(String path, Object defaultReturn);
    Object getString(String path);
    Object getStringOrDefault(String path, Object defaultReturn);
    Object getNumber(String path);
    Object getNumberOrDefault(String path, Object defaultReturn);
    Object getBoolean(String path);
    Object getBooleanOrDefault(String path, Object defaultReturn);

    void setArray(String path, Object arrayObject);
    void setString(String path, Object stringObject);
    void setNumber(String path, Object numberObject);
    void setBoolean(String path, Object numberObject);
}
