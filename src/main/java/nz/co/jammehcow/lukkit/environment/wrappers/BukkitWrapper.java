package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
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
                if (!(item.checkuserdata() instanceof ItemStack)) {
                    return LuaValue.NIL;
                }

                return CoerceJavaToLua.coerce(new Skull((ItemStack) item.touserdata()));
            }
        });
    }

    class Skull extends LuaTable {
        private ItemStack skull;
        private SkullMeta meta;

        Skull(ItemStack skull) {
            this.skull = skull;
            this.meta = (SkullMeta) skull.getItemMeta();

            this.set("setOwner", new OneArgFunction() {
                @Override
                public LuaValue call(LuaValue username) {
                    meta.setOwner(username.checkjstring());
                    return LuaValue.NIL;
                }
            });

            this.set("hasOwner", new ZeroArgFunction() {
                @Override
                public LuaValue call() {
                    return LuaValue.valueOf(meta.hasOwner());
                }
            });

            this.set("getOwner", new ZeroArgFunction() {
                @Override
                public LuaValue call() {
                    return LuaValue.valueOf(meta.getOwner());
                }
            });
        }
    }
}
