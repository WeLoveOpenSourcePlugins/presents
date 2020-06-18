package com.github.johnnyjayjay.presents.conversation;

import com.github.johnnyjayjay.presents.Present;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.RegexPrompt;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class TexturePrompt extends RegexPrompt {

  public static final TexturePrompt INSTANCE = new TexturePrompt();

  private TexturePrompt() {
    super("[\\w-]+=");
  }

  @Override
  protected Prompt acceptValidatedInput(ConversationContext context, String input) {
    Present present = (Present) context.getSessionData(Present.class);
    present.setTexture(input);
    return EndPrompt.INSTANCE;
  }

  @Override
  public String getPromptText(ConversationContext conversationContext) {
    return "§aEnter the Base64-encoded texture value to use for the present. You can find this on head websites.";
  }
}
