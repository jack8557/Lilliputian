package me.godead.lilliputian;

public enum Repository {

    JITPACK("https://jitpack.io/%s.jar"),
    MAVENCENTRAL("https://repo1.maven.org/maven2/%s.jar"),
    SONATYPE("https://search.maven.org/remotecontent?filepath=%s.jar"),
    CUSTOM("repo/%s.jar");

    private final String repositoryURL;

    Repository(String repositoryURL) {
        this.repositoryURL = repositoryURL;
    }

    public String getRepositoryURL() {
        return repositoryURL;
    }
}
