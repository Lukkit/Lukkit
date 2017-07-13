package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import nz.co.jammehcow.lukkit.environment.plugin.wrappedClasses.Skull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * @author jammehcow
 */

public class BukkitWrapper extends LuaTable {
    // TODO: wrap important Bukkit classes (e.g. Material)
    private LukkitPlugin plugin;

    public BukkitWrapper(LukkitPlugin plugin) {
        this.plugin = plugin;

        set("getMaterial", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue material) {
                Material m = Material.getMaterial(material.checkjstring());

                if (m == null) {
                    plugin.getLogger().warning("Requested material " + material.tojstring() + " but a material by that name doesn't exist.");
                    return LuaValue.NIL;
                }

                return CoerceJavaToLua.coerce(m);
            }
        });

        set("getSkullMeta", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue item) {
                if (!item.isnil() && !(item.checkuserdata() instanceof ItemStack)) {
                    return LuaValue.NIL;
                }

                return CoerceJavaToLua.coerce(new Skull((item.isnil()) ? null : (ItemStack) item.touserdata()));
            }
        });
    }
}
