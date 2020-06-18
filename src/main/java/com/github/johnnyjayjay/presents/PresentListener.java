package com.github.johnnyjayjay.presents;

import net.minecraft.server.v1_15_R1.ItemStack;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PresentListener implements Listener {

  private final PresentLocations presentLocations;
  private final ConfigurationSection presentConfig;

  public PresentListener(PresentLocations presentLocations, ConfigurationSection presentConfig) {
    this.presentLocations = presentLocations;
    this.presentConfig = presentConfig;
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onPresentPlace(BlockPlaceEvent event) {
    // TODO 18.06.2020: Add effects (sounds, particles)
    ItemStack item = CraftItemStack.asNMSCopy(event.getItemInHand());
    if (item.hasTag() && item.getTag().hasKey("present")) {
      presentLocations.registerPresent(event.getBlock(), item.getTag().getString("present"));
    }
  }

  @EventHandler
  public void onPresentOpen(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
      return;

    // TODO 18.06.2020: Remove present once clicked
    presentLocations.getPresentAt(event.getClickedBlock())
        .map((id) -> (Present) presentConfig.get(id))
        .ifPresent((present) -> present.openFor(event.getPlayer()));
  }

  @EventHandler(ignoreCancelled = true)
  public void onPresentDestroy(BlockBreakEvent event) {
    presentLocations.removePresent(event.getBlock());
  }

}
