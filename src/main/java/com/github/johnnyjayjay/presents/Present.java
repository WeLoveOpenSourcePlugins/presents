package com.github.johnnyjayjay.presents;

import com.google.common.collect.ImmutableMap;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.v1_15_R1.MojangsonParser;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class Present implements ConfigurationSerializable {

  public static final NamespacedKey KEY =
      new NamespacedKey(JavaPlugin.getPlugin(PresentPlugin.class), "present");

  private final String name;
  private final List<String> commands;
  private String texture;

  public Present(String name) {
    this.name = name;
    this.commands = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public List<String> getCommands() {
    return commands;
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

  public ItemStack createItemStack() {
    ItemStack item = new ItemStack(Material.PLAYER_HEAD);
    net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
    try {
      nmsItem.setTag(MojangsonParser.parse(
          "{SkullOwner:{Id:\"c8b28030-905d-4d85-a881-372849a8adc8\", Properties:{textures:[{Value:\"" + texture + "\"}]}}, " +
              "present: \"" + name + "\"}")
      );
    } catch (CommandSyntaxException e) {
      e.printStackTrace();
    }
    return CraftItemStack.asBukkitCopy(nmsItem);
  }

  @Override
  public Map<String, Object> serialize() {
    return ImmutableMap.of("name", name, "commands", commands, "texture", texture);
  }

  public static Present deserialize(Map<String, Object> data) {
    Present present = new Present((String) data.get("name"));
    present.commands.addAll((List<String>) data.get("commands"));
    present.texture = (String) data.get("texture");
    return present;
  }
}
