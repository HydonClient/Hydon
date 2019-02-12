package net.hydonclient.packages.discovery.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import net.hydonclient.util.Variables;
import net.hydonclient.packages.PackageManifest;
import net.hydonclient.packages.discovery.IPackageDiscoverer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.commons.io.IOUtils;

public class FolderPackageDiscoverer implements IPackageDiscoverer {

    @Override
    public List<PackageManifest> discoverPackages() {
        List<PackageManifest> packages = new ArrayList<>();
        LaunchClassLoader classLoader = Launch.classLoader;

        File packageDir = new File(Launch.minecraftHome, "packages");
        boolean mkdirs = packageDir.mkdirs();

        if (mkdirs) {
            for (File file : Objects.requireNonNull(packageDir.listFiles())) {
                if (file.isDirectory() || !file.getName().toLowerCase().endsWith(".jar")) {
                    continue;
                }

                try {
                    JarFile jarFile = new JarFile(file);
                    JarEntry manifestEntry = jarFile.getJarEntry("manifest.json");

                    if (manifestEntry != null && !manifestEntry.isDirectory()) {
                        String json = IOUtils.toString(jarFile.getInputStream(manifestEntry));
                        PackageManifest packageManifest = Variables.GSON
                            .fromJson(json, PackageManifest.class);

                        if (packageManifest.getName() == null || packageManifest.getVersion() == null
                            || packageManifest.getMainClass() == null) {
                            System.out.println(
                                "ERROR: Package doesn't have required attributes. (name, version, main class)");
                            continue;
                        }

                        classLoader.addURL(file.toURI().toURL());
                        packages.add(packageManifest);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return packages;
    }
}
