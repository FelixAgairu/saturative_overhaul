## What is that
A mod for Survival that changes player hunger and saturation system.\
By default you got ~~20~~ **400** Food level, so feed yourself with kinds of food.\
It also has 3 layer of food bar, the **Starving**, **Normal** and **Overeating**.\
Make life hard and enjoyable, cause it gained your food mods importance.
## 这是什么
一款生存模组改写了饥饿与饱食度系统\
默认拥有~~20~~ **400**点食物水平，所以用食物照顾好你自己\
有三层食物状态栏，分别是**饥饿**、**正常**、**暴食**\
让生活更艰苦但又令人愉悦，毕竟现在你的其他食物类mod更重要了

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

## 主要区别
**优点**
- 从右向左食物栏布局；
- 食物提供值改变为原先5倍外加抖动；
- 根据自定义的食物水平，自动调整食物栏比例；
- 饱食度会随时间流逝，即使你原地不动；
- 可以自定义 [自定义设置](https://github.com/FelixAgairu/saturative_overhaul/edit/master/README.md#自定义设置)；
- 无需其他模组库依赖；
- 无需修改其他食物模组；
- 因为几乎没有模组修改`HungerManager.class`，所以按理可兼容任何模组。

**缺点**
- 已移除睡个好觉模组支持；
- 已移除农夫乐事模组支持；
- 仅支持游戏版本1.21.1；
- 仅在Fabric。

## Config
Locate your config in Minecraft installation location:\
`%APPDATA%\.minecraft\config\saturative_overhaul.json` on Windows, default location.\
You will get somethings like that:
```
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
## 自定义设置
在我的世界安装位置里找到配置文件：\
`%APPDATA%\.minecraft\config\saturative_overhaul.json` Windows默认位置\
文件会包括以下内容:
```
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
> 很简单
![Desc](https://github.com/FelixAgairu/saturative_overhaul/blob/master/src/main/resources/assets/saturative_overhaul/pic/2.jpg?raw=true)

#### "foodLevel"
> Max level that you can have.
> 最大食物水平。

#### "defaultFoodLevel"
> New player or after-death etc default level.
> 默认食物水平，新玩家或重生后。

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

#### "foodLevel"
> 最大食物水平

#### "defaultFoodLevel"
> 默认食物水平，新玩家或重生后

#### "nutritionModifier"
> 用于帮助你增加食物水平\
> 如果原版游戏里一个食物增加`2`的食物水平\
> 本模组会将`2`变成*接近*`10` [查看代码](https://github.com/FelixAgairu/saturative_overhaul/blob/master/src/main/java/dev/emilahmaboy/felixagairu/saturative_overhaul/mixin/HungerManagerMixin.java)\
> 从而帮助你使用本模组进行游戏\

> 以下四个设置也用于于上述内容
> 同样用于让生活更多样:[查看代码](https://github.com/FelixAgairu/saturative_overhaul/blob/master/src/main/java/dev/emilahmaboy/felixagairu/saturative_overhaul/tools/LimitRandomizer.java)\

- **"nutritionRandLow"**
随机营养值下限
- **"nutritionRandHigh"**
随机营养值上限
- **"saturationRandLow"**
随机饱食度下限
- **"saturationRandHigh"**
随机饱食度上限

#### "thresholdStd"
> 食物水平介于和`thresholdMin`之间食物栏显示**正常**层

#### "thresholdMin"
> 食物水平低于，显示**饥饿**层\
> 会缓慢和恶心

#### "thresholdLow"
> 变得更容易饿、累\
> 缓慢

#### "thresholdMid"
> 会更饿、累

#### "thresholdHigh"
> 食物水平大于, 显示**暴食**层\
> 缓慢！恶心！你为什么要吃这么多？

## Bugs
- Apple skin support almost break, miss match the values with GUI.
- 苹果皮模组支持几乎无法使用，显示值于实际增加值不对应
- ...

## Original
[Saturative](https://github.com/EmilAhmaBoy/saturative) by EmilAhmaBoy
