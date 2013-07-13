package unwrittenfun.bukkit.lukkit.offlineplayer;

import org.bukkit.OfflinePlayer;

import unwrittenfun.bukkit.lukkit.LukkitObject;
import unwrittenfun.bukkit.lukkit.serveroperator.ServerOperatorObject;

public class OfflinePlayerObject extends LukkitObject {
	public OfflinePlayer offlineplayer;

	public OfflinePlayerObject(OfflinePlayer o) {
		offlineplayer = o;

		extendWith(new ServerOperatorObject(offlineplayer));

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

	@Override
	public Object getObject() {
		return offlineplayer;
	}
}
