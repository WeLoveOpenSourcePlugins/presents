package com.github.johnnyjayjay.presents;

import com.github.johnnyjayjay.compatre.NmsDependent;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.v1_8_R3.MojangsonParser;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@NmsDependent
public final class Present implements ConfigurationSerializable {

  private static final boolean newSystem;

  static {
    String versionString = Bukkit.getBukkitVersion();
    int[] version = Arrays.stream(versionString.substring(0, versionString.indexOf('-')).split("\\."))
        .mapToInt(Integer::parseInt)
        .toArray();
    newSystem = version[0] > 1
        || (version[0] == 1 && version[1] > 16)
        || (version[0] == 1 && version[1] == 16 && version[2] >= 1);
  }

  private final String name;
  private final List<String> commands;
  private String texture;
  private Sound sound;
  private Effect effect;

  public Present(String name) {
    this.name = name;
    this.commands = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public List<String> getCommands(Player player) {
    return commands.stream()
        .map((template) -> template.replace("%player%", player.getName()))
        .collect(Collectors.toList());
  }

  public void addCommand(String command) {
    commands.add(command);
  }

  public String getTexture() {
    return texture;
  }

  public void setTexture(String texture) {
    this.texture = texture;
  }

  public Optional<Sound> getSound() {
    return Optional.ofNullable(sound);
  }

  public void setSound(Sound sound) {
    this.sound = sound;
  }

  public Optional<Effect> getEffect() {
    return Optional.ofNullable(effect);
  }

  public void setEffect(Effect effect) {
    this.effect = effect;
  }

  public ItemStack createItemStack() {
    ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
    net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
    try {
      String nbtString = String.format(
          "{SkullOwner:{Id:%s,Properties:{textures:[{Value:\"%s\"}]}},present:\"%s\"}",
          serializeUuid(UUID.randomUUID()), texture, name
      );
      NBTTagCompound nbt = MojangsonParser.parse(nbtString);
      nmsItem.setTag(nbt);
    } catch (Exception e) {
      throw new AssertionError("NBT Tag parsing failed - This should never happen. If you see this error, please report it @ https://github.wlosp.org/presents/issues", e);
    }
    item = CraftItemStack.asBukkitCopy(nmsItem);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(name);
    item.setItemMeta(meta);
    return item;
  }

  private static String serializeUuid(UUID uuid) {
    if (newSystem) {
      StringBuilder result = new StringBuilder();
      long msb = uuid.getMostSignificantBits();
      long lsb = uuid.getLeastSignificantBits();
      return result.append("[I;")
          .append(msb >> 32)
          .append(',')
          .append(msb & Integer.MAX_VALUE)
          .append(',')
          .append(lsb >> 32)
          .append(',')
          .append(lsb & Integer.MAX_VALUE)
          .append(']')
          .toString();
    } else {
      return '"' + uuid.toString() + '"';
    }
  }

  @Override
  public Map<String, Object> serialize() {
    ImmutableMap.Builder<String, Object> result = ImmutableMap.builder();
    result.put("name", name)
        .put("commands", commands)
        .put("texture", texture);
    if (sound != null) {
      result.put("sound", sound.name());
    }
    if (effect != null) {
      result.put("effect", effect.name());
    }
    return result.build();
  }

  public static Present deserialize(Map<String, Object> data) {
    Present present = new Present((String) data.get("name"));
    present.commands.addAll((List<String>) data.get("commands"));
    present.texture = (String) data.get("texture");
    present.sound = data.containsKey("sound") ? Sound.valueOf((String) data.get("sound")) : null;
    present.effect = data.containsKey("effect") ? Effect.valueOf((String) data.get("effect")) : null;
    return present;
  }
}
