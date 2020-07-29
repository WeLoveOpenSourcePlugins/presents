package com.github.johnnyjayjay.presents;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PresentLocations implements ConfigurationSerializable {

  private final Map<Location, String> locationMap;

  public PresentLocations() {
    this(new HashMap<>());
  }

  private PresentLocations(Map<Location, String> locationMap) {
    this.locationMap = locationMap;
  }

  public void registerPresent(Location location, String present) {
    locationMap.put(location, present);
  }

  public void removePresent(Location location) {
    locationMap.remove(location);
  }

  public void removePresentsById(String presentId) {
    List<Location> toRemove = locationMap.keySet().stream()
        .filter((key) -> presentId.equals(locationMap.get(key)))
        .collect(Collectors.toList());

    for (Location location : toRemove) {
      locationMap.remove(location);
      Block block = location.getBlock();
      if (block.getType() == Material.SKULL) {
        block.setType(Material.AIR);
      }
    }
  }

  public Optional<String> getPresentAt(Location location) {
    return Optional.ofNullable(locationMap.get(location));
  }

  @Override
  public Map<String, Object> serialize() {
    List<Map<String, Object>> locations = new ArrayList<>(locationMap.size());
    locationMap.forEach((location, present) -> locations.add(ImmutableMap.of("location", location, "present", present)));
    return ImmutableMap.of("locations", locations);
  }

  public static PresentLocations deserialize(Map<String, Object> data) {
    List<Map<String, Object>> locations = ((List<Map<String, Object>>) data.get("locations"));
    return new PresentLocations(
        locations.stream()
            .collect(Collectors.toMap(
                (entry) -> (Location) entry.get("location"),
                (entry) -> (String) entry.get("present"),
                (left, right) -> right,
                HashMap::new
            ))
    );
  }
}
