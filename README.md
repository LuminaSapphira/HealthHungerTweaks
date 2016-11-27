# Health and Hunger Tweaks
### For Minecraft 1.10.2 (Forge 2151 / AppleCore 2.1.0)
Modifies the way Minecraft manages food and the corresponding health regeneration in the following ways:
 * Decreases hunger drain (1/2 normal rate)
 * Gives an effect (Satiated) to players when eating food that disables hunger drain for its duration
 * Disables health regeneration from saturation and fullness (*come on, it had to be balanced*)
 * Provides a Health Kit system to compensate

[Check out the longer documentation](https://github.com/CerulanLumina/HealthHungerTweaks/wiki/Changes-in-Detail)

*Author's Note: This is my first Minecraft mod. I made attempts to follow conventions, but no promises.*

**Most properties are configurable in the well-documented configuration file**
```
# Configuration file

exhaustion {
    # An exhaustion modifier that will be multiplied to the default maximum exhausion. Higher values mean slower food drain.
    D:exhaustionModifier=2.0
}


mending {
    # Declares a whitelist of damage sources that will apply a mending effect after taking damage. Has no effect if useDamageWhitelist is true. Available values: inFire, lightningBolt, onFire, lava, hotFloor, inWall, drown, cactus, fall, flyIntoWall, outOfWorld, generic, magic, wither, anvil, fallingBlock, dragonBreath
    S:damageBlacklist <
     >

    # Declares a whitelist of damage sources that will apply a mending effect after taking damage. Has no effect if useDamageWhitelist is false. Available values: inFire, lightningBolt, onFire, lava, hotFloor, inWall, drown, cactus, fall, flyIntoWall, outOfWorld, generic, magic, wither, anvil, fallingBlock, dragonBreath
    S:damageWhitelist <
        hotFloor
        inWall
        drown
        cactus
        fall
        flyIntoWall
        fallingBlock
     >

    # Toggles whether regular regen (from food) should be disabled, and players must use health kits. Recommended if food is made easier.
    B:disableRegularRegen=true

    # The highest health from which the mending buff will not get applied. (If you drop below this health, you will lose the mending effect)
    I:maxUnrecoverableHealth=5

    # Sets whether to use a whitelist or a blacklist for applying the mending effect following damage from a damage source.
    B:useDamageWhitelist=true
}


satiated {
    # Toggles whether eating food gives a satiated effect that disabled food drain for the duration.
    B:enableSatiated=true

    # This value will be multipled to the food value of the food to get the duration (in ticks) of the satiated effect.
    I:satiatedDuration=600
}



```