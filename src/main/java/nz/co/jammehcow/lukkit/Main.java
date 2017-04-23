package nz.co.jammehcow.lukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/**
 * @author jammehcow
 */

public class Main extends JavaPlugin {
    // Config version
    private static final int CFG_VERSION = 3;

    public static Logger logger;


    @Override
    public void onEnable() {
        logger = getLogger();

        this.checkForUpdates();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

    private void checkForUpdates() {
        // Update messages
        if (getConfig().get("update-checker").equals(true)) {
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(
                        "http://www.spigotmc.org/api/general.php").openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.getOutputStream().write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=32599").getBytes("UTF-8"));
                String version = new BufferedReader(new InputStreamReader(
                        con.getInputStream())).readLine();
                if (version.length() <= 7 && !version.equalsIgnoreCase(this.getDescription().getVersion())) {
                    logger.info("A new version of Lukkit has been released: " + version);
                    logger.info("You can download it from https://www.spigotmc.org/resources/lukkit.32599/");
                }
            } catch (Exception ex) {
                logger.warning("Unable to connect to Spigot API for Lukkit update check.");
            }
        }
    }
}
