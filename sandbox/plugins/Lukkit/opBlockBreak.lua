events.add("blockBreak", function(event)
  local sender = event:getPlayer()
  if not sender:isOp() then
    sender:sendRawMessage(stringOf(format.RED) .. "You are not allowed to break blocks")
    event:setCancelled(true)
  end
end)