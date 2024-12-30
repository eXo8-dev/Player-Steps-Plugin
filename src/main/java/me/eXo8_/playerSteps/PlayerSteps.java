package me.eXo8_.playerSteps;

import me.eXo8_.playerSteps.command.Command;
import me.eXo8_.playerSteps.config.Config;
import me.eXo8_.playerSteps.event.EventListener;
import me.eXo8_.playerSteps.misc.PDC;
import me.eXo8_.playerSteps.step.Task;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerSteps extends JavaPlugin
{
    private Config config;

    @Override
    public void onEnable()
    {
        PDC.initialize(this);

        config = new Config(this);

        new Task(config).runTaskTimer(this, 0L, 20L);
        new Command(this, config);
        new EventListener(this, config);
    }
}
