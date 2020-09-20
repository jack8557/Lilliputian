package me.godead.lilliputian;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PluginDependencyBuilder {

    @NotNull
    public static List<Dependency> dependencies = new ArrayList<>();

    public PluginDependencyBuilder addDependency(@NotNull Dependency dependency) {
        dependencies.add(dependency);
        return this;
    }

    public void loadDependencies() {
        PluginDependencyBuilder.dependencies.forEach(Dependency::downloadAndLoad);
    }

}
