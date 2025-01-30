package me.eXo8_.playerSteps.event;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.eXo8_.playerSteps.PlayerSteps;
import me.eXo8_.playerSteps.config.Config;
import me.eXo8_.playerSteps.misc.PDC;
import me.eXo8_.playerSteps.step.Step;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

public class EventListener implements Listener
{
    private Map<Player, Boolean> playerMoveLeftMap = new HashMap<>();


    public EventListener(PlayerSteps plugin)
    {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().setResourcePack(Config.resourcePackURL);
    }

    @EventHandler
    public void onWalk(PlayerMoveEvent e) {
        boolean positionChanged = e.getFrom().getX() != e.getTo().getX()
                || e.getFrom().getY() != e.getTo().getY()
                || e.getFrom().getZ() != e.getTo().getZ();

        boolean yawChanged = e.getFrom().getYaw() != e.getTo().getYaw();
        boolean pitchChanged = e.getFrom().getPitch() != e.getTo().getPitch();

        if (!positionChanged && (yawChanged || pitchChanged)) return;
        if (e.getTo().getY() < e.getFrom().getY() || !e.getPlayer().isOnGround()) return;
        if (isTextDisplayNearby(e.getTo())) return;

        Player player = e.getPlayer();

        boolean moveLeft = playerMoveLeftMap.getOrDefault(player, true);

        Step step = new Step(player, moveLeft);
        step.spawnStep();

        playerMoveLeftMap.put(player, !moveLeft);
    }

    @EventHandler
    public void onFall(PlayerJumpEvent e)
    {
        boolean positionChanged = e.getFrom().getX() != e.getTo().getX()
                || e.getFrom().getY() != e.getTo().getY()
                || e.getFrom().getZ() != e.getTo().getZ();

        boolean yawChanged = e.getFrom().getYaw() != e.getTo().getYaw();
        boolean pitchChanged = e.getFrom().getPitch() != e.getTo().getPitch();

        if (!positionChanged && (yawChanged || pitchChanged)) return;
        if (e.getTo().getY() < e.getFrom().getY() || !e.getPlayer().isOnGround()) return;
        if (isTextDisplayNearby(e.getTo())) return;

        Player player = e.getPlayer();

        boolean moveLeft = playerMoveLeftMap.getOrDefault(player, true);

        Step step = new Step(player, moveLeft);
        step.spawnStep();

        playerMoveLeftMap.put(player, !moveLeft);
    }

    private boolean isTextDisplayNearby(Location loc)
    {
        double radius = Config.minStepBlockInterval;

        for (Entity entity : loc.getNearbyEntities(radius, radius, radius))
        {
            if (entity instanceof TextDisplay textDisplay && textDisplay.getPersistentDataContainer().has(PDC.key))
                return true;
        }

        return false;
    }

}
