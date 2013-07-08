package unwrittenfun.bukkit.lukkit;

import java.io.File;
import java.util.Locale;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.jse.JsePlatform;

import unwrittenfun.bukkit.lukkit.server.ServerObject;

public final class Lukkit extends JavaPlugin {
	private Globals _G;
	public static Logger logger;
	public static Lukkit plugin;

	@Override
	public void onEnable() {
		plugin = this;
		logger = getLogger();
		
		loadEnvironment();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("lukkit")) {
			if (args[0].equalsIgnoreCase("reload")) {
				HandlerList.unregisterAll(this);
				loadEnvironment();
				return true;
			}
			return false;
		}
		return false;
	}
	
	private void loadEnvironment() {
		_G = JsePlatform.standardGlobals();
		
		LuaC.install();
		_G.compiler = LuaC.instance;
		
		_G.set("server", new ServerObject());
		
		loadPlugins();
	}

	private void loadPlugins() {
		File data = getDataFolder();
		if (!data.exists()) { data.mkdir(); }
		
		for (File pluginFile : data.listFiles()) {
			if (pluginFile.isFile() && pluginFile.getName().toLowerCase(Locale.ENGLISH).endsWith(".lua")) {
				try {
					LuaValue chunk = _G.loadFile(pluginFile.getAbsolutePath());
					if (chunk.isnil()) {
						throw new LuaError(chunk.tojstring(2));
					}
					chunk.call(pluginFile.getPath());
				} catch (LuaError e) {
					logger.warning(e.getMessage());
				}
			}
		}
	}
}
