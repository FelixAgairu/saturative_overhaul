/*
 *
 *  * This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *  * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package dev.felixagairu.saturative_overhaul.tools;

public class LimitRandomizer {
    public static float fTheRandom(float in, float lowMulti, float highMulti) {
        return in * lowMulti + (in * highMulti - in * lowMulti) * (float) Math.random();
    }
}
