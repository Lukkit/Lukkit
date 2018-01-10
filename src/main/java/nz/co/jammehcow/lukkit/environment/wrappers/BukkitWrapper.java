package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment.ObjectType;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPluginException;
import nz.co.jammehcow.lukkit.environment.plugin.wrappedClasses.Banner;
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
                    throw new LukkitPluginException("Requested material " + material.tojstring() + " but a material by that name doesn't exist.");
                }

                return CoerceJavaToLua.coerce(m);
            }
        });

        set("getSkullMeta", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue item) {
                if (!item.isnil() && !(item.checkuserdata() instanceof ItemStack)) {
                    throw new LukkitPluginException("bukkit.getSkullMeta was passed something other than an ItemStack.");
                }

                return CoerceJavaToLua.coerce(new Skull((item.isnil()) ? null : (ItemStack) item.touserdata()));
            }
        });

        set("getBannerMeta", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue item) {
                if (!item.isnil() && !(item.checkuserdata() instanceof ItemStack)) {
                    throw new LukkitPluginException("bukkit.getBannerMeta was passed something other than an ItemStack.");
                }

                return CoerceJavaToLua.coerce(new Banner((item.isnil()) ? null : (ItemStack) item.touserdata()));
            }
        });
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
