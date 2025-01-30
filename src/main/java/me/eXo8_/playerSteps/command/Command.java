package me.eXo8_.playerSteps.command;

import me.eXo8_.playerSteps.PlayerSteps;
import me.eXo8_.playerSteps.config.Config;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Command implements CommandExecutor, TabCompleter
{
    private Config config;

    public Command(PlayerSteps plugin, Config config)
    {
        this.config = config;

        plugin.getCommand("player-steps").setExecutor(this);
        plugin.getCommand("player-steps").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.hasPermission("playersteps.reload")) {
            commandSender.sendMessage("§cYou do not have permission!");
            return true;
        }

        config.reload(commandSender);
        commandSender.sendMessage("§aReload complete!");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();

        if (strings.length == 1) {
            if ("reload".startsWith(strings[0].toLowerCase())) {
                completions.add("reload");
            }
        }

        return completions;
    }
}
