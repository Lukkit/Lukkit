package nz.co.jammehcow.lukkit.environment.plugin.wrappedClasses;

import org.bukkit.Bukkit;
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

public class Skull extends LuaTable {
    private ItemStack skull;
    private SkullMeta meta;
    private Skull self;

    public Skull(ItemStack skullItem) {
        this.skull = (skullItem == null) ? new ItemStack(Material.SKULL_ITEM, 1) : skull;
        this.self = this; // Allows access from setOwner() since this is populate by the function.

        if (skullItem == null) {
            this.skull.setDurability((short) 3);
            this.meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        } else {
            this.meta = (SkullMeta) this.skull.getItemMeta();
        }

        this.set("setOwner", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue username) {
                meta.setOwner(username.checkjstring());
                return CoerceJavaToLua.coerce(self);
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
