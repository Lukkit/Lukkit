package nz.co.jammehcow.lukkit.environment.plugin.wrappedClasses;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * @author AL_1
 */

public class Banner extends LuaTable {
    private ItemStack banner;
    private BannerMeta meta;
    private Banner self;

    public Banner(ItemStack bannerItem) {
        this.banner = (bannerItem == null) ? new ItemStack(Material.BANNER, 1) : banner;
        this.self = this; // Allows access from setOwner() since this is populate by the function.

        if (bannerItem == null) {
            this.banner.setDurability((short) 3);
            this.meta = (BannerMeta) Bukkit.getItemFactory().getItemMeta(Material.BANNER);
        } else {
            this.meta = (BannerMeta) this.banner.getItemMeta();
        }

        this.set("addPattern", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue pattern) {
                // TODO
                return CoerceJavaToLua.coerce(self);
            }
        });

        this.set("setPatterns", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue patterns) {
                // TODO
                return CoerceJavaToLua.coerce(self);
            }
        });

        this.set("setPattern", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue pattern) {
                // TODO
                return CoerceJavaToLua.coerce(self);
            }
        });

        this.set("removePattern", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue num) {
                meta.removePattern(num.checkint());
                return CoerceJavaToLua.coerce(self);
            }
        });

        this.set("numberOfPatterns", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(meta.numberOfPatterns());
            }
        });
    }
}
