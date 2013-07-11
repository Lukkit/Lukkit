package unwrittenfun.bukkit.lukkit.livingentity;

import org.bukkit.entity.LivingEntity;

import unwrittenfun.bukkit.lukkit.LukkitObject;

public class LivingEntityObject extends LukkitObject {
	public LivingEntity livingentity;

	public LivingEntityObject(LivingEntity l) {
		livingentity = l;
		
//		set("addPotionEffect", new AddPotionEffectFunction());
//		set("addPotionEffects", new AddPotionEffectsFunction());
//		set("getActivePotionEffects", new GetActivePotionEffectsFunction());
		set("getCanPickupItems", new GetCanPickupItemsFunction());
		set("getCustomName", new GetCustomNameFunction());
//		set("getEquipment", new GetEquipmentFunction());
		set("getEyeHeight", new GetEyeHeightFunction());
		set("getEyeLocation", new GetEyeLocationFunction());
		set("getKiller", new GetKillerFunction());
		set("getLastDamage", new GetLastDamageFunction());
//		set("getLastTwoTargetBlocks", new GetLastTwoTargetBlocksFunction());
//		set("getLineOfSight", new GetLineOfSightFunction());
		set("getMaximumAir", new GetMaximumAirFunction());
		set("getMaximumNoDamageTicks", new GetMaximumNoDamageTicksFunction());
		set("getNoDamageTicks", new GetNoDamageTicksFunction());
		set("getRemainingAir", new GetRemainingAirFunction());
		set("getRemoveWhenFarAway", new GetRemoveWhenFarAwayFunction());
//		set("getTargetBlock", new GetTargetBlockFunction());
//		set("hasLineOfSight", new HasLineOfSightFunction());
//		set("hasPotionEffect", new HasPotionEffectFunction());
		set("isCustomNameVisible", new IsCustomNameVisibleFunction());
//		set("launchProjectile", new LaunchProjectileFunction());
//		set("removePotionEffect", new RemovePotionEffectFunction());
		set("setCanPickupItems", new SetCanPickupItemsFunction());
		set("setCustomName", new SetCustomNameFunction());
		set("setCustomNameVisible", new SetCustomNameVisibleFunction());
		set("setLastDamage", new SetLastDamageFunction());
		set("setMaximumAir", new SetMaximumAirFunction());
		set("setMaximumNoDamageTicks", new SetMaximumNoDamageTicksFunction());
		set("setNoDamageTicks", new SetNoDamageTicksFunction());
		set("setRemainingAir", new SetRemainingAirFunction());
		set("setRemoveWhenFarAway", new SetRemoveWhenFarAwayFunction());
	}
	
	@Override
	public Object getObject() {
		return livingentity;
	}
}
