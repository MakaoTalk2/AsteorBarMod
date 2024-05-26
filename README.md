# AsteorBar

[![](https://img.shields.io/curseforge/dt/959237?logo=curseforge&logoColor=%23000000&label=CurseForge&labelColor=%23f16436&color=%23555555)](https://www.curseforge.com/minecraft/mc-mods/asteorbar)
[![](https://img.shields.io/modrinth/dt/QMWG8bVO?logo=modrinth&logoColor=%23000000&label=Modrinth&labelColor=%2300AF5C&color=%23555555)](https://modrinth.com/mod/asteorbar)

A simple mod to display player's status using slim bars and display entity's status using bars.
Get it from badge links above.

See changelog [here](version_history.md).

## Features

### HUD Overlay

- Vanilla feel. Bars can blink as vanilla hearts do(on regen health, on hurt, and so on).
- Display health, hunger, mount health and more.
- Change the color of the bars while be with regeneration, poison, wither or starvation effects. Frozen also supported.
- Won't be affected by max health. Suitable for situations with high max health.
- Display health and absorption together.
- Display saturation and exhaustion together with hunger.
- Display experience value.
- Bars will flash and shake when the player has low health or hunger.
- Use stacked bars to display health.
- Multiple layout, vanilla and corner included.
- Hide bars when the value has not changed for a while.
- Configurable. You can change whether to display some bars.

### Entity info

- Display living entity's health and max health.
- Display absorption of living entity.
- Very simple with good look.
- Dynamic color of health bar. The color will change when the entity's health is low.
- Highly configurable. You can change whether to display bars in many situations. And you can change many properties of the bars(e.g. color,
  scale, offset...).

### Compatibility

- Mekanism: Forge(1.18.2, 1.19.2, 1.20.1)
- Tough As Nails: Forge(1.18.2, 1.19.2, 1.20.1, 1.20.2, 1.20.4), Fabric(1.19.2, 1.20.1, 1.20.2, 1.20.4, 1.20.6), NeoForge(1.20.2, 1.20.4,
  1.20.6)
- Thirst Was Taken: Forge(1.18.2, 1.19.2, 1.20.1)
- Dehydration: Fabric(1.18.2, 1.19.2, 1.20.1)
- Iron's Spells 'n Spellbooks: Forge(1.18.2, 1.19.2, 1.20.1)
- Light Shield: Forge(1.20.1), NeoForge(1.20.4)
- Parcool: Forge(1.18.2, 1.19.2, 1.19.3, 1.19.4, 1.20.1, 1.20.2)
- Feathers: Forge(1.18.2, 1.19.2, 1.19.3, 1.19.4, 1.20.1)
- Apple Skin: Forge(1.18.2, 1.19.2, 1.19.3, 1.19.4, 1.20.1, 1.20.2, 1.20.4), Fabric(1.18.2,1.19.2,1.19.4, 1.20.1, 1.20.2, 1.20.4, 1.20.6),
  NeoForge(1.20.2, 1.20.4, 1.20.6)
- Vampirism: Forge(1.18.2, 1.19.2, 1.19.3, 1.19.4, 1.20.1), NeoForge(1.20.4)
- Superior Shields: Forge(1.18.2, 1.19.2, 1.20.1)
- Homeostatic: Forge(1.18.2, 1.19.2, 1.19.4, 1.20.1)
- TerraFirmaCraft: Forge(1.18.2, 1.20.1)
- Botania: Forge(1.18.2, 1.19.2, 1.20.1)
- Ars Nouveau: Forge(1.18.2, 1.19.2, 1.20.1)

## Notes

### Data Sync

The following features will not take effect on servers because they are not synced in vanilla Minecraft

- Saturation and exhaustion
- Absorption of living entities

This [plugin](https://www.spigotmc.org/resources/asteorbar.114684/) for Spigot/Paper server can sync saturation and exhaustion to client.

### Compatibility

Due to the limitation of the API, this mod couldn't automatically turn off some third party's mod.
For a better experience, you will need to turn off their HUD manually.
Usually you can achieve this by setting the `enable` option in their config file to `false`.
For those mods that don't provide such an option, you may find `offset` or `location` options in their config file.
By setting these values to a large number(above 1000 is enough in most cases), you can move their HUD out of the screen, which is equivalent
to turning them off.

### Known Incompatibility

Here are some known incompatibility with other mods, and currently there is no solution for them.

- Oculus: while using shaders, living entity's health bar may not display correctly.

## Supported Minecraft versions and mod loaders

Earlier versions is not planned to be supported.

| Version | Forge  | Fabric | NeoForge |
|---------|--------|--------|:--------:|
| 1.18.2  | latest | latest |          |
| 1.19.2  | latest | latest |          |
| 1.19.3  | v1.4.1 | v1.4.1 |          |
| 1.19.4  | latest | latest |          |
| 1.20    | v1.2.2 | v1.2   |          |
| 1.20.1  | latest | latest |          |
| 1.20.2  | latest | latest |  latest  |
| 1.20.3  | v1.2.2 | v1.2   |          |
| 1.20.4  | latest | latest |  latest  |
| 1.20.6  | latest | latest |  latest  |

## Acknowledgements

The mod is inspired by

- [AppleSkin](https://github.com/squeek502/AppleSkin) by squeek502
- [Neat](https://github.com/VazkiiMods/Neat) by VazkiiMods
