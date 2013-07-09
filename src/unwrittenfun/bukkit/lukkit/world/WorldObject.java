package unwrittenfun.bukkit.lukkit.world;

import org.bukkit.World;
import org.luaj.vm2.LuaTable;

public class WorldObject extends LuaTable {
	public World world;

	public WorldObject(World w) {
		world = w;
		
		set("createExplosion", new CreateExplosionFunction());
//		set("dropItem", new DropItemFunction());
//		set("dropItemNaturally", new DropItemNaturallyFunction());
//		set("generateTree", new GenerateTreeFunction());
		set("loadChunk", new LoadChunkFunction());
//		set("playEffect", new PlayEffectFunction());
//		set("playSound", new PlaySoundFunction());
		set("refreshChunk", new RefreshChunkFunction());
		set("regenerateChunk", new RegenerateChunkFunction());
		set("save", new SaveFunction());
//		set("spawn", new SpawnFunction());
//		set("spawnArrow", new SpawnArrowFunction());
//		set("spawnCreature", new SpawnCreatureFunction());
//		set("spawnEntity", new SpawnEntityFunction());
		set("spawnFallingBlock", new SpawnFallingBlockFunction()); // TODO: Return FallingBlock
		set("strikeLightning", new StrikeLightningFunction()); // TODO: Return LightningStrike
		set("strikeLightningEffect", new StrikeLightningEffectFunction()); // TODO: Return LightningStrike
		set("unloadChunk", new UnloadChunkFunction());
		set("unloadChunkRequest", new UnloadChunkRequestFunction());
		
		set("canGenerateStructures", new CanGenerateStructuresFunction());
		set("hasStorm", new HasStormFunction());
		set("isAutoSave", new IsAutoSaveFunction());
		set("isChunkInUse", new IsChunkInUseFunction());
		set("isChunkLoaded", new IsChunkLoadedFunction());
		set("isGameRule", new IsGameRuleFunction());
		set("isThundering", new IsThunderingFunction());
		
		set("getAllowAnimals", new GetAllowAnimalsFunction());
		set("getAllowMonsters", new GetAllowMonstersFunction());
		set("getAmbientSpawnLimit", new GetAmbientSpawnLimitFunction());
		set("getAnimalSpawnLimit", new GetAnimalSpawnLimitFunction());
//		set("getBiome", new GetBiomeFunction());
//		set("getBlockAt", new GetBlockAtFunction());
		set("getBlockTypeIdAt", new GetBlockTypeIdAtFunction());
//		set("getChunkAt", new GetChunkAtFunction());
//		set("getDifficulty", new GetDifficultyFunction());
//		set("getEmptyChunkSnapshot", new GetEmptyChunkSnapshotFunction());
//		set("getEntities", new GetEntitiesFunction());
//		set("getEntitiesByClass", new GetEntitiesByClassFunction());
//		set("getEntitiesByClasses", new GetEntitiesByClassesFunction());
//		set("getEnvironment", new GetEnvironmentFunction());
		set("getFullTime", new GetFullTimeFunction());
		set("getGameRules", new GetGameRulesFunction());
		set("getGameRuleValue", new GetGameRuleValueFunction());
//		set("getGenerator", new GetGeneratorFunction());
//		set("getHighestBlockAt", new GetHighestBlockAtFunction());
		set("getHighestBlockYAt", new GetHighestBlockYAtFunction());
		set("getHumidity", new GetHumidityFunction());
		set("getKeepSpawnInMemory", new GetKeepSpawnInMemoryFunction());
//		set("getLivingEntities", new GetLivingEntitiesFunction());
//		set("getLoadedChunks", new GetLoadedChunksFunction());
		set("getMaxHeight", new GetMaxHeightFunction());
		set("getMonsterSpawnLimit", new GetMonsterSpawnLimitFunction());
		set("getName", new GetNameFunction());
		set("getPlayers", new GetPlayersFunction());
//		set("getPopulators", new GetPopulatorsFunction());
		set("getPVP", new GetPVPFunction());
		set("getSeaLevel", new GetSeaLevelFunction());
		set("getSeed", new GetSeedFunction());
		set("getSpawnLocation", new GetSpawnLocationFunction());
		set("getTemperature", new GetTemperatureFunction());
		set("getThunderDuration", new GetThunderDurationFunction());
		set("getTicksPerAnimalSpawns", new GetTicksPerAnimalSpawnsFunction());
		set("getTicksPerMonsterSpawns", new GetTicksPerMonsterSpawnsFunction());
		set("getTime", new GetTimeFunction());
//		set("getUID", new GetUIDFunction());
		set("getWaterAnimalSpawnLimit", new GetWaterAnimalSpawnLimitFunction());
		set("getWeatherDuration", new GetWeatherDurationFunction());
//		set("getWorldFolder", new GetWorldFolderFunction());
//		set("getWorldType", new GetWorldTypeFunction());
		
		set("setAmbientSpawnLimit", new SetAmbientSpawnLimitFunction());
		set("setAnimalSpawnLimit", new SetAnimalSpawnLimitFunction());
		set("setAutoSave", new SetAutoSaveFunction());
//		set("setBiome", new SetBiomeFunction());
//		set("setDifficulty", new SetDifficultyFunction());
		set("setFullTime", new SetFullTimeFunction());
		set("setGameRuleValue", new SetGameRuleValueFunction());
		set("setKeepSpawnInMemory", new SetKeepSpawnInMemoryFunction());
		set("setMonsterSpawnLimit", new SetMonsterSpawnLimitFunction());
		set("setPVP", new SetPVPFunction());
		set("setSpawnFlags", new SetSpawnFlagsFunction());
		set("setSpawnLocation", new SetSpawnLocationFunction());
		set("setStorm", new SetStormFunction());
		set("setThunderDuration", new SetThunderDurationFunction());
		set("setThundering", new SetThunderingFunction());
		set("setTicksPerAnimalSpawns", new SetTicksPerAnimalSpawnsFunction());
		set("setTicksPerMonsterSpawns", new SetTicksPerMonsterSpawnsFunction());
		set("setTime", new SetTimeFunction());
		set("setWaterAnimalSpawnLimit", new SetWaterAnimalSpawnLimitFunction());
		set("setWeatherDuration", new SetWeatherDurationFunction());
		


	}
}
