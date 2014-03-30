function tableFromList(list)
	local table = {}
	for i = 0, list:size() - 1 do
		table[i] = list:get(i)
	end
	return table
end

function tableFromArray(array)
    local table = {}
    for i = 0, array.length, 1 do
        table[i] = array[i]
    end
    return table
end

function stringOf(object)
    return object:toString()
end

function broadcast(message)
    server:broadcastMessage(message)
end