package com.github.johnnyjayjay.presents;

import com.github.johnnyjayjay.presents.conversation.StartPrompt;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO 18.06.2020: consider using compatre for version compatibility
public class PresentPlugin extends JavaPlugin {

  private PresentLocations presentLocations;

  @Override
  public void onEnable() {
    getLogger().info("Registering present serialisation...");
    ConfigurationSerialization.registerClass(Present.class);
    ConfigurationSerialization.registerClass(PresentLocations.class);
    getLogger().info("Loading config...");
    ConfigurationSection presentConfig = getConfig().getConfigurationSection("presents");
    presentLocations = (PresentLocations) getConfig().get("locations");
    getLogger().info("Config loaded successfully.");
    ConversationFactory conversationFactory = new ConversationFactory(this)
        .withFirstPrompt(StartPrompt.INSTANCE)
        .withEscapeSequence("abort");
    Bukkit.getPluginManager().registerEvents(new PresentListener(presentLocations, presentConfig), this);
    getCommand("present")
        .setExecutor(new DelegatingCommand(ImmutableMap.of(
            "get", new PresentGetCommand(presentConfig),
            "delete", new PresentDeleteCommand(presentConfig, presentLocations),
            "configure", new PresentConfigureCommand(presentConfig, conversationFactory)
        )));
  }

  @Override
  public void onDisable() {
    getLogger().info("Serialising present locations to config...");
    getConfig().set("locations", presentLocations);
    getLogger().info("Saving config...");
    saveConfig();
    getLogger().info("Config saved.");
  }
}
