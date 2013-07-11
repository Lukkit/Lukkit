import sys, os, urllib2, re

def main(args):
	code = """package unwrittenfun.bukkit.lukkit.^;

import #;

import unwrittenfun.bukkit.lukkit.LukkitObject;

public class $Object extends LukkitObject {
	public $ ^;

	public $Object($ %) {
		^ = %;

"""
	endCode = """
	}
	
	@Override
	public Object getObject() {
		return ^;
	}
}
"""
	methodCode = """package unwrittenfun.bukkit.lukkit.^;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;


public class $Function extends VarArgFunction {

	@Override
	public Varargs invoke(Varargs args) {
		return LuaValue.NIL;
	}
	
}
"""
	package = re.search(r'http://jd.bukkit.org/dev/apidocs/([/\w]+).html', args[1]).group(1).replace("/", ".")
	upperName = re.search(r'(\w+).html', args[1]).group(1)
	name = upperName.lower()
	code = code.replace("$", upperName).replace("^", name).replace("#", package).replace("%", name[0])
	endCode = endCode.replace("^", name)
	
	doc = urllib2.urlopen(args[1])
	html = doc.read()
	
	if not os.path.exists("src/unwrittenfun/bukkit/lukkit/" + name):
		os.mkdir("src/unwrittenfun/bukkit/lukkit/" + name)
	
	m = re.findall(r'<B><A HREF="[^"]*">(\w+)</A></B>', html)
	done = {}
	for method in m:
		if not method in done:
			done[method] = True
			code += '//		set("' + method + '", new ' + method[0].upper() + method [1:] + 'Function());\n'
			mf = open("src/unwrittenfun/bukkit/lukkit/" + name + "/" + method[0].upper() + method [1:] + "Function.java", "w")
			mf.write(methodCode.replace("$", method[0].upper() + method [1:]).replace("^", name))
			mf = None
	
	code += endCode
	f = open("src/unwrittenfun/bukkit/lukkit/" + name + "/" + upperName + "Object.java", "w")
	f.write(code)
	

if __name__ == "__main__":
	main(sys.argv)