package com.github.johnnyjayjay.presents.conversation;

import org.bukkit.Sound;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

import static com.github.johnnyjayjay.presents.conversation.Util.findMatch;
import static com.github.johnnyjayjay.presents.conversation.Util.getPresent;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class SoundPrompt extends ValidatingPrompt {

  public static final SoundPrompt INSTANCE = new SoundPrompt();

  private SoundPrompt() {}

  @Override
  protected boolean isInputValid(ConversationContext context, String input) {
    return "skip".equalsIgnoreCase(input) || findMatch(Sound.class, input) != null;
  }

  @Override
  protected Prompt acceptValidatedInput(ConversationContext context, String input) {
    if (!"skip".equalsIgnoreCase(input)) {
      getPresent(context).setSound(findMatch(Sound.class, input));
    }
    return ParticlePrompt.INSTANCE;
  }

  @Override
  public String getPromptText(ConversationContext conversationContext) {
    return "§aPlease enter the sound the present should produce in the world. Type 'skip' if there should be no sound";
  }

  @Override
  protected String getFailedValidationText(ConversationContext context, String invalidInput) {
    return "§6" + invalidInput + "§c is not a valid sound.";
  }
}
