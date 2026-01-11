import com.google.gson.*;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Generator {
    private Generator () {}
    public record Todo(String target, String template, String where, String method) {}
    public record Config(int layer, String type, String name, String value) {}

    public static String PKS_ROOT = "src/main/java/dev/felixagairu/saturative_overhaul";
    public static String ASSETS_ROOT = "src/main/resources/assets/saturative_overhaul";

    public static final List<Todo> JOBS = List.of(
            new Todo(
                    PKS_ROOT + "/util/ConfigHelper.java",
                    "ConfigHelper.java",
                    "// ===== GENERATED CONSTANTS HERE =====",
                    "ConfigHelper"
            ),
            new Todo(
                    PKS_ROOT + "/util/ConfigDefault.java",
                    "ConfigDefault.java",
                    "// ===== GENERATED JSON HERE =====",
                    "ConfigDefault"
            ),
            new Todo(
                    ASSETS_ROOT + "/lang/aa_aa.json",
                    "aa_aa.json",
                    "// ===== GENERATED LANGUAGES HERE =====",
                    "LanguageData"
            )
    );

    public static final List<Config> CONFIGS = List.of(
            new Config(0, "int", "defaultFoodLevel", "280"),
            new Config(0, "int", "guiStarving", "75"),
            new Config(0, "int", "guiNormal", "280"),
            new Config(0, "int", "guiOvereating", "350"),
            new Config(0, "desc", "threshold", "Placeholder"),
            new Config(1, "int", "thresholdMin", "25"),
            new Config(1, "int", "thresholdLow", "100"),
            new Config(1, "int", "thresholdMid", "280"),
            new Config(1, "int", "thresholdHigh", "350"),
            new Config(1, "int", "thresholdMidpointHighMax", "375"),
            new Config(1, "int", "thresholdMax", "400"),
            new Config(0, "desc", "thresholdEffect", "Placeholder"),
            new Config(1, "String", "threshold_Min_ShortEffectId", "minecraft:slowness"),
            new Config(1, "int", "threshold_Min_ShortEffectAmplification", "1"),
            new Config(1, "String", "threshold_Min_LongEffectId", "minecraft:nausea"),
            new Config(1, "int", "threshold_Min_LongEffectAmplification", "0"),
            new Config(1, "String", "thresholdMin_Low_EffectId", "minecraft:slowness"),
            new Config(1, "int", "thresholdMin_Low_EffectAmplification", "0"),
            new Config(1, "String", "thresholdHigh_ShortEffectId", "minecraft:slowness"),
            new Config(1, "int", "thresholdHigh_ShortEffectAmplification", "0"),
            new Config(1, "String", "thresholdHigh_LongEffectId", "minecraft:nausea"),
            new Config(1, "int", "thresholdHigh_LongEffectAmplification", "0"),
            new Config(1, "String", "thresholdHigh_Max_ShortEffectId", "minecraft:slowness"),
            new Config(1, "int", "thresholdHigh_Max_ShortAmplification", "1"),
            new Config(1, "String", "thresholdHigh_Max_LongEffectId", "minecraft:nausea"),
            new Config(1, "int", "thresholdHigh_Max_LongEffectAmplification", "0"),
            new Config(0, "desc", "nutritionModifierSystem", "Placeholder"),
            new Config(1, "boolean", "nutritionModifierEnabled", "true"),
            new Config(1, "float", "nutritionModifier", "5"),
            new Config(1, "boolean", "nutritionRandomModifierEnabled", "true"),
            new Config(1, "float", "nutritionModifierMinMultiplier", "0.75"),
            new Config(1, "float", "nutritionModifierMaxMultiplier", "1.25"),
            new Config(0, "desc", "saturationRandomization", "Placeholder"),
            new Config(1, "boolean", "saturationRandomizationEnabled", "true"),
            new Config(1, "float", "saturationMinMultiplier", "0.75"),
            new Config(1, "float", "saturationMaxMultiplier", "1.25"),
            new Config(0, "desc", "decreaseFoodLevelOverTime", "Placeholder"),
            new Config(1, "boolean", "decreaseFoodLevelOverTimeEnabled", "true"),
            new Config(1, "int", "decreaseFoodLevelBaseTicks", "200"),
            new Config(1, "float", "decreaseFoodLevelBaseAmounts", "1"),
            new Config(1, "boolean", "randomDecreaseFoodLevelEnabled", "true"),
            new Config(1, "float", "randomDecreaseFoodLevelMinMultiplier", "0.75"),
            new Config(1, "float", "randomDecreaseFoodLevelMaxMultiplier", "1.25"),
            new Config(1, "boolean", "decreaseRandomFoodLevelEnabled", "true"),
            new Config(1, "float", "decreaseFoodLevelMinMultiplier", "0.75"),
            new Config(1, "float", "decreaseFoodLevelMaxMultiplier", "1.25"),
            new Config(0, "desc", "exhaustionModifierSystem", "Placeholder"),
            new Config(1, "boolean", "exhaustionModifierEnabled", "true"),
            new Config(1, "float", "exhaustionModifier", "3.5"),
            new Config(1, "float", "exhaustionCap", "60"),
            new Config(0, "desc", "addExhaustionOverTime", "Placeholder"),
            new Config(1, "boolean", "addExhaustionOverTimeEnabled", "true"),
            new Config(1, "int", "addExhaustionBaseTicks", "200"),
            new Config(1, "float", "addExhaustionBaseAmounts", "0.0234375"),
            new Config(1, "boolean", "randomAddExhaustionEnabled", "true"),
            new Config(1, "float", "randomAddExhaustionMinMultiplier", "0.75"),
            new Config(1, "float", "randomAddExhaustionMaxMultiplier", "1.25"),
            new Config(1, "boolean", "addRandomExhaustionEnabled", "true"),
            new Config(1, "float", "addExhaustionMinMultiplier", "0.75"),
            new Config(1, "float", "addExhaustionMaxMultiplier", "1.25"),
            new Config(0, "desc", "addPerTickDamage", "Placeholder"),
            new Config(1, "boolean", "addPerTickDamageEnabled", "false"),
            new Config(1, "String", "damageStarvingDamageTypeId", "minecraft:starve"),
            new Config(1, "float", "damageStarvingDamageAmounts", "0.0234375"),
            new Config(1, "String", "damageOvereatingDamageTypeId", "minecraft:starve"),
            new Config(1, "float", "damageOvereatingDamageAmounts", "0.0234375"),
            new Config(0, "desc", "addPerTickRealDamage", "Placeholder"),
            new Config(1, "boolean", "addPerTickRealDamageEnabled", "false"),
            new Config(1, "float", "realDamageStarvingAmounts", "0.0078125"),
            new Config(1, "float", "realDamageOvereatingAmounts", "0.0078125")
    );

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        for (Todo j : JOBS) {
            genFiles(
                    j.target(),
                    "src/generator/java/generator/" + j.template() + ".template",
                    j.where(),
                    "sb" + j.method()
            );
        }
    }

    private static String addPreIndent(String json, int indentSpaces) {
        String indent = " ".repeat(indentSpaces);
        StringBuilder sb = new StringBuilder();

        for (String line : json.split("\n")) {
            sb.append(indent).append(line).append("\n");
        }

        return sb.toString();
    }

    public static void genFiles(String target, String template, String where, String method) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String templateFileContent = Files.readString(Path.of(template));
        Method m = Generator.class.getMethod(method);
        String output;

        StringBuilder stringBuilder = (StringBuilder) m.invoke(null);

        output = templateFileContent.replace(
                where,
                stringBuilder.toString()
        );

        Files.writeString(
                Path.of(target),
                output
        );
    }

    public static StringBuilder sbConfigHelper() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Config c : CONFIGS) {
            String type = c.type();
            String name = c.name();

            switch (type) {
                case "boolean" -> stringBuilder.append("""
                        \tpublic static final %s %s = parseBooleanSafe(deepGet(configs, "%s"));
                        """.formatted(type, name, name));
                case "String" -> stringBuilder.append("""
                        \tpublic static final %s %s = parseStringSafe(deepGet(configs, "%s"));
                        """.formatted(type, name, name));
                case "int" -> stringBuilder.append("""
                        \tpublic static final %s %s = (%s) clampValue(parseIntegerSafe(deepGet(configs, "%s")));
                        """.formatted(type, name, type, name));
                case "float" -> stringBuilder.append("""
                        \tpublic static final %s %s = (%s) clampValue(parseFloatSafe(deepGet(configs, "%s")));
                        """.formatted(type, name, type, name));
                case "desc" -> stringBuilder.append("""
                        \t//%s
                        """.formatted(name));
            }
        }
        return stringBuilder;
    }

    public static StringBuilder sbConfigDefault() {
        Map<String, Object> root = new LinkedHashMap<>();
        Map<String, Object> currentObj0 = null;
        Map<String, Object> currentObj1 = null;

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        StringWriter writer = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(writer);
        jsonWriter.setIndent("    "); // 4 spaces

        for (Config c : CONFIGS) {

            // Decide where this entry belongs
            Map<String, Object> target;

            if (c.layer() == 0) {
                target = root;
            } else if (c.layer() == 1) {
                target = currentObj0;
            } else {
                target = currentObj1;
            }

            // Add metadata for every entry
            assert target != null;
            target.put(c.name() + "_", "config.desc_" + c.name());


            // Now handle the actual value or object creation
            if (c.layer() == 0) {

                if (!c.type().equals("desc")) {
                    root.put(c.name(), c.value());
                } else {
                    Map<String, Object> obj = new LinkedHashMap<>();
                    root.put(c.name(), obj);
                    currentObj0 = obj;
                }
                currentObj1 = null;
            }

            else if (c.layer() == 1) {

                if (!c.type().equals("desc")) {
                    currentObj0.put(c.name(), c.value());
                } else {
                    Map<String, Object> obj = new LinkedHashMap<>();
                    currentObj0.put(c.name(), obj);
                    currentObj1 = obj;
                }
            }

            else if (c.layer() == 2) {

                if (!c.type().equals("desc")) {
                    currentObj1.put(c.name(), c.value());
                } else {
                    Map<String, Object> obj = new LinkedHashMap<>();
                    currentObj1.put(c.name(), obj);
                    currentObj1 = obj;
                }
            }
        }

        gson.toJson(root, Map.class, jsonWriter);
        String jsonString = addPreIndent(writer.toString(), 4);

        return new StringBuilder(jsonString);
    }
    
    public static StringBuilder sbLanguageData() {
        Map<String, String> translations = new LinkedHashMap<>();
        String configDefaults = sbConfigDefault().toString();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        StringWriter writer = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(writer);
        jsonWriter.setIndent("  "); // 4 spaces

        JsonObject defaults = JsonParser.parseString(configDefaults).getAsJsonObject();

        translations = traverse(defaults, translations);

        gson.toJson(translations, Map.class, jsonWriter);
        String jsonString = writer.toString();


        jsonString = jsonString.substring(2, jsonString.length() - 2);

        return new StringBuilder(jsonString);
    }

    private static Map<String, String> traverse(JsonElement element, Map<String, String> maps) {
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();

            for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                String key = entry.getKey();
                JsonElement value = entry.getValue();

                // If you want the value as a string:
                if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
                    String s = value.getAsString();
                    if (key.endsWith("_")) {
                        maps.put(s, key);
                    }
                }

                // Recurse
                traverse(value, maps);
            }
        }

        else if (element.isJsonArray()) {
            for (JsonElement child : element.getAsJsonArray()) {
                traverse(child, maps);
            }
        }

        return maps;
    }
}