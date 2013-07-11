package unwrittenfun.bukkit.lukkit.world;

import org.bukkit.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import unwrittenfun.bukkit.lukkit.LukkitObject;


public class GetGameRulesFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		String[] rules = ((World) ((LukkitObject) args.arg(1)).getObject()).getGameRules();
		LuaValue[] rulesList = new LuaValue[rules.length];
		for (int i = 0; i < rules.length; i++) {
			rulesList[i] = LuaValue.valueOf(rules[i]);
		}
		return LuaValue.listOf(rulesList);
	}
	
}
