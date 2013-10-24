require "rake"

BUKKIT_PATH = "/Users/unwrittenfun/Documents/Testing/Lukkit" # CHANGE TO MATCH YOUR ENVIRONMENT
JAR_NAME = "Lukkit.jar"

desc "Package compiled code into jar"
task :jar do
	rm "pkg/#{JAR_NAME}" if File.exists? "pkg/#{JAR_NAME}"
	sh "python package.py dev"
end

desc "Package compiled code into jar then zip to be released"
task :zip do
	rm "pkg/#{JAR_NAME}" if File.exists? "pkg/#{JAR_NAME}"
	sh "python package.py"
end

desc "Package compiled code into jar and run instance of bukkit"
task run: [:jar] do
	rm "#{BUKKIT_PATH}/plugins/#{JAR_NAME}" if File.exists? "#{BUKKIT_PATH}/plugins/#{JAR_NAME}"
	cp "pkg/#{JAR_NAME}", "#{BUKKIT_PATH}/plugins"
	cd BUKKIT_PATH
	sh "java -jar craftbukkit.jar"
end