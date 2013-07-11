package unwrittenfun.bukkit.lukkit.conversable;

import org.bukkit.conversations.Conversable;

import unwrittenfun.bukkit.lukkit.LukkitObject;

public class ConversableObject extends LukkitObject {
	public Conversable conversable;

	public ConversableObject(Conversable c) {
		conversable = c;

//		set("abandonConversation", new AbandonConversationFunction());
		set("acceptConversationInput", new AcceptConversationInputFunction());
//		set("beginConversation", new BeginConversationFunction());
		set("isConversing", new IsConversingFunction());
		set("sendRawMessage", new SendRawMessageFunction());
	}
	
	@Override
	public Object getObject() {
		return conversable;
	}
}
