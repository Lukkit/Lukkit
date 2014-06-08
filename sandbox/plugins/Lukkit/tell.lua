lukkit.addCommand("tell", "Shortcut to broadcast a message", "/tell Your message here", function(sender, args)
  if (#args > 0) then
    broadcast(table.concat(args, " "))
  else
    sender:sendMessage("/tell Your message here")
  end
end)