package com.github.johnnyjayjay.presents;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PresentListener implements Listener {

  private final ConfigurationSection presents;

  public PresentListener(ConfigurationSection presents) {
    this.presents = presents;
  }

  @EventHandler(ignoreCancelled = true)
  public void onPresentPlace(BlockPlaceEvent event) {
    PersistentDataContainer data = event.getItemInHand().getItemMeta().getPersistentDataContainer();
    if (data.has(Present.KEY, PersistentDataType.STRING)) {
      String name = data.get(Present.KEY, PersistentDataType.STRING);
      // TODO: store present name in block (via config)
    }
  }

  @EventHandler
  public void onPresentOpen(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
      return;

    // TODO: If block is present, get present data and run commands (replacing %player%)
  }

}
