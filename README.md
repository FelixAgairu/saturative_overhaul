**[English](/README.md)** [简体中文](README-zh_cn.md)
## What is that
A mod for Survival that changes player hunger and saturation system.\
By default you got ~~20~~ **400** Food level, so feed yourself with kinds of food.\
It also has 3 layer of food bar, the **Starving**, **Normal** and **Overeating**.\
Make life hard and enjoyable, cause it gained your food mods importance.

![Default](https://cdn.modrinth.com/data/x5alUhw5/images/4a21e6e0e1b0526412997cf3f4f1489870e2b2bc.jpeg)

## Key Differences
**Pros**
- Right to Left food bar.
- Food's nutrition changes to 5x *default* with small randomizer.
- Automatic food bar scale with customize Food level.
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
	"foodLevel":400,
	"defaultFoodLevel":280,
	"nutritionModifier":5,
	"nutritionRandLow":0.8,
	"nutritionRandHigh":1.2,
	"saturationRandLow":0.8,
	"saturationRandHigh":1.2,
	"thresholdStd":300,
	"thresholdMin":25,
	"thresholdLow":100,
	"thresholdMid":250,
	"thresholdHigh":350
}
```
> It's Easy
![Desc](https://github.com/FelixAgairu/saturative_overhaul/blob/master/src/main/resources/assets/saturative_overhaul/pic/2.jpg?raw=true)

#### "foodLevel"
> Max level that you can have.

#### "defaultFoodLevel"
> New player or after-death etc default level.

#### "nutritionModifier"
> To help you gain food level\
> If you got a food add `2` as vanilla does\
> This mod just make `2` to *nearly* `10` [See Code](https://github.com/FelixAgairu/saturative_overhaul/blob/master/src/main/java/dev/emilahmaboy/felixagairu/saturative_overhaul/mixin/HungerManagerMixin.java)\
> To help you play with this mod.\

> The following 4 settings also for that just above
> Also make life random not static:[See Code](https://github.com/FelixAgairu/saturative_overhaul/blob/master/src/main/java/dev/emilahmaboy/felixagairu/saturative_overhaul/tools/LimitRandomizer.java)\

- **"nutritionRandLow"**
For nutrition random low limit.
- **"nutritionRandHigh"**
For nutrition random upper limit.
- **"saturationRandLow"**
For saturation random low limit.
- **"saturationRandHigh"**
For saturation random upper limit.

#### "thresholdStd"
> Between this and `thresholdMin`, GUI show **Normal** Layer

#### "thresholdMin"
> Lower, GUI show **Starving** Layer\
> Make your more slow and nausea

#### "thresholdLow"
> Make your more likely to hunger and tired\
> And make you move slow

#### "thresholdMid"
> Make your likely to hunger and tired

#### "thresholdHigh"
> Lager, GUI show **Overeating** Layer\
> SLOW! NAUSEA! Why You Eat So Much?

## Bugs
- Apple skin support almost break, miss match the values with GUI.
- ...

## Original
[Saturative](https://github.com/EmilAhmaBoy/saturative) by EmilAhmaBoy
