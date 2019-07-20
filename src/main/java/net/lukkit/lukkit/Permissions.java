package net.lukkit.lukkit;

@SuppressWarnings("WeakerAccess")
public class Permissions {
    // /lukkit repo *
    public static final String REPO_BASE = "lukkit.repo";

    public static final String REPO_GET         = REPO_BASE + ".get";
    public static final String REPO_GET_ALL     = REPO_GET + ".all";
    public static final String REPO_GET_FORCE   = REPO_GET + ".force";
    public static final String REPO_GET_VERSION = REPO_GET + ".version";

    public static final String REPO_UPGRADE = REPO_BASE + ".upgrade";
    public static final String REPO_UPGRADE_ALL     = REPO_UPGRADE + ".all";
    public static final String REPO_UPGRADE_VERSION = REPO_UPGRADE + ".version";
    public static final String REPO_UPGRADE_FORCE   = REPO_UPGRADE + ".force";

    public static final String REPO_DOWNGRADE         = REPO_BASE + ".downgrade";
    public static final String REPO_DOWNGRADE_ALL     = REPO_DOWNGRADE  + ".all";
    public static final String REPO_DOWNGRADE_FORCE   = REPO_DOWNGRADE  + ".force";

    public static final String REPO_HELP         = REPO_BASE + ".help";
    public static final String REPO_INFO         = REPO_BASE + ".info";
    public static final String REPO_SEARCH       = REPO_BASE + ".search";
    public static final String REPO_LISTUPGRADES = REPO_BASE + ".listupgrades";
    public static final String REPO_VERSIONS     = REPO_BASE + ".versions";
}
