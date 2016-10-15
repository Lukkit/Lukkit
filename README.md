Lukkit
======

Lukkit allows developers to create Bukkit plugins quickly and easily using the lua scripting language.

See the project on [my site](https://jammehcow.ml/#lukkit).

For support or general disccusion, come join us on the SpigotMC IRC at [#lukkit](http://fry.spi.gt/iris/?nick=&channels=lukkit)

### Version 1.1-alpha1

[Download](https://files.jammehcow.ml/Lukkit/build/1.1-alpha1/Lukkit.jar)

After 2 years of inactivtity, the project was picked back up and updated to Minecraft version 1.10.2.
With this brings support for all of the new events included in the Spigot API and support for Lua 5.2.x (LuaJ 3.0.1 dated 2014).
The plan is to get this plugin a decent Wiki, update the documentation and breathe life back into the ease-or-use language known as Lua.


### What's the point?
 - Lua is a very beginner friendly language
 - It is very quick to make a simple plugin
 - Supports ALL bukkit functions, with more lua-like ways of doing things added frequently
 - Projects added in v1.0-alpha4 and later allow for more advanced plugins to be made within their own folder

### Examples
More examples can be found in the examples folder on [github](https://github.com/jammehcow/Lukkit/tree/master/examples).

Add command /shout to broadcast a message to the server. Bear in mind that at the moment naming a command with a capital letter will stop the command from being deregistered when running /lukkit reload or /lukkit resetenv
```lua
-- Command name, short description, command usage
lukkit.addCommand("shout", "Broadcast a message to the server", "/shout Your message here", function(sender, args)
  -- Collate all arguments into dtring to broadcast globally.
  broadcast(table.concat(args, " "))
end)
```

Only allow ops to break blocks.
```lua
-- Adding an event listener. Ain't it easy!
events.add("blockBreak", function(event)
  local sender = event:getPlayer()
  if not sender:isOp() then
    If the player's not op, display message and stop the block breaking event
    sender:sendRawMessage(stringOf(format.RED) .. "You are not allowed to break blocks")
    event:setCancelled(true)
  end
end)
```

Register a new plugin called HelloPlugin. This should go in `..plugins/Lukkit/PROJECT_NAME/main.lua`.
```lua
-- Register the plugin with a name, version and code. 
-- lukkit.addPlugin(pluginName, pluginVersion, pluginContent)
local helloPlugin = lukkit.addPlugin("HelloPlugin", "1.0", function(plugin)
  -- When the plugin is enabled (startup)
  plugin.onEnable(function()
    plugin.print("HelloPlugin v" .. plugin.version .. " enabled")
  end)

  -- When the plugin is disabled (shutdown)
  plugin.onDisable(function()
    plugin.warn("HelloPlugin v" .. plugin.version .. " disabled")
  end)
  
  -- Add command
  plugin.addCommand("hello", "Send the sender the message 'Hello, world!'", "/hello", function(sender, args)
    sender:sendMessage("Hello, world!")
  end)
end)
```
