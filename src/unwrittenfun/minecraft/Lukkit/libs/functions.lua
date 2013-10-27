function tableFromList(list)
	table = {}
	for i = 0, list:size() - 1 do
		table[i] = list:get(i)
	end
	return table
end