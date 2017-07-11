package nz.co.jammehcow.lukkit.environment.wrappers;

import nz.co.jammehcow.lukkit.environment.plugin.LukkitPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
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
                if (!item.isnil() && !(item.checkuserdata() instanceof ItemStack)) {
                    return LuaValue.NIL;
                }

                return CoerceJavaToLua.coerce(new Skull((item.isnil()) ? null : (ItemStack) item.touserdata()));
            }
        });
    }

    class Skull extends LuaTable {
        private ItemStack skull;
        private SkullMeta meta;

        Skull(ItemStack skull) {
            this.skull = (skull == null) ? new ItemStack(Material.SKULL_ITEM, 1) : skull;

            if (skull == null) {
                this.meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
            } else {
                this.meta = (SkullMeta) this.skull.getItemMeta();
            }

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
                    if (meta.getOwner() == null) {
                        return LuaValue.NIL;
                    }
                    return LuaValue.valueOf(meta.getOwner());
                }
            });

            this.set("getItemStack", new ZeroArgFunction() {
                @Override
                public LuaValue call() {
                    skull.setItemMeta(meta);
                    return CoerceJavaToLua.coerce(skull);
                }
            });
        }
    }
}
