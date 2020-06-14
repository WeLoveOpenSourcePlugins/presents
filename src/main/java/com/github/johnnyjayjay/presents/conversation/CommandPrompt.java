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
    return "Enter a command to add or \"finish\" to finish";
  }

  @Override
  public Prompt acceptInput(ConversationContext context, String input) {
    Present present = (Present) context.getSessionData("present");
    if (input.equalsIgnoreCase("finish")) {
      return TexturePrompt.INSTANCE;
    } else {
      present.addCommand(input);
      return this;
    }
  }
}
