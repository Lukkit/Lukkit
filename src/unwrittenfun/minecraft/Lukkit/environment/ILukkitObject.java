package unwrittenfun.minecraft.lukkit.environment;

import org.luaj.vm2.Varargs;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public interface ILukkitObject {
    /**
     * @return Name of object to be used in lua
     */
    public String getName();

    /**
     * Define the list of methods that are available from this object.
     * The index of the method will be passed into doMethod
     *
     * @return List of methods
     */
    public String[] getMethods();

    /**
     * Handles the call the the function from lua
     *
     * @param methodIndex Index of the method called
     * @param methodName Name of the method called
     * @param args Arguments passed with the call
     * @return The arguments to return in lua
     */
    public Varargs doMethod(int methodIndex, String methodName, Varargs args);
}
