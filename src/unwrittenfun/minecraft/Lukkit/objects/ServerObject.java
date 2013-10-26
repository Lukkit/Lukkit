package unwrittenfun.minecraft.lukkit.objects;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import unwrittenfun.minecraft.lukkit.bridge.LuaBridge;
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
                "useExactLoginLocation",
                "getPlayer", "getOnlinePlayers",
                "matchPlayer"
        };
    }

    @Override
    public Varargs doMethod(int methodIndex, String methodName, Varargs args) {
        Server server = Bukkit.getServer();
        switch (methodIndex) {
            case 0:
                String methodsString = "";
                for (String method : getMethods()) {
                    methodsString += method + ", ";
                }
                server.broadcastMessage(methodsString);
            case 1: // banIp
                server.banIP(args.tojstring(1)); break;
            case 2: // broadcast
                if (args.narg() == 2) {
                    return LuaBridge.toLuaValue(server.broadcast(args.tojstring(1), args.tojstring(2)));
                }
                return LuaBridge.toLuaValue(server.broadcastMessage(args.tojstring(1)));
            case 3: // clearRecipes
                server.clearRecipes(); break;
            case 4: // getAllowEnd
                return LuaBridge.toLuaValue(server.getAllowEnd());
            case 5: // getAllowFlight
                return LuaBridge.toLuaValue(server.getAllowFlight());
            case 6: // getAllowNether
                return LuaBridge.toLuaValue(server.getAllowNether());
            case 7: // getAmbientSpawnLimit
                return LuaBridge.toLuaValue(server.getAmbientSpawnLimit());
            case 8: // getAnimalSpawnLimit
                return LuaBridge.toLuaValue(server.getAnimalSpawnLimit());
            case 9: // getBukkitVersion
                return LuaBridge.toLuaValue(server.getBukkitVersion());
            case 10: // getConnectionThrottle
                return LuaBridge.toLuaValue(server.getConnectionThrottle());
            case 11: // getDefaultGameMode
                return LuaBridge.toLuaValue(server.getDefaultGameMode().ordinal());
            case 12: // getGenerateStructures
                return LuaBridge.toLuaValue(server.getGenerateStructures());
            case 13: // getIp
                return LuaBridge.toLuaValue(server.getIp());
            case 14: // getMaxPlayers
                return LuaBridge.toLuaValue(server.getMaxPlayers());
            case 15: // getMonsterSpawnLimit
                return LuaBridge.toLuaValue(server.getMonsterSpawnLimit());
            case 16: // getMotd
                return LuaBridge.toLuaValue(server.getMotd());
            case 17: // getName
                return LuaBridge.toLuaValue(server.getName());
            case 18: // getOnlineMode
                return LuaBridge.toLuaValue(server.getOnlineMode());
            case 19: // getPort
                return LuaBridge.toLuaValue(server.getPort());
            case 20: // getServerId
                return LuaBridge.toLuaValue(server.getServerId());
            case 21: // getServerName
                return LuaBridge.toLuaValue(server.getServerName());
            case 22: // getShutdownMessage
                return LuaBridge.toLuaValue(server.getShutdownMessage());
            case 23: // getSpawnRadius
                return LuaBridge.toLuaValue(server.getSpawnRadius());
            case 24: // getTicksPerAnimalSpawns
                return LuaBridge.toLuaValue(server.getTicksPerAnimalSpawns());
            case 25: // getTicksPerMonsterSpawns
                return LuaBridge.toLuaValue(server.getTicksPerMonsterSpawns());
            case 26: // getUpdateFolder
                return LuaBridge.toLuaValue(server.getUpdateFolder());
            case 27: // getVersion
                return LuaBridge.toLuaValue(server.getVersion());
            case 28: // getViewDistance
                return LuaBridge.toLuaValue(server.getViewDistance());
            case 29: // getWaterAnimalSpawnLimit
                return LuaBridge.toLuaValue(server.getWaterAnimalSpawnLimit());
            case 30: // getWorldType
                return LuaBridge.toLuaValue(server.getWorldType());
            case 31: // hasWhitelist
                return LuaBridge.toLuaValue(server.hasWhitelist());
            case 32: // isHardcore
                return LuaBridge.toLuaValue(server.isHardcore());
            case 33: // isPrimaryThread
                return LuaBridge.toLuaValue(server.isPrimaryThread());
            case 34: // reload
                server.reload(); break;
            case 35: // reloadWhitelist
                server.reloadWhitelist(); break;
            case 36: // resetRecipes
                server.resetRecipes(); break;
            case 37: // savePlayers
                server.savePlayers(); break;
            case 38: // setSpawnRadius
                server.setSpawnRadius(args.toint(1)); break;
            case 39: // setWhitelist
                server.setWhitelist(args.toboolean(1));
                break;
            case 40: // shutdown
                server.shutdown(); break;
            case 41: // unbanIp
                server.unbanIP(args.tojstring(1)); break;
            case 42: // unloadWorld
                return LuaBridge.toLuaValue(server.unloadWorld(args.tojstring(1), args.toboolean(2)));
            case 43: // useExactLoginLocation
                return LuaBridge.toLuaValue(server.useExactLoginLocation());
            case 44: // getPlayer
                return LuaBridge.toLuaValue(server.getPlayer(args.tojstring(1)));
            case 45: // getOnlinePlayers
                return LuaBridge.toLuaTable(server.getOnlinePlayers());
            case 46: // matchPlayer
                return LuaBridge.toLuaTable(server.matchPlayer(args.tojstring(1)));
        }

        return LuaValue.NIL;
    }
}
