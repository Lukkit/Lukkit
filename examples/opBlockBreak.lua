events.add("blockBreak", function(event)
  if not event:getPlayer():isOp() then
    broadcast(stringOf(format.RED) .. "You are not allowed to break blocks")
    event:setCancelled(true)
  end
end)