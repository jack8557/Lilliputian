package me.godead.lilliputian;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Lilliputian {

    private final PluginDependencyBuilder builder = new PluginDependencyBuilder();

    @Nullable
    private static Plugin plugin = null;

    @Nullable
    public static String path = null;

    public Lilliputian(@NotNull final Plugin plugin) {
        plugin(plugin);
        path(plugin.getDataFolder().getParent());
    }

    private void path(@NotNull final String path) {
        Lilliputian.path = path + "/" + "LilliputianLibraries";
    }

    private void plugin(@NotNull final Plugin plugin) {
        Lilliputian.plugin = plugin;
    }

    @NotNull
    public static Plugin getPlugin() {
        assert plugin != null : "Error. Plugin seems to be null";
        return plugin;
    }

    public PluginDependencyBuilder getPluginDependencyBuilder() {
        return builder;
    }
}
