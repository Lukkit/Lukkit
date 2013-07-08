package unwrittenfun.bukkit.lukkit.server;

import org.luaj.vm2.LuaTable;

public class ServerObject extends LuaTable {
	public ServerObject() {
//		set("addRecipe", new AddRecipeFunction());
		set("banIP", new BanIPFunction());
		set("broadcast", new BroadcastFunction());
		set("clearRecipes", new ClearRecipesFunction());
//		set("createInventory", new CreateInventoryFunction());
//		set("createMap", new CreateMapFunction());
//		set("createWorld", new CreateWorldFunction());
//		set("dispatchCommand", new DispatchCommandFunction());
		set("matchPlayer", new MatchPlayerFunction());
		set("reload", new ReloadFunction());
		set("reloadWhitelist", new ReloadWhitelistFunction());
		set("resetRecipes", new ResetRecipesFunction());
		set("savePlayers", new SavePlayersFunction());
		set("shutdown", new ShutdownFunction());
		set("unbanIP", new UnbanIPFunction());
//		set("unloadWorld", new UnloadWordFunction());
		set("userExactLoginLocation", new UseExactLoginLocationFunction());
		
		set("allowEnd", new AllowEndFunction());
		set("allowFlight", new AllowFlightFunction());
		set("allowNether", new AllowNetherFunction());
		set("generateStructures", new GenerateStructuresFunction());
		set("hasWhitelist", new HasWhitelistFunction());
		set("isHardcore", new IsHardcoreFunction());
		set("isPrimaryThread", new IsPrimaryThreadFunction());
		set("isOnline", new IsOnlineFunction());
		
		set("getAmbientSpawnLimit", new GetAmbientSpawnLimitFunction());
		set("getAnimalSpawnLimit", new GetAnimalSpawnLimitFunction());
//		set("getBannedPlayers", new GetBannedPlayersFunction());
		set("getBukkitVersion", new GetBukkitVersionFunction());
//		set("getCommandAliases", new GetCommandAliasesFunction());
		set("getConnectionThrottle", new GetConnectionThrottleFunction());
//		set("getConsoleSender", new GetConsoleSenderFunction());
		set("getDefaultGameMode", new GetDefaultGameModeFunction());
//		set("getHelpMap", new GetHelpMapFunction());
		set("getIP", new GetIPFunction());
		set("getIPBans", new GetIPBansFunction());
//		set("getItemFactory", new GetItemFactoryFunction());
//		set("getMap", new GetMapFunction());
		set("getMaxPlayers", new GetMaxPlayersFunction());
//		set("getMessenger", new GetMessengerFunction());
		set("getMonsterSpawnLimit", new GetMonsterSpawnLimitFunction());
		set("getMotd", new GetMotdFunction());
		set("getName", new GetNameFunction());
//		set("getOfflinePlayer", new GetOfflinePlayerFunction());
//		set("getOfflinePlayers", new GetOfflinePlayersFunction());
		set("getOnlinePlayers", new GetOnlinePlayersFunction());
//		set("getOps", new GetOpsFunction());
		set("getPlayer", new GetPlayerFunction());
		set("getPlayerExact", new GetPlayerExactFunction());
		set("getPort", new GetPortFunction());
//		set("getRecipesFor", new GetRecicpesForFunction());
//		set("getScoreboardManager", new GetScoreboardManagerFunction());
		set("getServerId", new GetServerIdFunction());
		set("getServerName", new GetServerNameFunction());
		set("getShutdownMessage", new GetShutdownMessageFunction());
		set("getSpawnRadius", new GetSpawnRadiusFunction());
		set("getTicksPerAnimalSpawns", new GetTicksPerAnimalSpawnsFunction());
		set("getTicksPerMonsterSpawns", new GetTicketsPerMonsterSpawnsFunction());
		set("getUpdateFolder", new GetUpdateFolderFunction());
		set("getVersion", new GetVersionFunction());
		set("getViewDistance", new GetViewDistanceFunction());
//		set("getWarningState", new GetWarningStateFunction());
		set("getWaterAnimalSpawnLimit", new GetWaterAnimalSpawnLimitFunction());
//		set("getWhitlistedPlayers", new GetWhitelistedPlayersFunction());
//		set("getWorld", new GetWorldFunction());
//		set("getWorldContainer", new GetWorldContainerFunction());
//		set("getWorlds", new GetWorldsFunction());
		set("getWorldType", new GetWorldTypeFunction());
		
		set("setDefaultGameMode", new SetDefaultGameModeFunction());
		set("setSpawnRadius", new SetSpawnRadiusFunction());
		set("setWhitelist", new SetWhitelistFunction());
	}
}
