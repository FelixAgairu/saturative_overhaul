**[English](/README.md)** [简体中文](README-zh_cn.md)
## What is that
A mod for Survival that changes player hunger and saturation system.\
By default you got ~~20~~ ~~400~~ the customized Food level, so feed yourself with kinds of food.\
It also has 3 layer of food bar, the **Starving**, **Normal** and **Overeating**.\
Make life hard and enjoyable, cause it gained your food mods importance.

![Default](https://cdn.modrinth.com/data/x5alUhw5/images/4a21e6e0e1b0526412997cf3f4f1489870e2b2bc.jpeg)

## Use
- Users\
Load the require mod [Config Manager](https://modrinth.com/mod/config-manager).
- Developer\
See [Github](https://github.com/FelixAgairu/saturative_overhaul)

## Key Differences
**Pros**
- Right to Left food bar.
- Configured Food's nutrition values and it's randomizer.
- Configured food bar scale with customize Food level.
- Lost saturation level on movement and even you standstill.
- Can be configured with [Customize Settings](https://github.com/FelixAgairu/saturative_overhaul/edit/master/README.md#config).
- No extra mod library needed.
- No other food mod changes required.
- Almost no mod do with `HungerManager.class`, cause this mod should works with others, well.

**Cons**
- Removed sleeptight support.
- Removed farmer delight support.
- Only Minecraft version 1.21.1.
- Only Fabric.

## Config
Locate your config in Minecraft installation location:\
`%APPDATA%\.minecraft\config\saturative_overhaul.json` on Windows, default location.\
You will get somethings like that:
```json
{
    "maxFoodLevel":400,
    "defaultFoodLevel":280,
    
    "exhaustionModifier":3.5,
    "exhaustionCap":60.0,
    "nutritionModifier":5.0,
    "nutritionRandLow":0.8,
    "nutritionRandHigh":1.2,
    "saturationRandLow":0.8,
    "saturationRandHigh":1.2,
    
    "thresholdStd":300,
    "threshold_Min_ShortEffectId":"minecraft:slowness",
    "threshold_Min_ShortEffectAmplification":1,
    "threshold_Min_LongEffectId":"minecraft:nausea",
    "threshold_Min_LongEffectAmplification":0,
    "thresholdMin":25,
    "thresholdMin_Low_EffectId":"minecraft:slowness",
    "thresholdMin_Low_EffectAmplification":0,
    "thresholdLow":100,
    "thresholdMid":250,
    "thresholdHigh":350,
    "thresholdHigh_ShortEffectId":"minecraft:slowness",
    "thresholdHigh_ShortEffectAmplification":0,
    "thresholdHigh_LongEffectId":"minecraft:nausea",
    "thresholdHigh_LongEffectAmplification":0,
    "thresholdHigh_Max_ShortEffectId":"minecraft:slowness",
    "thresholdHigh_Max_ShortAmplification":1,
    "thresholdHigh_Max_LongEffectId":"minecraft:nausea",
    "thresholdHigh_Max_LongEffectAmplification":0,
    
    "decreaseFoodLevelOverTimeEnabled":true,
    "randomDecreaseFoodLevelEnabled":true,
    "randomDecreaseFoodLevelBaseTicks":80,
    "randomDecreaseFoodLevelMinMultiplier":0.5,
    "randomDecreaseFoodLevelMaxMultiplier":1.5,
    "randomDecreaseFoodLevelPerTickAmounts":1,
    "randomAddExhaustionPerTickAmounts":0.025,
    
    "damageEffectsStarving":1.0,
    "damageEffectsOvereating":1.0,
    
    "addRealDamageEnabled":false,
    "damagePreTickStarving":0.025,
    "damagePreTickOvereating":0.025,
    "damagePreTickEffectsStarving":0.001,
    "damagePreTickEffectsOvereating":0.001
}
```
> It's not quite very easy
#### maxFoodLevel
The maximum allowed food level for the player.

#### defaultFoodLevel
The starting or baseline food level applied when initializing the system.


#### exhaustionModifier
A multiplier that adjusts how quickly exhaustion accumulates.

#### exhaustionCap
The upper limit for how much exhaustion can be stored before triggering effects.

#### nutritionModifier
A scaling factor that influences how much nutrition is gained from food.

#### nutritionRandLow
The minimum random variance applied to nutrition calculations.

#### nutritionRandHigh
The maximum random variance applied to nutrition calculations.

#### saturationRandLow
The minimum random variance applied to saturation calculations.

#### saturationRandHigh
The maximum random variance applied to saturation calculations.


#### thresholdStd
The standard threshold used as a reference point for hunger‑related effects.

#### threshold_Min_ShortEffectId
The short‑duration status effect applied when the minimum threshold is reached.

#### threshold_Min_ShortEffectAmplification
The amplification level for the short‑duration minimum‑threshold effect.

#### threshold_Min_LongEffectId
The long‑duration status effect applied when the minimum threshold is reached.

#### threshold_Min_LongEffectAmplification
The amplification level for the long‑duration minimum‑threshold effect.

#### thresholdMin
The lowest hunger threshold at which special effects begin to trigger.

#### thresholdMin_Low_EffectId
The effect applied when the player is slightly above the minimum threshold.

#### thresholdMin_Low_EffectAmplification
The amplification level for the low‑tier minimum‑threshold effect.

#### thresholdLow
The lower hunger threshold used to determine mild hunger effects.

#### thresholdMid
The mid‑range hunger threshold used for moderate effect evaluation.

#### thresholdHigh
The high hunger threshold used to determine strong hunger‑related effects.

#### thresholdHigh_ShortEffectId
The short‑duration effect applied when the high threshold is reached.

#### thresholdHigh_ShortEffectAmplification
The amplification level for the short‑duration high‑threshold effect.

#### thresholdHigh_LongEffectId
The long‑duration effect applied when the high threshold is reached.

#### thresholdHigh_LongEffectAmplification
The amplification level for the long‑duration high‑threshold effect.

#### thresholdHigh_Max_ShortEffectId
The short‑duration effect applied at the maximum high threshold.

#### thresholdHigh_Max_ShortAmplification
The amplification level for the short‑duration maximum‑threshold effect.

#### thresholdHigh_Max_LongEffectId
The long‑duration effect applied at the maximum high threshold.

#### thresholdHigh_Max_LongEffectAmplification
The amplification level for the long‑duration maximum‑threshold effect.


#### decreaseFoodLevelOverTimeEnabled
Indicates whether food level naturally decreases over time.

#### randomDecreaseFoodLevelEnabled
Indicates whether food level decreases at randomized intervals.

#### randomDecreaseFoodLevelBaseTicks
The base tick interval used when calculating random food decreases.

#### randomDecreaseFoodLevelMinMultiplier
The minimum multiplier applied to random decrease timing.

#### randomDecreaseFoodLevelMaxMultiplier
The maximum multiplier applied to random decrease timing.

#### randomDecreaseFoodLevelPerTickAmounts
The amount of food reduced per tick during random decreases.

#### randomAddExhaustionPerTickAmounts
The amount of exhaustion added per tick during random decrease events.


#### damageEffectsStarving
The damage multiplier applied when the player is starving.

#### damageEffectsOvereating
The damage multiplier applied when the player is overeating.


#### addRealDamageEnabled
Indicates whether real health damage is applied from hunger effects.

#### damagePreTickStarving
The amount of pre‑tick damage applied while starving.

#### damagePreTickOvereating
The amount of pre‑tick damage applied while overeating.

#### damagePreTickEffectsStarving
The amount of pre‑tick effect‑based damage applied while starving.

#### damagePreTickEffectsOvereating
The amount of pre‑tick effect‑based damage applied while overeating.


## Bugs
- ...

## Original
Under MIT [Saturative](https://github.com/EmilAhmaBoy/saturative) by EmilAhmaBoy
