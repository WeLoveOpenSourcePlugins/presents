package com.github.johnnyjayjay.presents.conversation;

import com.github.johnnyjayjay.presents.Present;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

import static com.github.johnnyjayjay.presents.conversation.Util.getPresent;

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
  public String getPromptText(ConversationContext context) {
    return "§aYou're done configuring present §6"
        + getPresent(context).getName() + "§a!";
  }
}
