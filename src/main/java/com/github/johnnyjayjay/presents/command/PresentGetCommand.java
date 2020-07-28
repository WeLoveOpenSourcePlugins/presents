package com.github.johnnyjayjay.presents.command;

import com.github.johnnyjayjay.presents.Present;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
  protected void execute(CommandSender sender, Present present) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("§cThis command is only executable by players.");
      return;
    }
    Player player = (Player) sender;
    player.getInventory().addItem(present.createItemStack());
    player.updateInventory();
    player.sendMessage("§aA §6" + present.getName() + "§a present item was added to your inventory.");
  }

  @Override
  protected void unknownPresent(CommandSender sender, String name) {
    sender.sendMessage(
        "§cUnknown present. You can create this present using §6/present configure §a"
            + name + "§c."
    );
  }
}
