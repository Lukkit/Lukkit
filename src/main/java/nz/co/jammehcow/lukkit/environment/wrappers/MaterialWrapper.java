package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment.ObjectType;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * @author jammehcow
 */

public class MaterialWrapper extends LuaTable {

    private LukkitPlugin plugin;

    public MaterialWrapper(LukkitPlugin plugin) {
        this.plugin = plugin;

        set("valueOf", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue c) {
                return CoerceJavaToLua.coerce(Material.valueOf(c.checkjstring()));
            }
        });

        set("matchMaterial", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue c) {
                return CoerceJavaToLua.coerce(Material.matchMaterial(c.checkjstring()));
            }
        });

        set("getMaterial", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue c) {
                return CoerceJavaToLua.coerce(Material.getMaterial(c.checkjstring()));
            }
        });

        set("getMaterialById", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue c) {
                plugin.getLogger().warning("getMaterialByID(number) is deprecated. getMaterial(name) should be used instead");
                return CoerceJavaToLua.coerce(Material.getMaterial(c.checkint()));
            }
        });

        set("values", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue c) {
                return CoerceJavaToLua.coerce(Material.values());
            }
        });

        // Add every mat to the table
        for (Material mat : Material.values())
            set(mat.name(), CoerceJavaToLua.coerce(mat));


    }

    @Override
    public String typename() {
        return ObjectType.WRAPPER.name;
    }

    @Override
    public int type() {
        return ObjectType.WRAPPER.type;
    }
}
