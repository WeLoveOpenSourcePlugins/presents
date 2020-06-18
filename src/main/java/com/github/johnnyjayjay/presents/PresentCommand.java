package com.github.johnnyjayjay.presents;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class PresentCommand implements CommandExecutor {

  protected final ConfigurationSection presentConfig;

  protected PresentCommand(ConfigurationSection presentConfig) {
    this.presentConfig = presentConfig;
  }

  @Override
  public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("§cThis command is only executable by players.");
      return true;
    }

    if (args.length != 1) {
      return false;
    }

    Player executor = (Player) sender;
    String name = args[0];
    if (presentConfig.contains(name)) {
      execute(executor, (Present) presentConfig.get(name));
    } else {
      unknownPresent(executor, name);
    }
    return true;
  }

  protected abstract void execute(Player executor, Present present);

  protected abstract void unknownPresent(Player executor, String name);
}
