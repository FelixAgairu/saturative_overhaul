package dev.felixagairu.saturative_overhaul.util;

import java.lang.reflect.Field;

public final class ClampValuesProcessor {
    private ClampValuesProcessor(){}

    public static void applyClamps(Object target) {
        Class<?> type = target.getClass();

        for (Field field : type.getDeclaredFields()) {
            ClampValues clamp = field.getAnnotation(ClampValues.class);
            if (clamp == null) {
                continue; // no annotation → skip
            }

            field.setAccessible(true);

            long min = clamp.min();
            long max = clamp.max();

            try {
                Class<?> fieldType = field.getType();

                if (fieldType == int.class || fieldType == Integer.class) {
                    int value = (int) field.get(target);
                    int clamped = Math.clamp((long) value, (int) min, (int) max);
                    field.setInt(target, clamped);
                }

                else if (fieldType == long.class || fieldType == Long.class) {
                    long value = (long) field.get(target);
                    long clamped = Math.clamp(value, min, max);
                    field.setLong(target, clamped);
                }

                else if (fieldType == float.class || fieldType == Float.class) {
                    float value = (float) field.get(target);
                    float fMin = (float) min;
                    float fMax = (float) max;
                    float clamped = Math.clamp(value, fMin, fMax);
                    field.setFloat(target, clamped);
                }

                else if (fieldType == double.class || fieldType == Double.class) {
                    double value = (double) field.get(target);
                    double dMin = (double) min;
                    double dMax = (double) max;
                    double clamped = Math.clamp(value, dMin, dMax);
                    field.setDouble(target, clamped);
                }

                // Narrower integer types via int
                else if (fieldType == short.class || fieldType == Short.class) {
                    short value = (short) field.get(target);
                    int clamped = Math.clamp((long) value, (int) min, (int) max);
                    field.setShort(target, (short) clamped);
                }

                else if (fieldType == byte.class || fieldType == Byte.class) {
                    byte value = (byte) field.get(target);
                    int clamped = Math.clamp((long) value, (int) min, (int) max);
                    field.setByte(target, (byte) clamped);
                }

                // Anything else: ignore
            } catch (IllegalAccessException e) {
                // For your style, I'd wrap and fail hard so it's visible:
                throw new RuntimeException("Failed to apply @Clamp on field "
                        + type.getName() + "#" + field.getName(), e);
            }
        }
    }
}
