package com.github.johnnyjayjay.presents.effect;

import com.github.johnnyjayjay.presents.Present;
import com.github.johnnyjayjay.presents.PresentLocations;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PresentEffectRunnable extends BukkitRunnable {

  private final String presentName;
  private final ConfigurationSection presentConfig;
  private final PresentLocations locations;
  private final Location location;

  public PresentEffectRunnable(String presentName, ConfigurationSection presentConfig, PresentLocations locations, Location location) {
    this.presentName = presentName;
    this.presentConfig = presentConfig;
    this.locations = locations;
    this.location = location;
  }

  @Override
  public void run() {
    if (presentConfig.contains(presentName)
        && locations.getPresentAt(location).map(presentName::equals).orElse(false)) {
      Present present = (Present) presentConfig.get(presentName);
      World world = location.getWorld();
      present.getSound().ifPresent((sound) -> world.playSound(location, sound, 1, 1));
      present.getEffect().ifPresent((effect) -> world.playEffect(location, effect, 0));
    } else {
      cancel();
    }
  }

}
