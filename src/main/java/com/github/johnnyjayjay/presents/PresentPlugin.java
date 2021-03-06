package com.github.johnnyjayjay.presents;

import com.github.johnnyjayjay.compatre.NmsClassLoader;
import com.github.johnnyjayjay.presents.command.*;
import com.github.johnnyjayjay.presents.conversation.StartPrompt;
import com.google.common.collect.ImmutableMap;
import me.bristermitten.pdm.PDMBuilder;
import me.bristermitten.pdm.PluginDependencyManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PresentPlugin extends JavaPlugin {

  private PresentLocations presentLocations;

  @Override
  public void onLoad() {
    PluginDependencyManager dependencyManager = new PDMBuilder(this).build();
    dependencyManager.loadAllDependencies().join();
    NmsClassLoader.loadNmsDependents(PresentPlugin.class);
  }

  @Override
  public void onEnable() {
    System.out.println(getClass().getClassLoader());
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
    Bukkit.getPluginManager().registerEvents(new PresentListener(this, presentLocations, presentConfig), this);
    getCommand("present")
        .setExecutor(new DelegatingCommand(new PresentHelpCommand(), ImmutableMap.of(
            "get", new PresentGetCommand(presentConfig),
            "delete", new PresentDeleteCommand(presentConfig, presentLocations),
            "config", new PresentConfigCommand(presentConfig, conversationFactory),
            "list", new PresentListCommand(presentConfig)
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
