package dev.artex.lukkit;

import java.util.Properties;

public class Constants {
    private static final Properties PROPS = new Properties();
    private static final String REPLACE_TOKEN = "@@";

    // URLs
    public static final String PLUGIN_REPO_URL = formatTokens("@@lukkitPluginRepoURL@@");

    // onABC method messages
    static final String MAIN_ENABLE = formatTokens("Lukkit @@version@@ enabled!");
    static final String MAIN_DISABLE = formatTokens("Lukkit @@version@@ disabled!");

    // Command messages
    static final String CMD_INFO = formatTokens("Lukkit @@version@@\n" +
            "Target Minecraft version @@mcVersion@@\n" +
            "GitHub repo @@fullRepoURL@@");

    /**
     * Replaces tokens in the provided string
     * If the provided token key isn't found the token will not be replaced
     *
     * @param str the string containing replaceable tokens
     * @return string with inserted tokens
     */
    public static String formatTokens(String str) {
        for (Object name : PROPS.keySet()) {
            String prop = (String)name;
            String propVal = PROPS.getProperty(prop);

            if (propVal != null) {
                str = str.replace(REPLACE_TOKEN + prop + REPLACE_TOKEN, propVal);
            }
        }

        return str;
    }
}
