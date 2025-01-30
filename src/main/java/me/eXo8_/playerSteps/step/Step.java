package me.eXo8_.playerSteps.step;

import me.eXo8_.playerSteps.PlayerSteps;
import me.eXo8_.playerSteps.config.Config;
import me.eXo8_.playerSteps.misc.PDC;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.Random;

public class Step
{
    private Random random = new Random();

    private TextDisplay stepEntity;
    private Player player;

    private boolean moveLeft;

    public Step(Player p, boolean moveLeft)
    {
        this.player = p;
        this.moveLeft = moveLeft;
    }

    public void spawnStep()
    {
        World world = player.getWorld();
        Location playerLoc = player.getLocation();

        float yaw = playerLoc.getYaw();

        double offsetX = 0.0;
        double offsetZ = 0.0;

        double baseDistance = Config.baseStep;

        double randomOffset = Config.randomOffset;
        double randomZ = -randomOffset + random.nextDouble() * (randomOffset * 2);

        if (moveLeft)
        {
            offsetX = -Math.cos(Math.toRadians(yaw)) * baseDistance;
            offsetZ = -Math.sin(Math.toRadians(yaw)) * baseDistance;
        }
        else
        {
            offsetX = Math.cos(Math.toRadians(yaw)) * baseDistance;
            offsetZ = Math.sin(Math.toRadians(yaw)) * baseDistance;
        }

        double finalX = playerLoc.getX() + offsetX;
        double finalZ = playerLoc.getZ() + offsetZ + randomZ;
        double finalY = playerLoc.getY();

        Location spawnLocation = new Location(world, finalX, finalY, finalZ);

        Vector3f scale = new Vector3f(1, 1, 1);

        float scaling = getRandomScale(random);

        scale.x = scaling;
        scale.y = scaling;
        scale.z = scaling;

        Transformation transformation = new Transformation(new Vector3f(0, 0, 0), new AxisAngle4f(0, 0, 0, 0), scale, new AxisAngle4f(0, 0, 0, 0));

        stepEntity = (TextDisplay) player.getWorld().spawnEntity(spawnLocation, EntityType.TEXT_DISPLAY);
        stepEntity.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
        stepEntity.setRotation(yaw, -90.0f);
        stepEntity.setTransformation(transformation);
        stepEntity.setText(Config.fontText);
        stepEntity.getPersistentDataContainer().set(PDC.key, PersistentDataType.STRING, "step");

        startRemovalTask();
    }

    private void startRemovalTask()
    {
        Bukkit.getScheduler().runTaskLater(PlayerSteps.instance, () -> {
            stepEntity.remove();
        }, Config.removalTime * 20);
    }

    private float getRandomScale(Random random) {
        return 0.9f + (1.1f - (0.9f)) * random.nextFloat();
    }
}
