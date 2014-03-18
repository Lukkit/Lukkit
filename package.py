import sys, os, zipfile, yaml, shutil, subprocess

COMPILE_DIR = "out/production/Lukkit/"
LUAJ_SRC_DIR = "../APIS/luaj-jse/"
SERVER_DIR = "../TestServer/"

def main(args):
	pluginFile = open("src/plugin.yml", "r")
	plugin = yaml.load(pluginFile)
	name = plugin["name"]
	if len(args) < 2:
		version = plugin["version"]
	else:
		version = args[1]
	
	zip = zipfile.ZipFile("pkg/" + name + ".jar", "w")
	for root, dirs, files in os.walk(COMPILE_DIR):
		for file in files:
			zip.write(os.path.join(root, file), os.path.join(root[len(COMPILE_DIR):], file))
	for root, dirs, files in os.walk(LUAJ_SRC_DIR):
		for file in files:
			zip.write(os.path.join(root, file), os.path.join(root[len(LUAJ_SRC_DIR):], file))
	zip.close()
	
	if version == "test":
		shutil.copy("pkg/" + name + ".jar", SERVER_DIR + "plugins/")
		os.remove("pkg/" + name + ".jar")
		os.chdir(SERVER_DIR)
		subprocess.call("java -jar craftbukkit.jar", shell=True, stdin=sys.stdin, stdout=sys.stdout)
	else:
		zip = zipfile.ZipFile("pkg/" + name + "-" + version + ".zip", "w")
		zip.write("pkg/" + name + ".jar", name + ".jar")
		zip.close()
		os.remove("pkg/" + name + ".jar")

if __name__ == "__main__":
	main(sys.argv)