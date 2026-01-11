/*
 *
 *  * This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *  * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

package dev.felixagairu.saturative_overhaul.util;

import com.google.gson.*;
import net.minecraft.text.Text;

import java.util.Map;

public class ConfigTranslator {
    public static String transformJsonString(String jsonString) {
        JsonElement root = JsonParser.parseString(jsonString);

        transform(root);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        return gson.toJson(root);
    }

    private static void transform(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();

            for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                String key = entry.getKey();
                JsonElement value = entry.getValue();

                if (key.endsWith("_") && value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
                    String s = value.getAsString();
                    String replaced = Text.translatable(s).getString();
                    entry.setValue(new JsonPrimitive(replaced));
                }

                transform(value);
            }
        }

        if (element.isJsonArray()) {
            for (JsonElement child : element.getAsJsonArray()) {
                transform(child);
            }
        }
    }
}
