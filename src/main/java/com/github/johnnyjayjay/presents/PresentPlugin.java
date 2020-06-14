package com.github.johnnyjayjay.presents;

import com.github.johnnyjayjay.presents.conversation.StartPrompt;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PresentPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    ConversationFactory conversationFactory = new ConversationFactory(this)
        .withFirstPrompt(StartPrompt.INSTANCE);
    ConfigurationSerialization.registerClass(Present.class);
    Bukkit.getPluginManager().registerEvents(new PresentListener(getConfig()), this);
    getCommand("present").setExecutor(new PresentGetCommand(conversationFactory, getConfig()));
  }

}
