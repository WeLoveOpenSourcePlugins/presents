package com.github.johnnyjayjay.presents.command;

import com.github.johnnyjayjay.presents.Present;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PresentConfigureCommand extends PresentCommand {

  private final ConversationFactory conversationFactory;

  public PresentConfigureCommand(ConfigurationSection presentConfig, ConversationFactory conversationFactory) {
    super(presentConfig);
    this.conversationFactory = conversationFactory;
  }

  @Override
  protected void execute(Player executor, Present present) {
    runConfiguration(executor, present, (r) ->
        executor.sendMessage("§aPresent §6" + r.getName() + "§a was modified successfully."));
  }

  @Override
  protected void unknownPresent(Player executor, String name) {
    runConfiguration(executor, new Present(name), (result) -> {
      presentConfig.set(name, result);
      executor.sendMessage("§aPresent §6" + name + "§a was created successfully.");
    });
  }

  private void runConfiguration(Player player, Present present, Consumer<Present> callback) {
    Conversation conversation = conversationFactory.buildConversation(player);
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
