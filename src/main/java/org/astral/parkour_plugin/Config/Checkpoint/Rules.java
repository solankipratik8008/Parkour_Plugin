package org.astral.parkour_plugin.Config.Checkpoint;

import org.astral.parkour_plugin.Config.Configuration;
import org.astral.parkour_plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Rules {
    // Instances
    private static final Main plugin = Main.getInstance();
    private final Configuration configuration = plugin.getConfiguration();

    // FOLDERS & FILES
    private final String MAPS = Configuration.MAPS;
    private final String MAP_FOLDER;
    // Configuration
    private YamlConfiguration yamlConfiguration;

    // File Names
    public static final String RULES = Configuration.RULES;

    public Rules(final String MAP_FOLDER){
        this.MAP_FOLDER = MAP_FOLDER;
        try {
            yamlConfiguration = configuration.getYamlConfiguration(MAPS, this.MAP_FOLDER);
        } catch (FileNotFoundException e) {
            plugin.getLogger().warning("YAML file not found for " + this.MAP_FOLDER + ".");
        }
    }

    public @NotNull List<Location> getSpawnsLocations() {
        final List<Location> positions = new ArrayList<>();
        final ConfigurationSection spawnsSection = yamlConfiguration.getConfigurationSection("spawns");
        if (spawnsSection == null) return positions;
        for (String key : spawnsSection.getKeys(false)) {
            final ConfigurationSection positionSection = spawnsSection.getConfigurationSection(key);
            if (positionSection == null) continue;
            final String worldName = positionSection.getString("world");
            final World world = Bukkit.getWorld(worldName);
            if (world == null) continue;
            double x = positionSection.getDouble("x");
            double y = positionSection.getDouble("y");
            double z = positionSection.getDouble("z");
            positions.add(new Location(world, x, y, z));
        }
        return positions;
    }

    public @NotNull List<Location> getEndPoints() {
        final List<Location> positions = new ArrayList<>();
        final ConfigurationSection finishSection = yamlConfiguration.getConfigurationSection("finish");
        if (finishSection == null) return positions;
        for (String key : finishSection.getKeys(false)) {
            final ConfigurationSection positionSection = finishSection.getConfigurationSection(key);
            if (positionSection == null) continue;
            final String worldName = positionSection.getString("world");
            final World world = Bukkit.getWorld(worldName);
            if (world == null) continue;
            final double x = positionSection.getDouble("x");
            final double y = positionSection.getDouble("y");
            final double z = positionSection.getDouble("z");
            positions.add(new Location(world, x, y, z));
        }
        return positions;
    }

    public void setSpawns(final @NotNull List<Location> positions) {
        final ConfigurationSection spawnsSection = yamlConfiguration.createSection("spawns");
        for (int i = 0; i < positions.size(); i++) {
            final Location loc = positions.get(i);
            final double x = Math.floor(loc.getX()) + 0.5;
            final double y = Math.floor(loc.getY());
            final double z = Math.floor(loc.getZ()) + 0.5;
            final ConfigurationSection positionSection = spawnsSection.createSection("position_" + i);
            positionSection.set("world", loc.getWorld().getName());
            positionSection.set("x", x);
            positionSection.set("y", y);
            positionSection.set("z", z);
        }
        saveConfiguration();
    }

    public void setEndPoints(@NotNull List<Location> positions) {
        final ConfigurationSection finishSection = yamlConfiguration.createSection("finish");
        for (int i = 0; i < positions.size(); i++) {
            final Location loc = positions.get(i);
            final double x = Math.floor(loc.getX()) + 0.5;
            final double y = Math.floor(loc.getY());
            final double z = Math.floor(loc.getZ()) + 0.5;

            final ConfigurationSection positionSection = finishSection.createSection("position_" + i);
            positionSection.set("world", loc.getWorld().getName());
            positionSection.set("x", x);
            positionSection.set("y", y);
            positionSection.set("z", z);
        }
        saveConfiguration();
    }


    public boolean isEqualsLocation(final @NotNull Location location) {
        final double adjustedX = (int) location.getX() + 0.5;
        final double adjustedZ = (int) location.getZ() + 0.5;
        final double y = location.getY();
        final World world = location.getWorld();

        for (Location end : getEndPoints()) {
            if (end.getWorld().equals(world) &&
                    end.getX() == adjustedX &&
                    end.getY() == y &&
                    end.getZ() == adjustedZ) {
                return true;
            }
        }
        return false;
    }

    private void saveConfiguration() {
        try {
            configuration.saveConfiguration(yamlConfiguration, MAPS, MAP_FOLDER ,RULES);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save configuration", e);
        }
    }
}
