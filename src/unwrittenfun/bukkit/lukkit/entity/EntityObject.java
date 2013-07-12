package unwrittenfun.bukkit.lukkit.entity;

import org.bukkit.entity.Entity;

import unwrittenfun.bukkit.lukkit.LukkitObject;

public class EntityObject extends LukkitObject {
	public Entity entity;

	public EntityObject(Entity e) {
		entity = e;

		set("eject", new EjectFunction());
		set("getEntityId", new GetEntityIdFunction());
		set("getFallDistance", new GetFallDistanceFunction());
		set("getFireTicks", new GetFireTicksFunction());
//		set("getLastDamageCause", new GetLastDamageCauseFunction());
		set("getLocation", new GetLocationFunction());
		set("getMaxFireTicks", new GetMaxFireTicksFunction());
		set("getNearbyEntities", new GetNearbyEntitiesFunction());
//		set("getPassenger", new GetPassengerFunction());
		set("getServer", new GetServerFunction());
		set("getTicksLived", new GetTicksLivedFunction());
//		set("getType", new GetTypeFunction());
//		set("getUniqueId", new GetUniqueIdFunction());
//		set("getVehicle", new GetVehicleFunction());
		set("getVelocity", new GetVelocityFunction());
		set("getWorld", new GetWorldFunction());
		set("isDead", new IsDeadFunction());
		set("isEmpty", new IsEmptyFunction());
		set("isInsideVehicle", new IsInsideVehicleFunction());
		set("isOnGround", new IsOnGroundFunction());
		set("isValid", new IsValidFunction());
		set("leaveVehicle", new LeaveVehicleFunction());
//		set("playEffect", new PlayEffectFunction());
		set("remove", new RemoveFunction());
		set("setFallDistance", new SetFallDistanceFunction());
		set("setFireTicks", new SetFireTicksFunction());
//		set("setLastDamageCause", new SetLastDamageCauseFunction());
//		set("setPassenger", new SetPassengerFunction());
		set("setTicksLived", new SetTicksLivedFunction());
		set("setVelocity", new SetVelocityFunction());
		set("teleport", new TeleportFunction());

	}
	
	@Override
	public Object getObject() {
		return entity;
	}
}
