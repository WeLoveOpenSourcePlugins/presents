package com.github.johnnyjayjay.presents.conversation;

import com.github.johnnyjayjay.presents.Present;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class StartPrompt extends MessagePrompt {

  public static final StartPrompt INSTANCE = new StartPrompt();

  private StartPrompt() {

  }

  @Override
  protected Prompt getNextPrompt(ConversationContext conversationContext) {
    return CommandPrompt.INSTANCE;
  }

  @Override
  public String getPromptText(ConversationContext context) {
    return "§aWelcome to the configuration of present §6"
        + ((Present) context.getSessionData(Present.class)).getName() + "§a.";
  }
}
