package unwrittenfun.minecraft.lukkit.objects;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import unwrittenfun.minecraft.lukkit.environment.ILukkitObject;

/**
 * Bukkit Plugin: Lukkit
 * Author: UnwrittenFun
 */
public class ServerObject implements ILukkitObject {
    @Override
    public String getName() {
        return "server";
    }

    @Override
    public String[] getMethods() {
        return new String[]{
                "listMethods", "banIp",
                "broadcast",
                "clearRecipes",
                "getAllowEnd",
                "getAllowFlight",
                "getAllowNether",
                "getAmbientSpawnLimit",
                "getAnimalSpawnLimit",
                "getBukkitVersion",
                "getConnectionThrottle",
                "getDefaultGameMode",
                "getGenerateStructures",
                "getIp", "getMaxPlayers",
                "getMonsterSpawnLimit",
                "getMotd", "getName",
                "getOnlineMode", "getPort",
                "getServerId", "getServerName",
                "getShutdownMessage",
                "getSpawnRadius",
                "getTicksPerAnimalSpawns",
                "getTicksPerMonsterSpawns",
                "getUpdateFolder",
                "getVersion", "getViewDistance",
                "getWaterAnimalSpawnLimit",
                "getWorldType", "hasWhitelist",
                "isHardcore", "isPrimaryThread",
                "reload", "reloadWhitelist",
                "resetRecipes", "savePlayers",
                "setSpawnRadius", "setWhitelist",
                "shutdown", "unbanIp",
                "unloadWorld",
                "useExactLoginLocation"
        };
    }

    @Override
    public Varargs doMethod(int methodIndex, String methodName, Varargs args) {
        switch (methodIndex) {
            case 1: // banIp
                Bukkit.getServer().banIP(args.tojstring(1));
                break;
            case 2: // broadcast
                if (args.narg() == 2) {
                    return LuaValue.valueOf(Bukkit.getServer().broadcast(args.tojstring(1), args.tojstring(2)));
                }
                return LuaValue.valueOf(Bukkit.getServer().broadcastMessage(args.tojstring(1)));
            case 3: // clearRecipes
                Bukkit.getServer().clearRecipes();
                break;
            case 4: // getAllowEnd
                return LuaValue.valueOf(Bukkit.getServer().getAllowEnd());
            case 5: // getAllowFlight
                return LuaValue.valueOf(Bukkit.getServer().getAllowFlight());
            case 6: // getAllowNether
                return LuaValue.valueOf(Bukkit.getServer().getAllowNether());
            case 7: // getAmbientSpawnLimit
                return LuaValue.valueOf(Bukkit.getServer().getAmbientSpawnLimit());
            case 8: // getAnimalSpawnLimit
                return LuaValue.valueOf(Bukkit.getServer().getAnimalSpawnLimit());
            case 9: // getBukkitVersion
                return LuaValue.valueOf(Bukkit.getServer().getBukkitVersion());
            case 10: // getConnectionThrottle
                return LuaValue.valueOf(Bukkit.getServer().getConnectionThrottle());
            case 11: // getDefaultGameMode
                return LuaValue.valueOf(Bukkit.getServer().getDefaultGameMode().ordinal());
            case 12: // getGenerateStructures
                return LuaValue.valueOf(Bukkit.getServer().getGenerateStructures());
            case 13: // getIp
                return LuaValue.valueOf(Bukkit.getServer().getIp());
            case 14: // getMaxPlayers
                return LuaValue.valueOf(Bukkit.getServer().getMaxPlayers());
            case 15: // getMonsterSpawnLimit
                return LuaValue.valueOf(Bukkit.getServer().getMonsterSpawnLimit());
            case 16: // getMotd
                return LuaValue.valueOf(Bukkit.getServer().getMotd());
            case 17: // getName
                return LuaValue.valueOf(Bukkit.getServer().getName());
            case 18: // getOnlineMode
                return LuaValue.valueOf(Bukkit.getServer().getOnlineMode());
            case 19: // getPort
                return LuaValue.valueOf(Bukkit.getServer().getPort());
            case 20: // getServerId
                return LuaValue.valueOf(Bukkit.getServer().getServerId());
            case 21: // getServerName
                return LuaValue.valueOf(Bukkit.getServer().getServerName());
            case 22: // getShutdownMessage
                return LuaValue.valueOf(Bukkit.getServer().getShutdownMessage());
            case 23: // getSpawnRadius
                return LuaValue.valueOf(Bukkit.getServer().getSpawnRadius());
            case 24: // getTicksPerAnimalSpawns
                return LuaValue.valueOf(Bukkit.getServer().getTicksPerAnimalSpawns());
            case 25: // getTicksPerMonsterSpawns
                return LuaValue.valueOf(Bukkit.getServer().getTicksPerMonsterSpawns());
            case 26: // getUpdateFolder
                return LuaValue.valueOf(Bukkit.getServer().getUpdateFolder());
            case 27: // getVersion
                return LuaValue.valueOf(Bukkit.getServer().getVersion());
            case 28: // getViewDistance
                return LuaValue.valueOf(Bukkit.getServer().getViewDistance());
            case 29: // getWaterAnimalSpawnLimit
                return LuaValue.valueOf(Bukkit.getServer().getWaterAnimalSpawnLimit());
            case 30: // getWorldType
                return LuaValue.valueOf(Bukkit.getServer().getWorldType());
            case 31: // hasWhitelist
                return LuaValue.valueOf(Bukkit.getServer().hasWhitelist());
            case 32: // isHardcore
                return LuaValue.valueOf(Bukkit.getServer().isHardcore());
            case 33: // isPrimaryThread
                return LuaValue.valueOf(Bukkit.getServer().isPrimaryThread());
            case 34: // reload
                Bukkit.getServer().reload();
                break;
            case 35: // reloadWhitelist
                Bukkit.getServer().reloadWhitelist();
                break;
            case 36: // resetRecipes
                Bukkit.getServer().resetRecipes();
                break;
            case 37: // savePlayers
                Bukkit.getServer().savePlayers();
                break;
            case 38: // setSpawnRadius
                Bukkit.getServer().setSpawnRadius(args.toint(1));
                break;
            case 39: // setWhitelist
                Bukkit.getServer().setWhitelist(args.toboolean(1));
                break;
            case 40: // shutdown
                Bukkit.getServer().shutdown();
                break;
            case 41: // unbanIp
                Bukkit.getServer().unbanIP(args.tojstring(1));
                break;
            case 42: // unloadWorld
                return LuaValue.valueOf(Bukkit.getServer().unloadWorld(args.tojstring(1), args.toboolean(2)));
            case 43: // useExactLoginLocation
                return LuaValue.valueOf(Bukkit.getServer().useExactLoginLocation());
        }

        return LuaValue.NIL;
    }
}
