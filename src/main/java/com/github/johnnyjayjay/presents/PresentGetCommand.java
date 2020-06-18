package com.github.johnnyjayjay.presents;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PresentGetCommand extends PresentCommand {

  public PresentGetCommand(ConfigurationSection presentConfig) {
    super(presentConfig);
  }

  @Override
  protected void execute(Player executor, Present present) {
    executor.getInventory().addItem(present.createItemStack());
    executor.updateInventory();
    executor.sendMessage("§aA §6" + present.getName() + "§a present item was added to your inventory.");
  }

  @Override
  protected void unknownPresent(Player executor, String name) {
    executor.sendMessage(
        "§cUnknown present. You can create this present using §6/present configure §a"
            + name + "§c."
    );
  }
}
