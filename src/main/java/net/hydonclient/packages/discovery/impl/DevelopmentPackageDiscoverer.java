package net.hydonclient.packages.discovery.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.hydonclient.Variables;
import net.hydonclient.packages.PackageManifest;
import net.hydonclient.packages.discovery.IPackageDiscoverer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.commons.io.IOUtils;

public class DevelopmentPackageDiscoverer implements IPackageDiscoverer {

    @Override
    public List<PackageManifest> discoverPackages() {
        LaunchClassLoader classLoader = Launch.classLoader;
        URL manifestUrl = classLoader.findResource("manifest.json");

        if (manifestUrl != null) {
            try {
                String json = IOUtils.toString(classLoader.getResourceAsStream("manifest.json"));
                PackageManifest packageManifest = Variables.GSON
                    .fromJson(json, PackageManifest.class);

                if (packageManifest.getName() == null || packageManifest.getVersion() == null
                    || packageManifest.getMainClass() == null) {
                    System.out.println(
                        "ERROR: Package doesn't have required attributes. (name, version, main class)");
                    return new ArrayList<>();
                }

                return Collections.singletonList(packageManifest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ArrayList<>();
    }
}
