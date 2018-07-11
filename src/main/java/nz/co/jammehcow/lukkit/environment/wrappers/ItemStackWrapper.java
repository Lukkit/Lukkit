package nz.co.jammehcow.lukkit.environment.wrappers;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class ItemStackWrapper extends LuaTable {

    private final ItemStack item;
    private ItemMeta meta;

    public ItemStackWrapper(Material mat) {
        this(new ItemStack(mat));
    }

    public ItemStackWrapper(Material mat, int amt) {
        this(new ItemStack(mat, amt));
    }

    public ItemStackWrapper(Material mat, int amt, short d) {
        this(new ItemStack(mat, amt, d));
    }

    public ItemStackWrapper(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();

        this.set("getData", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(item.getData());
            }
        });

        this.set("getEnchantments", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(item.getEnchantments());
            }
        });

        this.set("getType", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(item.getType());
            }
        });

        this.set("getAmount", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(item.getAmount());
            }
        });

        this.set("getDurability", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(item.getDurability());
            }
        });

        this.set("getMaxStackSize", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(item.getMaxStackSize());
            }
        });

        this.set("getEnchantmentLevel", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue value) {
                return CoerceJavaToLua.coerce(item.getEnchantmentLevel((Enchantment) value.checkuserdata(Enchantment.class)));
            }
        });

        this.set("getBukkitItemStack", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(getBukkitItemStack());

            }
        });

        this.set("setAmount", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue value) {
                item.setAmount(value.checkint());
                return NIL;
            }
        });

        this.set("setData", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue value) {
                item.setData((MaterialData) value.checkuserdata(MaterialData.class));
                return NIL;
            }
        });

        this.set("setDurability", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue value) {
                item.setDurability(value.checknumber().toshort());
                return NIL;
            }
        });

        this.set("setType", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue value) {
                item.setType((Material) value.checkuserdata(Material.class));
                return NIL;
            }
        });

        // Item Meta
        this.set("getItemMeta", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                if (meta instanceof SkullMeta) {
                    return CoerceJavaToLua.coerce((SkullMeta) meta);
                } else if (meta instanceof BannerMeta) {
                    return CoerceJavaToLua.coerce((BannerMeta) meta);
                } else if (meta instanceof FireworkMeta) {
                    return CoerceJavaToLua.coerce((FireworkMeta) meta);
                } else {
                    return CoerceJavaToLua.coerce(meta);
                }
            }
        });
        this.set("setItemMeta", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue value) {
                meta = (ItemMeta) value.checkuserdata(ItemMeta.class);
                item.setItemMeta(meta);
                return NIL;
            }
        });
    }

    public ItemStack getBukkitItemStack() {
        item.setItemMeta(meta);
        return item;
    }
}
