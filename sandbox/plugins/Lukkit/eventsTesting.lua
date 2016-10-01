-- Block events

events.add("blockExplode", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("cauldronLevelChange", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

-- Entity events

events.add("areaEffectCloudApply", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("enderDragonChangePhase", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("entityAirChange", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("entityBreed", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("entityToggleGlide", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("fireworkExplode", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("blockExplode", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("itemMerge", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("lingeringPotionSplash", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("villagerAcquireTrade", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("villagerReplenishTrade", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

-- Inventory events

events.add("prepareAnvil", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

-- Player events

events.add("playerArmorStandManipulate", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("playerChangedMainHand", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("playerInteractAtEntity", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("playerItemDamage", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("playerPickupArrow", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("playerStatisticIncrement", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

events.add("playerSwapHandItems", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

-- Server events

events.add("tabComplete", function(event)
    broadcast("Event called : " .. event:getEventName())
end)

-- Vehicle events

events.add("vehicleCollision", function(event)
    broadcast("Event called : " .. event:getEventName())
end)
