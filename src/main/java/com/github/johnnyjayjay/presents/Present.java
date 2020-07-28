package com.github.johnnyjayjay.presents;

import com.github.johnnyjayjay.compatre.NmsDependent;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagList;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@NmsDependent
public final class Present implements ConfigurationSerializable {

  private final String name;
  private final List<String> commands;
  private String texture;
  private Sound sound;
  private Particle particle;

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

  public Optional<Particle> getParticle() {
    return Optional.ofNullable(particle);
  }

  public void setParticle(Particle particle) {
    this.particle = particle;
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

  @Override
  public Map<String, Object> serialize() {
    ImmutableMap.Builder<String, Object> result = ImmutableMap.builder();
    result.put("name", name)
        .put("commands", commands)
        .put("texture", texture);
    if (sound != null) {
      result.put("sound", sound.name());
    }
    if (particle != null) {
      result.put("particle", particle.name());
    }
    return result.build();
  }

  public static Present deserialize(Map<String, Object> data) {
    Present present = new Present((String) data.get("name"));
    present.commands.addAll((List<String>) data.get("commands"));
    present.texture = (String) data.get("texture");
    present.sound = data.containsKey("sound") ? Sound.valueOf((String) data.get("sound")) : null;
    present.particle = data.containsKey("particle") ? Particle.valueOf((String) data.get("particle")) : null;
    return present;
  }
}
