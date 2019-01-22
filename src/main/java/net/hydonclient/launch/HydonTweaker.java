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

    /**
     * The arguments for when launching Hydon
     */
    private List<String> newArgs;

    /**
     * The options for the client
     *
     * @param args      JVM Arguments
     * @param gameDir   the game directory (typically their minecraft folder)
     * @param assetsDir the textures directory
     * @param profile   the profile they're launching the client from (typically Hydon)
     */
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

    /**
     * Inject Mixins into the client so our features can be used
     *
     * @param classLoader the class being added
     */
    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.hydon.json");
    }

    /**
     * Get the targeted class to direct itself to
     *
     * @return the targeted class
     */
    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    /**
     * The added arguments for when launching the client
     *
     * @return args
     */
    @Override
    public String[] getLaunchArguments() {
        return newArgs.toArray(new String[0]);
    }
}
