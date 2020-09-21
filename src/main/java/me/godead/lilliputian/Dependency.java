package me.godead.lilliputian;

/*
 * MIT License
 *
 * Copyright (c) 2020 GoDead
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

        File dir = new File(Lilliputian.path);

        if (!dir.exists()) dir.mkdirs();

        File file = new File(Lilliputian.path + "/" + getJarName());

        if (file.exists()) { System.out.println("Returned, already exists"); return;}

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
