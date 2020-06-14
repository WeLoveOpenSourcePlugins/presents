package com.github.johnnyjayjay.presents.conversation;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class EndPrompt extends MessagePrompt {

  public static final EndPrompt INSTANCE = new EndPrompt();

  private EndPrompt() {}

  @Override
  protected Prompt getNextPrompt(ConversationContext context) {
    return null;
  }

  @Override
  public String getPromptText(ConversationContext conversationContext) {
    return "All set";
  }
}
