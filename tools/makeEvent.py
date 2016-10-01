import sys, urllib2, re

URL_BASE = "http://jd.bukkit.org/rb/apidocs/org/bukkit/event/"
URL_END  = "/package-summary.html"
PKG_BASE = "org.bukkit.event"
SAVE_DIR = "src/unwrittenfun/minecraft/lukkit/environment/events"

def main(args):
    event = args[1]
    pkg = PKG_BASE + "." + event
    pkg_url = URL_BASE + event + URL_END
    ptn_event = "<TD WIDTH=\"15%\"><B><A HREF=\"../../../../org/bukkit/event/" + event + "/.*.html\" title=\"class in org.bukkit.event." + event + "\">(.*)</A></B></TD>"
    java = """
package unwrittenfun.minecraft.lukkit.environment.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.""" + event + """.*;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.ArrayList;

/**
 * Bukkit Plugin: Lukkit
 * Original Author: UnwrittenFun
 */
public class Lukkit""" + event.capitalize() + """Events implements Listener {
    public Lukkit""" + event.capitalize() + """Events() {
"""

    print("Making event " + pkg)
    print("Downloading info from " + pkg_url)

    page = urllib2.urlopen(pkg_url)
    page_content = page.read()
    events = re.findall(ptn_event, page_content)
    for (event_name) in events:
        java += "        LukkitEvents.eventMap.put(\"" + event_name[0].lower() + event_name[1:-5] + "\", new ArrayList<LuaFunction>());\n"
    java += "    }\n"

    for (event_name) in events:
        java += """
    @EventHandler
    public void """ + event_name[0].lower() + event_name[1:-5] + """(""" + event_name + """ event) {
        ArrayList<LuaFunction> callbacks = LukkitEvents.eventMap.get(\"""" + event_name[0].lower() + event_name[1:-5] + """\");
        for (LuaFunction cb : callbacks) {
            cb.call(CoerceJavaToLua.coerce(event));
            if (event.isCancelled()) return;
        }
    }
"""
    java += "}"
    
    save_file = SAVE_DIR + "/Lukkit" + event.capitalize() + "Events.java"
    print("Saving to " + save_file)
    f = open(save_file, "w")
    f.write(java)
    f.close()
    print("Done!")

if __name__ == "__main__":
    main(sys.argv)