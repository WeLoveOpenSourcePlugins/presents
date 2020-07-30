package com.github.johnnyjayjay.presents.command;

import com.github.johnnyjayjay.presents.Present;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PresentConfigCommand extends PresentCommand {

  private final ConversationFactory conversationFactory;

  public PresentConfigCommand(ConfigurationSection presentConfig, ConversationFactory conversationFactory) {
    super(presentConfig);
    this.conversationFactory = conversationFactory;
  }

  @Override
  protected void execute(CommandSender sender, Present present) {
    runConfiguration(sender, present, (r) ->
        sender.sendMessage("§aPresent §6" + r.getName() + "§a was modified successfully."));
  }

  @Override
  protected void unknownPresent(CommandSender sender, String name) {
    runConfiguration(sender, new Present(name), (result) -> {
      presentConfig.set(name, result);
      sender.sendMessage("§aPresent §6" + name + "§a was created successfully.");
    });
  }

  private void runConfiguration(CommandSender sender, Present present, Consumer<Present> callback) {
    if (!(sender instanceof Conversable)) {
      return;
    }
    Conversation conversation = conversationFactory.buildConversation((Conversable) sender);
    conversation.getContext().setSessionData(Present.class, present);
    conversation.begin();
    conversation.addConversationAbandonedListener((event) -> {
      if (event.gracefulExit()) {
        Present result = (Present) event.getContext().getSessionData(Present.class);
        callback.accept(result);
      }
    });
  }
}
