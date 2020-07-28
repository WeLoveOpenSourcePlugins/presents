package com.github.johnnyjayjay.presents.command;

import com.github.johnnyjayjay.presents.Present;
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
    if (args.length != 1) {
      return false;
    }

    String name = args[0];
    if (presentConfig.contains(name)) {
      execute(sender, (Present) presentConfig.get(name));
    } else {
      unknownPresent(sender, name);
    }
    return true;
  }

  protected abstract void execute(CommandSender sender, Present present);

  protected abstract void unknownPresent(CommandSender sender, String name);
}
