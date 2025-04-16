package org.astral.parkour_plugin.Config.Checkpoint;

import org.astral.parkour_plugin.Config.Configuration;
import org.astral.parkour_plugin.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.FileNotFoundException;
import java.io.IOException;

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

    public void setSpawn(final Location location){

    }

    private void saveConfiguration() {
        try {
            configuration.saveConfiguration(yamlConfiguration, MAPS, MAP_FOLDER ,RULES);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save configuration", e);
        }
    }
}
