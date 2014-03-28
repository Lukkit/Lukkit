lukkit.addCommand("tell", "Shortcut to broadcast a message", "/tell Your message here", function(args, event)
  broadcast(table.concat(args, " "))
end)