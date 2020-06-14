package com.github.johnnyjayjay.presents;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO: Present delete, update command
public class PresentGetCommand implements CommandExecutor {

  private final ConversationFactory conversationFactory;
  private final ConfigurationSection presents;

  public PresentGetCommand(ConversationFactory conversationFactory, ConfigurationSection presents) {
    this.conversationFactory = conversationFactory;
    this.presents = presents;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      if (args.length != 1) {
        return false;
      }
      String name = args[0];
      if (presents.contains(name)) {
        giveItem(player, (Present) presents.get(name));
      } else {
        Conversation conversation = conversationFactory.buildConversation(player);
        conversation.getContext().setSessionData("present", new Present(name));
        conversation.begin();
        conversation.addConversationAbandonedListener((event) -> {
          if (event.gracefulExit()) {
            giveItem(player, (Present) event.getContext().getSessionData("present"));
          }
        });
      }
    } else {
      sender.sendMessage("Only players");
    }
    return true;
  }

  private void giveItem(Player player, Present present) {
    player.getInventory().addItem(present.createItemStack());
    player.updateInventory();
  }
}
