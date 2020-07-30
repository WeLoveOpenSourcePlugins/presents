package com.github.johnnyjayjay.presents;

import com.github.johnnyjayjay.compatre.NmsDependent;
import com.github.johnnyjayjay.presents.effect.PresentEffectRunnable;
import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@NmsDependent
public class PresentListener implements Listener {

  private final Plugin plugin;
  private final PresentLocations presentLocations;
  private final ConfigurationSection presentConfig;

  public PresentListener(Plugin plugin, PresentLocations presentLocations, ConfigurationSection presentConfig) {
    this.plugin = plugin;
    this.presentLocations = presentLocations;
    this.presentConfig = presentConfig;
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onPresentPlace(BlockPlaceEvent event) {
    ItemStack item = CraftItemStack.asNMSCopy(event.getItemInHand());
    if (item.hasTag() && item.getTag().hasKey("present")) {
      String presentName = item.getTag().getString("present");
      if (presentConfig.contains(presentName)) {
        Location location = event.getBlock().getLocation();
        presentLocations.registerPresent(location, presentName);
        new PresentEffectRunnable(presentName, presentConfig, presentLocations, location)
            .runTaskTimer(plugin, 0, 20);
      }
    }
  }

  @EventHandler
  public void onPresentOpen(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
      return;
    }

    Block block = event.getClickedBlock();
    presentLocations.getPresentAt(block.getLocation())
        .map((name) -> (Present) presentConfig.get(name))
        .ifPresent((present) -> {
          event.setCancelled(true);
          present.getCommands(event.getPlayer()).forEach((command) -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
          presentLocations.removePresent(block.getLocation());
          block.setType(Material.AIR);
        });
  }

  private void openPresent(Player player, Present present, Block block) {

  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onPresentDestroy(BlockBreakEvent event) {
    presentLocations.removePresent(event.getBlock().getLocation());
  }

}
