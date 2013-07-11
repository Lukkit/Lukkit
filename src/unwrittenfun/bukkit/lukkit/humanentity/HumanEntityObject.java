package unwrittenfun.bukkit.lukkit.humanentity;

import org.bukkit.entity.HumanEntity;

import unwrittenfun.bukkit.lukkit.LukkitObject;

public class HumanEntityObject extends LukkitObject {
	public HumanEntity humanentity;

	public HumanEntityObject(HumanEntity h) {
		humanentity = h;

		set("closeInventory", new CloseInventoryFunction());
//		set("getEnderChest", new GetEnderChestFunction());
		set("getExpToLevel", new GetExpToLevelFunction());
		set("getGameMode", new GetGameModeFunction());
//		set("getInventory", new GetInventoryFunction());
//		set("getItemInHand", new GetItemInHandFunction());
//		set("getItemOnCursor", new GetItemOnCursorFunction());
		set("getName", new GetNameFunction());
//		set("getOpenInventory", new GetOpenInventoryFunction());
		set("getSleepTicks", new GetSleepTicksFunction());
		set("isBlocking", new IsBlockingFunction());
		set("isSleeping", new IsSleepingFunction());
//		set("openEnchanting", new OpenEnchantingFunction());
//		set("openInventory", new OpenInventoryFunction());
//		set("openWorkbench", new OpenWorkbenchFunction());
		set("setGameMode", new SetGameModeFunction());
//		set("setItemInHand", new SetItemInHandFunction());
//		set("setItemOnCursor", new SetItemOnCursorFunction());
//		set("setWindowProperty", new SetWindowPropertyFunction());
	}
	
	@Override
	public Object getObject() {
		return humanentity;
	}
}
