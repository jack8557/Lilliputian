package me.godead.lilliputian;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

class DependencyLoader {

    @NotNull
    private final URLClassLoader classLoader;

    @NotNull
    private final List<File> loadedFiles = new ArrayList<>();

    public DependencyLoader(@NotNull final URLClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    //Adds the file to the class path
    protected void loadDependency(File file) {

        if (loadedFiles.contains(file)) return;

        try {
            try {
                Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                method.setAccessible(true);
                method.invoke(classLoader, file.toURI().toURL());
            } catch (Throwable t) {
                t.printStackTrace();
            }
            loadedFiles.add(file);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }

    }
}