package me.eXo8_.playerSteps;

import me.eXo8_.playerSteps.command.Command;
import me.eXo8_.playerSteps.config.Config;
import me.eXo8_.playerSteps.event.EventListener;
import me.eXo8_.playerSteps.misc.PDC;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerSteps extends JavaPlugin
{
    public static Config config;
    public static PlayerSteps instance;

    @Override
    public void onEnable()
    {
        instance = this;
        PDC.initialize(this);

        config = new Config(this);

        new Command(this, config);
        new EventListener(this);
    }

    @Override
    public void onDisable()
    {
        for (World world : getServer().getWorlds())
        {
            for (Entity entity : world.getEntities())
            {
                if (entity instanceof TextDisplay)
                {
                    PersistentDataContainer pdc = entity.getPersistentDataContainer();

                    if (pdc.has(PDC.key)) entity.remove();
                }
            }
        }
    }
}
