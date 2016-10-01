-- Example project

lukkit.addPlugin("ExamplePlugin", "1.0", function(plugin)
  plugin.onEnable(function() 
    plugin.print("Example Plugin v" .. plugin.version .. " enabled")
  end)

  plugin.onDisable(function() 
    plugin.print("Example Plugin v" .. plugin.version .. " disabled")
  end)

  plugin.addCommand("giveme", "Give the player an item", "/giveme [ITEMNAME] [amount]", function(sender, args)
    print(itemHelpers)
    if material:getMaterial(string.upper(args[1])) ~= nil then
      itemHelpers.giveItem(sender, material:valueOf(string.upper(args[1])), args[2])
    else
      sender:sendMessage("Invalid item " .. args[1])
    end
  end)
end)
