package com.github.johnnyjayjay.presents.conversation;

import com.github.johnnyjayjay.presents.Present;
import org.bukkit.Sound;
import org.bukkit.conversations.ConversationContext;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class Util {

  static Present getPresent(ConversationContext context) {
    return (Present) context.getSessionData(Present.class);
  }

  static <T extends Enum<T>> T findMatch(Class<T> enumClass, String input) {
    try {
      return Enum.valueOf(enumClass, input.replace(' ', '_').toUpperCase());
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

}
