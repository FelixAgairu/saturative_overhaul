/*
 *
 *  * This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *  * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */
// This file includes modifications by Felix.
// Licensed under the MPL 2.0. Original portions © saturative upstream under MIT by EmilAhmaBoy.

package dev.felixagairu.saturative_overhaul;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

/*? if fabric {*/
import net.fabricmc.api.ModInitializer;
 /*?}*/

/*? if neoforge {*/
/*import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
*//*?}*/

/*? if fabric {*/
public class Saturative implements ModInitializer {
    public static final String MOD_ID = "saturative-overhaul";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
    }
}
/*?}*/

/*? if neoforge {*/
/*// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Saturative.MOD_ID)
public class Saturative {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "saturative-overhaul";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Saturative(IEventBus modEventBus, ModContainer modContainer) {
    }
}
*//*?}*/