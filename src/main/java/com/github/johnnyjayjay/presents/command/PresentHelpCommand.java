package com.github.johnnyjayjay.presents.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PresentHelpCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    sender.sendMessage(
        "§aAll available present commands:\n\n"
            + "§6/present list§a - lists all configured present types\n"
            + "§6/present get <name>§a - adds the specified present to your inventory\n"
            + "§6/present config <name>§a - starts a configuration dialog for the given present\n"
            + "§6/present delete <name>§a - deletes the given present"
    );
    return true;
  }

}
