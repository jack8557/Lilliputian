package me.godead.lilliputian;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.Channels;

public class Dependency {

    private final Repository repository;

    private final String group;

    private final String name;

    private final String version;

    private final Boolean customRepository;

    private final String repositoryURL;

    public Dependency(Repository repository, String group, String name, String version) {
        this.repositoryURL = null;
        this.customRepository = false;
        this.repository = repository;
        this.group = group;
        this.name = name;
        this.version = version;
    }

    public Dependency(String repositoryURL, String group, String name, String version) {
        this.repositoryURL = repositoryURL;
        this.customRepository = true;
        this.repository = Repository.CUSTOM;
        this.group = group;
        this.name = name;
        this.version = version;
    }

    private String getJarName() {
        return name + "-" + version + ".jar";
    }

    private String getDownloadURL() {
        final String repo = String.format(repository.getRepositoryURL(), group.replace('.', '/') + "/" + name + "/" + version + "/" + name + "-" + version);

        if (customRepository) {
            assert repositoryURL != null : "Error. Something went wrong! Custom repository URL is null.";
            return repo.replace("repo", repositoryURL);
        }

        return repo;
    }

    private final DependencyLoader loader = new DependencyLoader((URLClassLoader) Lilliputian.getPlugin().getClass().getClassLoader());

    protected void downloadAndLoad() {
        // Downloads the dependency
        download();
        // Then loads it into the class path
        load(new File(Lilliputian.path, getJarName()));
    }

    private void load(File file) {
        loader.loadDependency(file);
    }

    private void download() {

        File file = new File(Lilliputian.path);

        if (!file.exists()) file.mkdirs();

        //System.out.println(getDownloadURL());

        try (FileOutputStream outputStream = new FileOutputStream(Lilliputian.path + "/" + getJarName())) {
            outputStream.getChannel()
                    .transferFrom(Channels.newChannel(new URL(getDownloadURL())
                            .openStream()), 0, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
