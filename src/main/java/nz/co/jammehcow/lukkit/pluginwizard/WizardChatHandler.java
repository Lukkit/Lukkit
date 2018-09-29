package nz.co.jammehcow.lukkit.pluginwizard;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

class WizardChatHandler {
    private final PluginWizard wizard;
    private final Listener listener;
    private String chatContent = "";

    WizardChatHandler(PluginWizard wizard, CommandSender sender) {
        this.wizard = wizard;

        //noinspection unused
        this.listener = new Listener() {
            @EventHandler
            public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
                if (event.getPlayer() == sender) {
                    event.setCancelled(true);
                    String message = event.getMessage();

                    if (message.equalsIgnoreCase("quit")) {
                        // Unregister this listener
                        wizard.cleanup();
                        //noinspection UnnecessaryReturnStatement
                        return;
                    }

                    chatContent = message;
                } else {
                    event.setCancelled(false);
                }
            }

            @EventHandler
            public void onPlayerQuitEvent(PlayerQuitEvent event) {
                if (event.getPlayer() == sender) {
                    wizard.cleanup();
                }
            }

            @EventHandler
            public void onPlayerKickEvent(PlayerKickEvent event) {
                if (event.getPlayer() == sender) {
                    wizard.cleanup();
                }
            }
        };
    }

    synchronized String getInput() {
        while (!chatContent.isEmpty()) {
            try {
                wait(1);
            } catch (InterruptedException ignored) {}
        }

        String msg = this.chatContent;
        // Set to empty string
        this.chatContent = "";
        return msg;
    }

    void setup() {
        Bukkit.getServer().getPluginManager().registerEvents(this.listener, this.wizard.plugin);
    }

    void cleanup() {
        AsyncPlayerChatEvent.getHandlerList().unregister(this.listener);
    }
}
