package com.github.johnnyjayjay.presents;

import java.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public final class DelegatingCommand implements CommandExecutor {

  private final Map<String, ? extends CommandExecutor> children;
  private final CommandExecutor defaultCase;

  public DelegatingCommand(Map<String, ? extends CommandExecutor> children) {
    this((s, c, l, a) -> false, children);
  }

  public DelegatingCommand(CommandExecutor defaultCase, Map<String, ? extends CommandExecutor> children) {
    this.children = new HashMap<>(children);
    this.defaultCase = defaultCase;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length > 0) {
      String childLabel = args[0];
      if (children.containsKey(childLabel)) {
        return children.get(childLabel).onCommand(sender, command, childLabel, Arrays.copyOfRange(args, 1, args.length));
      }
    }
    return defaultCase.onCommand(sender, command, label, args);
  }
}
