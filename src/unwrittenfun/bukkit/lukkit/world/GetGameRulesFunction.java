package unwrittenfun.bukkit.lukkit.world;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;


public class GetGameRulesFunction extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		String[] rules = ((WorldObject) args.arg(1)).world.getGameRules();
		LuaValue[] rulesList = new LuaValue[rules.length];
		for (int i = 0; i < rules.length; i++) {
			rulesList[i] = LuaValue.valueOf(rules[i]);
		}
		return LuaValue.listOf(rulesList);
	}
	
}
