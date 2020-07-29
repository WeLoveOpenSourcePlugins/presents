package com.github.johnnyjayjay.presents.conversation;

import org.bukkit.Effect;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

import static com.github.johnnyjayjay.presents.conversation.Util.findMatch;
import static com.github.johnnyjayjay.presents.conversation.Util.getPresent;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class EffectPrompt extends ValidatingPrompt {

  public static EffectPrompt INSTANCE = new EffectPrompt();

  private EffectPrompt() {
  }

  @Override
  protected boolean isInputValid(ConversationContext context, String input) {
    return "skip".equalsIgnoreCase(input) || findMatch(Effect.class, input) != null;
  }

  @Override
  protected Prompt acceptValidatedInput(ConversationContext context, String input) {
    if (!"skip".equalsIgnoreCase(input)) {
      getPresent(context).setEffect(findMatch(Effect.class, input));
    }
    return EndPrompt.INSTANCE;
  }

  @Override
  public String getPromptText(ConversationContext conversationContext) {
    return "§aPlease enter the name of the effect the present should produce in the world. Type 'skip' if there should be no effect";
  }

  @Override
  protected String getFailedValidationText(ConversationContext context, String invalidInput) {
    return "§6" + invalidInput + "§c is not a valid effect.";
  }
}
