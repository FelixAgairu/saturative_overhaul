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
`%APPDATA%\.minecraft\config\saturative-overhaul.json` Windows 系统的默认位置
> 现在，您将获得一个包含介绍信息的配置文件。

## 问题
- 配置文件的输入值尚未进行验证，例如，如果您将 `thresholdMin` 设置得大于 `thresholdHigh`，就会出现问题。

## 源于
模组（Modrinth）：[saturative](https://modrinth.com/mod/saturative) 代码（GitHub）：[Saturative](https://github.com/EmilAhmaBoy/saturative) 作者：EmilAhmaBoy 协议：MIT
