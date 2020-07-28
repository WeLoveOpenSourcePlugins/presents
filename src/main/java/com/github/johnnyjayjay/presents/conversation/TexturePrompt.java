package com.github.johnnyjayjay.presents.conversation;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.RegexPrompt;

import static com.github.johnnyjayjay.presents.conversation.Util.getPresent;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class TexturePrompt extends RegexPrompt {

  public static final TexturePrompt INSTANCE = new TexturePrompt();

  private TexturePrompt() {
    super("[\\w-]+=+");
  }

  @Override
  protected Prompt acceptValidatedInput(ConversationContext context, String input) {
    getPresent(context).setTexture(input);
    return SoundPrompt.INSTANCE;
  }

  @Override
  public String getPromptText(ConversationContext conversationContext) {
    return "§aEnter the Base64-encoded texture value to use for the present. You can find this on head websites.";
  }

  @Override
  protected String getFailedValidationText(ConversationContext context, String invalidInput) {
    return "§6" + invalidInput + "§c is not a valid texture.";
  }
}
