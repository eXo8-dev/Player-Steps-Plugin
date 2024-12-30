package me.eXo8_.playerSteps.config;

import me.eXo8_.playerSteps.PlayerSteps;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config
{
    private final PlayerSteps plugin;
    private FileConfiguration config;

    private String fontText, resourcePackURL;
    private int removalTime;
    private double minStepBlockInterval, randomOffset, baseStep;

    public Config(PlayerSteps plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");

        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdirs();

        if (!file.exists())
            plugin.saveResource("config.yml", false);

        this.config = YamlConfiguration.loadConfiguration(file);

        resourcePackURL = config.getString("resource-pack");
        fontText = config.getString("font");

        removalTime = config.getInt("removal-time");

        minStepBlockInterval = config.getDouble("min-step-block-interval");
        randomOffset = config.getDouble("random-offset");
        baseStep = config.getDouble("base-step");
    }

    public int getRemovalTime()
    {
        return removalTime;
    }

    public double getRandomOffset() {
        return randomOffset;
    }

    public double getBaseStep() {
        return baseStep;
    }

    public String getResourcePackURL()
    {
        return resourcePackURL;
    }

    public String getFontText()
    {
        return fontText;
    }

    public double getMinBlockInterval()
    {
        return minStepBlockInterval;
    }

    public void reload(CommandSender sender) {
        try {
            File file = new File(plugin.getDataFolder(), "config.yml");

            if (!file.exists())
                plugin.saveResource("config.yml", false);

            this.config = YamlConfiguration.loadConfiguration(file);

            resourcePackURL = config.getString("resource-pack");
            fontText = config.getString("font");
            removalTime = config.getInt("removal-time");
            minStepBlockInterval = config.getDouble("min-step-block-interval");
            randomOffset = config.getDouble("random-offset");
            baseStep = config.getDouble("base-step");

            plugin.getLogger().info("Config reloaded!");
            sender.sendMessage("§aReload complete!");
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to reload configuration: " + e.getMessage());
        }
    }

}
