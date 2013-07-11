import sys, os, zipfile, yaml

def main(args):
	pluginFile = open("src/plugin.yml", "r")
	plugin = yaml.load(pluginFile)
	name = plugin["name"]
	version = plugin["version"]
	
	zip = zipfile.ZipFile("pkg/" + name + ".jar", "w")
	for root, dirs, files in os.walk("bin/"):
		for file in files:
			zip.write(os.path.join(root, file), os.path.join(root[3:], file))
	for root, dirs, files in os.walk("libs/luj-jse/"):
		for file in files:
			zip.write(os.path.join(root, file), os.path.join(root[12:], file))
	zip.close()
	
	zip = zipfile.ZipFile("pkg/" + name + "-" + version + ".zip", "w")
	zip.write("LICENSE")
	zip.write("pkg/" + name + ".jar", name + ".jar")
	zip.close()
	
	os.remove("pkg/" + name + ".jar")

if __name__ == "__main__":
	main(sys.argv)