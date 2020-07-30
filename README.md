# presents

A Spigot plugin that lets you create and place small presents in your Minecraft worlds 
for other players to find and open them.

## Install

1. Head over to the [releases](../releases) section and download the latest version
2. Drag the file into the `plugins` directory of your server
3. Restart or reload the server
4. Done!

## Use

- [Configuration](#Configuration)
  - [Commands](#Commands)
  - [Texture](#Texture)
  - [Sound And Effect](#Sound-And-Effect)
- [In Game Presents](#In-Game-Presents)
  - [Re-Configuration](#Re-Configuration)
  - [Present Deletion](#Present-Deletion)
- [Permissions](#Permissions)

With `presents`, you create reusable "present types" that define a texture, 
sounds and effects as well as a list of commands to run when a player opens a 
present of that type. Based on those types, you get actual present items to place 
in game.

### Configuration

Create a type using `/present config <name>`. This will initiate a configuration 
dialogue in chat that you may abandon at any time by typing `abort`.

#### Commands

First, you will be asked to enter a list of commands that should be run when a player
opens a present. The commands will be run as if they were typed in the console, so omit the `/`!.
You can use this to say something to the player, to add money to their economy balance,
to give them a special item or effect - be creative!
You can use `%player%` as a placeholder for the player's name.

**For example**, adding the command `say %player% just found a present!` will result in 
`[Server] Johnny just found a present!` when Johnny opens a present of that type.

#### Texture

You will then need to provide a head texture. You can find such textures in the 
[minecraft heads database](https://minecraft-heads.com/custom-heads) in the `Value` field.

**For example**, the texture value for a [purple present](https://minecraft-heads.com/custom-heads/decoration/2477-present-purple)
is `eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzU0MTlmY2U1MDZhNDk1MzQzYTFkMzY4YTcxZDIyNDEzZjA4YzZkNjdjYjk1MWQ2NTZjZDAzZjgwYjRkM2QzIn19fQ==`.
You can find this directly beneath the "Other" section. Click *Copy* to copy the value to the
clipboard, and use *Ctrl + V* to paste it in Minecraft chat.

#### Sound And Effect

You then have the option to set a sound and an effect that will run every second at the 
locations you place presents of this type. If you don't want any sound or effect, you
can skip setting them by typing `skip`.
You can find a list of all available [sounds](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html)/[effects](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Effect.html) 
in the official Spigot docs. Note that the names of some sounds or effects may vary from version to version and some may not even be available in earlier versions.

**For example**, setting the sound to `block note block pling` and the effect to `villager plant grow` will result in the present emitting a "pling" sound and green sparkles every second.

### In Game Presents

To see all available present types, use `/present list`

To get a present item you can place, use `/present get <name>`. After having placed a 
present, it can be opened with a right click. Opening a present means it will run 
the list of commands for the opener and disappear.

#### Re-Configuration

Changes to a present type (by running `/present config <name>` again) will reflect upon
already placed presents with the exception of changes in texture. So you can place 100
presents of type x and afterwards still change the list of commands, sound and effect of 
type x, but changing the texture won't have any effect on the already placed presents.

#### Present Deletion

By running `/present delete <name>`, you can delete a present type. This will also
remove all placed presents of this type from the world.

### Permissions

All `/present` commands require the permission `presents.all` to be run.


