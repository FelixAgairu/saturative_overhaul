[English](/README.md) **[简体中文](README-zh_cn.md)**

获取方式：

![Modrinth Downloads](https://img.shields.io/modrinth/dt/saturative-overhaul?logo=modrinth&label=Downloads%20%E4%B8%8B%E8%BD%BD%E9%87%8F&link=https%3A%2F%2Fmodrinth.com%2Fmod%2Fsaturative-overhaul)

## 这是什么
一款生存模组改写了饥饿与饱食度系统\
默认拥有 ~~20~~ ~~400~~ 自定义的食物水平，所以用食物照顾好你自己\
有三层食物状态栏，分别是**饥饿**、**正常**、**暴食**\
让生活更艰苦但又令人愉悦，毕竟现在你的其他食物类mod更重要了

![Default](https://cdn.modrinth.com/data/x5alUhw5/images/4a21e6e0e1b0526412997cf3f4f1489870e2b2bc.jpeg)

## 使用
- 用户\
需要前置/依赖模组[Config Manager](https://modrinth.com/mod/config-manager).
- 开发者\
查看[Github](https://github.com/FelixAgairu/saturative_overhaul)

## 主要区别
**优点**
- 从右向左食物栏布局；
- 自定义食物提供营养值；
- 自定义不同食物水平的食物栏比例；
- 饱食度会随时间流逝，即使你原地不动；
- 可以自定义 [自定义设置](https://github.com/FelixAgairu/saturative_overhaul/edit/master/README.md#自定义设置)；
- 因为几乎没有模组修改`HungerManager.class`，所以按理可兼容任何模组；
- 支持 1.21.1 及以上版本。

**缺点**
- 已移除睡个好觉模组支持；
- 已移除农夫乐事模组支持；
- 已移除苹果皮支持；
- 仅在Fabric。

## 自定义设置
在我的世界安装位置里找到配置文件：\
`%APPDATA%\.minecraft\config\saturative_overhaul.json` Windows默认位置\
文件会包括以下内容:
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

> 现在不是很简单了

#### maxFoodLevel
玩家允许的最大食物等级。

#### defaultFoodLevel
系统初始化时应用的起始或基准食物等级。


#### exhaustionModifier
一个乘数，用于调整疲劳积累的速度。

#### exhaustionCap
在触发效果之前，疲劳值可以存储的上限。

#### nutritionModifier
一个缩放因子，影响从食物中获得的营养值。

#### nutritionRandLow
应用于营养计算的最小随机方差。

#### nutritionRandHigh
应用于营养计算的最大随机方差。

#### saturationRandLow
应用于饱腹感计算的最小随机方差。

#### saturationRandHigh
应用于饱腹感计算的最大随机方差。


#### thresholdStd
用作饥饿相关效果参考点的标准阈值。

#### threshold_Min_ShortEffectId
达到最小阈值时应用的短时状态效果。

#### threshold_Min_ShortEffectAmplification
短时最小阈值效果的放大级别。

#### threshold_Min_LongEffectId
达到最小阈值时应用的长时间状态效果。

#### threshold_Min_LongEffectAmplification
长时间最小阈值效果的放大级别。

#### thresholdMin
开始触发特殊效果的最低饥饿阈值。

#### thresholdMin_Low_EffectId
玩家饥饿值略高于最小阈值时应用的效果。

#### thresholdMin_Low_EffectAmplification
低级最小阈值效果的放大级别。

#### thresholdLow
用于确定轻度饥饿效果的较低饥饿阈值。

#### thresholdMid
用于评估中度效果的中等饥饿阈值。

#### thresholdHigh
用于确定强烈饥饿相关效果的高饥饿阈值。

#### thresholdHigh_ShortEffectId
达到高阈值时应用的短时效果。

#### thresholdHigh_ShortEffectAmplification
短时高阈值效果的放大级别。

#### thresholdHigh_LongEffectId
达到高阈值时应用的长时间效果。

#### thresholdHigh_LongEffectAmplification
长时间高阈值效果的放大级别。

#### thresholdHigh_Max_ShortEffectId
在最高高阈值时应用的短时效果。 #### thresholdHigh_Max_ShortAmplification
短时最大阈值效果的放大级别。

#### thresholdHigh_Max_LongEffectId
达到最大高阈值时应用的长时间效果。

#### thresholdHigh_Max_LongEffectAmplification
长时间最大阈值效果的放大级别。


#### decreaseFoodLevelOverTimeEnabled
指示食物水平是否会随时间自然下降。

#### randomDecreaseFoodLevelEnabled
指示食物水平是否会以随机间隔下降。

#### randomDecreaseFoodLevelBaseTicks
计算随机食物减少量时使用的基本刻度间隔。

#### randomDecreaseFoodLevelMinMultiplier
应用于随机减少时间间隔的最小乘数。

#### randomDecreaseFoodLevelMaxMultiplier
应用于随机减少时间间隔的最大乘数。

#### randomDecreaseFoodLevelPerTickAmounts
随机减少期间每刻度减少的食物量。

#### randomAddExhaustionPerTickAmounts
随机减少事件期间每刻度增加的疲劳值。


#### damageEffectsStarving
玩家饥饿时应用的伤害乘数。

#### damageEffectsOvereating
玩家暴饮暴食时应用的伤害乘数。


#### addRealDamageEnabled
指示饥饿效果是否会造成实际生命值伤害。

#### damagePreTickStarving
饥饿时每刻度造成的伤害量。

#### damagePreTickOvereating
暴饮暴食时每刻度造成的伤害量。

#### damagePreTickEffectsStarving
饥饿时每刻度造成的基于效果的伤害量。

#### damagePreTickEffectsOvereating
暴饮暴食时每刻度造成的基于效果的伤害量。

## Bugs
- ...

## 源于
[Saturative](https://github.com/EmilAhmaBoy/saturative) 作者：EmilAhmaBoy 协议：MIT
