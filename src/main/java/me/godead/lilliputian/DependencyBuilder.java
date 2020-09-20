package me.godead.lilliputian;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DependencyBuilder {

    @NotNull
    public static List<Dependency> dependencies = new ArrayList<>();

    public DependencyBuilder addDependency(@NotNull Dependency dependency) {
        dependencies.add(dependency);
        return this;
    }

    public void loadDependencies() {
        DependencyBuilder.dependencies.forEach(Dependency::downloadAndLoad);
    }

}
