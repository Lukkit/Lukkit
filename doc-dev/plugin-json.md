# plugin.json

The chosen descriptor format for Lukkit v3 plugins

## Required keys

```json
{
    "_cfg_version": "1",
    "name": "MyCoolPlugin",
    "version": "1.0.0",
    "author": "jammehcow",
    "entry": "myplugin.lua",
    "lukkit_version": 3
}
```

**_cfg_version:** The version of the config file (pertaining to Lukkit's cfg version enums). Increments when changes are made to internal config parsing etc.
**name:** Alphanumeric, may not start or end with a hyphen
**version:** Must conform to semver guidelines (MAJ.MIN.PATCH-suffix)
**author:** Author name, should be the author's IGN or dev handle (LukkitRepo/GitHub etc)
**entry:** The file in which the plugin is loaded. Used to start the plugin
**lukkit_version:** The major version of Lukkit this plugin was written for. Ideally will display a warning of possible incompatibility if the version differs
