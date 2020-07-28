package com.github.johnnyjayjay.presents.conversation;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

import static com.github.johnnyjayjay.presents.conversation.Util.findMatch;
import static com.github.johnnyjayjay.presents.conversation.Util.getPresent;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ParticlePrompt extends ValidatingPrompt {

  public static ParticlePrompt INSTANCE = new ParticlePrompt();

  private ParticlePrompt() {
  }

  @Override
  protected boolean isInputValid(ConversationContext context, String input) {
    return "skip".equalsIgnoreCase(input) || findMatch(Particle.class, input) != null;
  }

  @Override
  protected Prompt acceptValidatedInput(ConversationContext context, String input) {
    if (!"skip".equalsIgnoreCase(input)) {
      getPresent(context).setParticle(findMatch(Particle.class, input));
    }
    return EndPrompt.INSTANCE;
  }

  @Override
  public String getPromptText(ConversationContext conversationContext) {
    return "§aPlease enter the name of the particle the present should produce in the world. Type 'skip' if there should be no particles";
  }

  @Override
  protected String getFailedValidationText(ConversationContext context, String invalidInput) {
    return "§6" + invalidInput + "§c is not a valid particle.";
  }
}
