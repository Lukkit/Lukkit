package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.LuaEnvironment.ObjectType;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import nz.co.jammehcow.lukkit.environment.plugin.LukkitPluginException;
import nz.co.jammehcow.lukkit.environment.plugin.wrappedClasses.Banner;
import nz.co.jammehcow.lukkit.environment.plugin.wrappedClasses.Skull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * @author jammehcow
 */

public class BukkitWrapper extends LuaTable {
    // TODO: wrap important Bukkit classes (e.g. Material)
    private LukkitPlugin plugin;

    public BukkitWrapper(LukkitPlugin plugin) {
        this.plugin = plugin;

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

        set("getServer", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(Bukkit.getServer());
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
