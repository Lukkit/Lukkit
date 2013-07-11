package unwrittenfun.bukkit.lukkit.offlineplayer;

import org.bukkit.OfflinePlayer;
import org.luaj.vm2.LuaTable;

public class OfflinePlayerObject extends LuaTable {
	public OfflinePlayer offlineplayer;

	public OfflinePlayerObject(OfflinePlayer o) {
		offlineplayer = o;

		set("hasPlayedBefore", new HasPlayedBeforeFunction());
		set("isBanned", new IsBannedFunction());
		set("isOnline", new IsOnlineFunction());
		set("isWhitelisted", new IsWhitelistedFunction());
		
		set("getBedSpawnLocation", new GetBedSpawnLocationFunction());
		set("getFirstPlayed", new GetFirstPlayedFunction());
		set("getLastPlayed", new GetLastPlayedFunction());
		set("getName", new GetNameFunction());
		set("getPlayer", new GetPlayerFunction());
		
		set("setBanned", new SetBannedFunction());
		set("setWhitelisted", new SetWhitelistedFunction());
	}
}
