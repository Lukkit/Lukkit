import sys, os, zipfile, yaml

def main(args):
	pluginFile = open("src/plugin.yml", "r")
	plugin = yaml.load(pluginFile)
	name = plugin["name"]
	if len(args) < 2:
		version = plugin["version"]
	else:
		version = args[1]
	
	zip = zipfile.ZipFile("pkg/" + name + ".jar", "w")
	for root, dirs, files in os.walk("out/production/Lukkit"):
		for file in files:
			zip.write(os.path.join(root, file), os.path.join(root[21:], file))
	for root, dirs, files in os.walk("libs/luaj-jse/"):
		for file in files:
			zip.write(os.path.join(root, file), os.path.join(root[13:], file))
	zip.write("src/plugin.yml", "plugin.yml")
	zip.close()
	
	if not version == "dev":
		zip = zipfile.ZipFile("pkg/" + name + "-" + version + ".zip", "w")
		zip.write("pkg/" + name + ".jar", name + ".jar")
		zip.close()
		
		os.remove("pkg/" + name + ".jar")

if __name__ == "__main__":
	main(sys.argv)