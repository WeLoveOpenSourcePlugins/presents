package com.github.johnnyjayjay.presents.command;

import com.github.johnnyjayjay.presents.Present;
import com.github.johnnyjayjay.presents.PresentLocations;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PresentDeleteCommand extends PresentCommand {

  private final PresentLocations presentLocations;

  public PresentDeleteCommand(ConfigurationSection presentConfig, PresentLocations presentLocations) {
    super(presentConfig);
    this.presentLocations = presentLocations;
  }

  @Override
  protected void execute(Player executor, Present present) {
    String name = present.getName();
    presentLocations.removePresentsById(name);
    presentConfig.set(name, null);
    executor.sendMessage("§aThe present §6" + name + "§a was deleted successfully.");
  }

  @Override
  protected void unknownPresent(Player executor, String name) {
    executor.sendMessage("§cThis present does not exist.");
  }
}
