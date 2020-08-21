package com.github.johnnyjayjay.presents.command;

import com.github.johnnyjayjay.presents.Present;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PresentGetCommand extends PresentCommand {

  public PresentGetCommand(ConfigurationSection presentConfig) {
    super(presentConfig);
  }

  @Override
  protected void execute(CommandSender sender, Present present, String[] args) {
    if (args.length > 0) {
      List<Player> targets = Arrays.stream(args)
          .map(Bukkit::getPlayer)
          .filter(Objects::nonNull)
          .collect(Collectors.toList());
      targets.forEach((target) -> addPresent(target, present));
      sender.sendMessage("§aA §6" + present.getName() + "§a present item was given to"
          + targets.stream().map(Player::getName).collect(Collectors.joining("§a,§6 ", "§6", "§a.")));
    } else if (sender instanceof Player) {
      addPresent((Player) sender, present);
    } else {
      sender.sendMessage("§cYou can only give yourself present items when you are a player.");
    }
  }

  private void addPresent(Player player, Present present) {
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
