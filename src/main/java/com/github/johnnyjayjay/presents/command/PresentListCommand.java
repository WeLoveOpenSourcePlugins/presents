package com.github.johnnyjayjay.presents.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PresentListCommand implements CommandExecutor {

  private final ConfigurationSection presentConfig;

  public PresentListCommand(ConfigurationSection presentConfig) {
    this.presentConfig = presentConfig;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    sender.sendMessage("§aHere is a list of all available present types:");
    sender.sendMessage(String.join("\n§a- §6", presentConfig.getKeys(false)));
    return true;
  }
}
