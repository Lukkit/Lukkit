Lukkit
======

Lukkit is currently being rewritten on the 2.0-dev branch.

Lukkit allows developers to create Bukkit plugins quickly and easily using the lua scripting language.

Since it was first release by UnwrittenFun, Lukkit has moved from developer to developer. Although updates have been rolling out from time to time, no one person has been able to continue maintaining Lukkit. However, we are trying to change that. 

UnwrittenFun and jammehcow have made the greatest contributions to Lukkit and we would like to thank them for their work.

The Discord guild has been moved to https://dev.theartex.net/discord/.


### What's the point?
 - Lua is a very friendly language for beginners
 - It is very easy to make a simple plugin
 - Lua supports all of the Spigot API
 - Projects from v1.0-alpha4 allow for more advanced plugins

### Examples
More examples can be found [here](https://github.com/artex-development/Lukkit/tree/master/examples).

Add command /shout to broadcast a message to the server. Bear in mind that, at the moment, naming a command with a capital letter will stop the command from being deregistered when running /lukkit reload or /lukkit resetenv.
```lua
-- Command name, short description, command usage
lukkit.addCommand("shout", "Broadcast a message to the server", "/shout Your message here", function(sender, args)
  -- Collate all arguments into dtring to broadcast globally.
  broadcast(table.concat(args, " "))
end)
```

Only allow operators to break blocks.
```lua
-- Adding an event listener. Ain't it easy!
events.add("blockBreak", function(event)
  local sender = event:getPlayer()
  if not sender:isOp() then
    -- If the player is not op, display message and stop the block breaking event
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
