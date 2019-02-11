package net.hydonclient.packages.transformer.impl;

import net.hydonclient.packages.PackageManifest;
import net.hydonclient.packages.transformer.IPackageTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class ASMPackageTransformer implements IPackageTransformer {

    @Override
    public void transformPackage(PackageManifest manifest) {
        LaunchClassLoader classLoader = Launch.classLoader;

        if (manifest.getTransformerClass() != null) {
            classLoader.registerTransformer(manifest.getTransformerClass());
        }
    }
}
