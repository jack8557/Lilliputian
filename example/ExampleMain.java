package example;

import io.github.retrooper.packetevents.PacketEvents;
import me.godead.lilliputian.Dependency;
import me.godead.lilliputian.Lilliputian;
import me.godead.lilliputian.Repository;
import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class ExampleMain extends JavaPlugin {

    @Override
    public void onEnable() {
        final Lilliputian lilliputian = new Lilliputian(this);
        lilliputian.getPluginDependencyBuilder()

                // Jitpack
                .addDependency(new Dependency(
                        Repository.JITPACK,
                        "com.github.retrooper",
                        "packetevents",
                        "1.6.9"))

                // Maven Central
                .addDependency(new Dependency(
                        Repository.MAVENCENTRAL,
                        "commons-io",
                        "commons-io",
                        "2.8.0"))

                // Custom repository
                .addDependency(new Dependency(
                        "https://jitpack.io",
                        "com.github.ProtocolSupport",
                        "ProtocolSupport",
                        "4fdd683"))
                .loadDependencies();

        PacketEvents.init(this);
        PacketEvents.getSettings().setIdentifier("Lilliputian");

        System.out.println(PacketEvents.getSettings().getIdentifier());

        System.out.println(FileUtils.getTempDirectory().getAbsolutePath());
    }
}
