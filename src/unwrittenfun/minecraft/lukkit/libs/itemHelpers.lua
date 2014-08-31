itemHelpers = {}

function itemHelpers.itemStack(mat, stackSize)
  return itemHelpers.itemStackWithDamage(mat, stackSize, 0)
end

function itemHelpers.itemStackWithDamage(mat, stackSize, damage)
  return luajava.newInstance("org.bukkit.inventory.ItemStack", mat, stackSize, damage)
end

function itemHelpers.giveItem(player, mat, stackSize)
  local stack = itemHelpers.itemStack(mat, stackSize)
  player:getInventory():addItem({stack})
end

function itemHelpers.giveItemWithDamage(player, mat, stackSize, damage)
  local stack = itemHelpers.itemStackWithDamage(mat, stackSize, damage)
  player:getInventory():addItem({stack})
end