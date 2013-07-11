package unwrittenfun.bukkit.lukkit.player;

import org.bukkit.entity.Player;

import unwrittenfun.bukkit.lukkit.LukkitObject;
import unwrittenfun.bukkit.lukkit.offlineplayer.OfflinePlayerObject;

public class PlayerObject extends LukkitObject {
	public Player player;
	
	public PlayerObject(Player p) {
		player = p;
		
		extendWith(new OfflinePlayerObject(player));
		
//		set("awardAchievement", new AwardAchievmentFunction());
		set("chat", new ChatFunction());
		set("hidePlayer", new HidePlayerFunction());
		set("showPlayer", new ShowPlayerFunction());
		set("kickPlayer", new KickPlayerFunction());
		set("loadPlayer", new LoadPlayerFunction());
		set("performCommand", new PerformCommandFunction());
//		set("playEffect", new PlayEffectFunction());
//		set("playNote", new PlayNoteFunction());
//		set("playSound", new PlaySoundFunction());
		set("resetPlayerTime", new ResetPlayerTimeFunction());
		set("resetPlayerWeather", new ResetPlayerWeatherFunction());
		set("saveData", new SaveDataFunction());
//		set("sendBlockChange", new SendBlockChangeFunction());
//		set("sendChunkChange", new SendChunkChangeFunction());
//		set("sendMap", new SendMapFunction());
		set("sendRawMessage", new SendRawMessageFunction());
		
		set("allowFlight", new GetAllowFlightFunction());
		set("canSee", new CanSeeFunction());
		set("isFlying", new IsFlyingFunction());
		set("isPlayerTimeRelative", new IsPlayerTimeRelativeFunction());
		set("isScaledHealth", new IsScaledHealthFunction());
		set("isSleepingIgnored", new IsSleepingIgnoredFunction());
		set("isSneaking", new IsSneakingFunction());
		set("isSprinting", new IsSprintingFunction());

		set("getBedSpawnLocation", new GetBedSpawnLocationFunction());
		set("getCompassTarget", new GetCompassTargetFunction());
		set("getDisplayName", new GetDisplayNameFunction());
		set("getExhaustion", new GetExhaustionFunction());
		set("getExp", new GetExpFunction());
		set("getFlySpeed", new GetFlySpeedFunction());
		set("getFoodLevel", new GetFoodLevelFunction());
		set("getLevel", new GetLevelFunction());
		set("getPlayerListName", new GetPlayerListNameFunction());
		set("getPlayerTime", new GetPlayerTimeFunction());
		set("getPlayerTimeOffset", new GetPlayerTimeOffsetFunction());
//		set("getPlayerWeather", new GetPlayerWeatherFunction());
		set("getSaturation", new GetSaturationFunction());
//		set("getScoreboard", new GetScoreboardFunction());
		set("getTotalExperience", new GetTotalExperienceFunction());
		set("getWalkSpeed", new GetWalkSpeedFunction());

		set("giveExp", new GiveExpFunction());
		set("giveExpLevel", new GiveExpLevelsFunction());
		set("setAllowFlight", new SetAllowFlightFunction());
		set("setBedSpawnLocation", new SetBedSpawnLocationFunction());
		set("setCompassTarget", new SetCompassTargetFunction());
		set("setDisplayName", new SetDisplayNameFunction());
		set("setExhaustion", new SetExhaustionFunction());
		set("setExp", new SetExpFunction());
		set("setFlying", new SetFlyingFunction());
		set("setFlySpeed", new SetFlySpeedFunction());
		set("setFoodLevel", new SetFoodLevelFunction());
		set("setLevel", new SetLevelFunction());
		set("setPlayerListName", new SetPlayerListNameFunction());
		set("setPlayerTime", new SetPlayerTimeFunction());
//		set("setPlayerWeather", new SetPlayerWeatherFunction());
		set("setSaturation", new SetSaturationFunction());
		set("setScaleHealth", new SetScaledHealthFunction());
//		set("setScoreboard", new SetScoreboardFunction());
		set("setSleepingIgnored", new SetSleepingIgnoredFunction());
		set("setSneaking", new SetSneakingFunction());
		set("setSprinting", new SetSprintingFunction());
		set("setTexturePack", new SetTexturePackFunction());
		set("setTotalExperience", new SetTotalExperienceFunction());
		set("setWalkSpeed", new SetWalkSpeedFunction());
	}

	@Override
	public Object getObject() {
		return player;
	}
}
