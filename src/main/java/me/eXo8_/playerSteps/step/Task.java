package me.eXo8_.playerSteps.step;

import me.eXo8_.playerSteps.config.Config;
import me.eXo8_.playerSteps.misc.PDC;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class Task extends BukkitRunnable
{
    private Config config;

    public Task(Config config)
    {
        this.config = config;
    }

    @Override
    public void run()
    {
        for (World world : Bukkit.getWorlds())
        {
            for (Entity entity : world.getEntities())
            {
                PersistentDataContainer container = entity.getPersistentDataContainer();

                if (container.has(PDC.key, PersistentDataType.STRING))
                {
                    if (container.has(PDC.timer, PersistentDataType.INTEGER))
                    {
                        int currentTime = container.getOrDefault(PDC.timer, PersistentDataType.INTEGER, config.getRemovalTime());

                        if (currentTime > 0)
                            container.set(PDC.timer, PersistentDataType.INTEGER, currentTime - 1);
                        else
                        {
                            onTimerExpired(entity);
                            container.remove(PDC.timer);
                        }
                    }
                }
            }
        }
    }

    private void onTimerExpired(Entity entity)
    {
        entity.remove();
    }
}
