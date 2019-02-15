package net.hydonclient.packages;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class MinecraftPackageBootstrap {

    private static final List<AbstractPackage> LOADED_PACKAGES = new ArrayList<>();

    public static void init() {
        List<AbstractPackage> packages = new ArrayList<>();
        LaunchClassLoader classLoader = Launch.classLoader;

        PackageBootstrap.DISCOVERED_PACKAGES.forEach(packageManifest -> {
            try {
                Class<?> clazz = classLoader.findClass(packageManifest.getMainClass());

                if (clazz == null) {
                    System.out.println(
                        "ERROR: Couldn't find package class (" + packageManifest.getName() + ").");
                    return;
                }

                AbstractPackage packageImpl = (AbstractPackage) clazz.getConstructor()
                    .newInstance();
                packageImpl.setPackageManifest(packageManifest);
                packages.add(packageImpl);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        packages.forEach(AbstractPackage::load);
        LOADED_PACKAGES.addAll(packages);
    }

    public static void shutdown() {
        LOADED_PACKAGES.forEach(AbstractPackage::shutdown);
    }

    public static List<AbstractPackage> getLoadedPackages() {
        return LOADED_PACKAGES;
    }

}
