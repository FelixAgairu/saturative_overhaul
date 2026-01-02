/*
 *
 *  * This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *  * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package dev.felixagairu.saturative_overhaul.util;

public class LimitRandomizer {
    /**
     * @return Random float between two scaled bounds.
     * <p>lerp(a,b,r)=a+(b−a)*r</>
     *
     *
     * @param base Input float value.
     * @param low Default the minimum bound.
     * @param high Default the maximum bound.
     */
    public static float generateRandom(float base, float low, float high) {
        if (low > high) {
            float tmp = low; low = high; high = tmp;
        }
        float min = base * low;
        float max = base * high;
        return min + (max - min) * (float) Math.random();
    }
}
