package me.eXo8_.playerSteps.misc;

import me.eXo8_.playerSteps.PlayerSteps;
import org.bukkit.NamespacedKey;

public class PDC
{
    public static NamespacedKey key;
    public static NamespacedKey timer;

    public static void initialize(PlayerSteps plugin)
    {
        key = new NamespacedKey(plugin, "step");
        timer = new NamespacedKey(plugin, "timer");
    }
}
