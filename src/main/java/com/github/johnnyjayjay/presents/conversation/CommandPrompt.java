package com.github.johnnyjayjay.presents.conversation;

import com.github.johnnyjayjay.presents.Present;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CommandPrompt extends StringPrompt {

  public static final CommandPrompt INSTANCE = new CommandPrompt();

  private CommandPrompt() {

  }

  @Override
  public String getPromptText(ConversationContext context) {
    return "§aEnter a command to add or §6finish§a to finish.";
  }

  @Override
  public Prompt acceptInput(ConversationContext context, String input) {
    Present present = (Present) context.getSessionData(Present.class);
    if ("finish".equalsIgnoreCase(input)) {
      return TexturePrompt.INSTANCE;
    } else {
      present.addCommand(input);
      return this;
    }
  }
}
