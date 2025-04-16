package org.astral.parkour_plugin.Compatibilizer.Adapters;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class MaterialApi {

    public static Material getMaterial(final String @NotNull ... materialNames) {
        for (String materialName : materialNames) {
            try {
                return Material.valueOf(materialName);
            } catch (IllegalArgumentException ignored) {
            }
        }
        throw new IllegalArgumentException("No se pudo encontrar un material válido con los nombres proporcionados: " + Arrays.toString(materialNames));
    }
}