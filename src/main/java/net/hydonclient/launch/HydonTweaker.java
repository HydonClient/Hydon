package net.hydonclient.launch;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

public class HydonTweaker implements ITweaker {

    private List<String> newArgs;

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        newArgs = new ArrayList<>(args);
        if (gameDir != null) {
            newArgs.addAll(Arrays.asList("--gameDir", gameDir.getAbsolutePath()));
        }
        if (assetsDir != null) {
            newArgs.addAll(Arrays.asList("--assetsDir", assetsDir.getAbsolutePath()));
        }
        if (profile != null) {
            newArgs.addAll(Arrays.asList("--version", profile));
        }
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.hydon.json");
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        return newArgs.toArray(new String[0]);
    }
}
