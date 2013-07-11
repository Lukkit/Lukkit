package unwrittenfun.bukkit.lukkit.serveroperator;

import org.bukkit.permissions.ServerOperator;

import unwrittenfun.bukkit.lukkit.LukkitObject;

public class ServerOperatorObject extends LukkitObject {
	public ServerOperator serveroperator;

	public ServerOperatorObject(ServerOperator s) {
		serveroperator = s;

		set("isOp", new IsOpFunction());
		set("setOp", new SetOpFunction());
	}
	
	@Override
	public Object getObject() {
		return serveroperator;
	}
}
