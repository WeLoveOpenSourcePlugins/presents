package com.github.johnnyjayjay.presents;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    NBTTagCompound baseCompound = nmsItem.getOrCreateTag();
    NBTTagCompound skullOwner = new NBTTagCompound();
    skullOwner.setString("Id", "c8b28030-905d-4d85-a881-372849a8adc8");
    NBTTagCompound properties = new NBTTagCompound();
    NBTTagList textures = new NBTTagList();
    NBTTagCompound textureCompound = new NBTTagCompound();
    textureCompound.setString("Value", texture);
    textures.add(textureCompound);
    properties.set("textures", textures);
    skullOwner.set("Properties", properties);
    baseCompound.set("SkullOwner", skullOwner);
    baseCompound.setString("present", name);
    nmsItem.setTag(baseCompound);
      /* MojangsonParser.parse(
          "{SkullOwner:{Id:\"c8b28030-905d-4d85-a881-372849a8adc8\", Properties:{textures:[{Value:\"" + textureCompound + "\"}]}}, " +
              "present: \"" + name + "\"}") */
    return CraftItemStack.asBukkitCopy(nmsItem);
  }

  public void openFor(Player player) {
    commands.stream()
        .map((template) -> template.replace("%player%", player.getName()))
        .forEach((command) -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
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
