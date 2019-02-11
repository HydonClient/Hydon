package net.hydonclient.packages.transformer.impl;

import net.hydonclient.packages.PackageManifest;
import net.hydonclient.packages.transformer.IPackageTransformer;
import org.spongepowered.asm.mixin.Mixins;

public class MixinPackageTransformer implements IPackageTransformer {

    @Override
    public void transformPackage(PackageManifest manifest) {
        if (manifest.getMixinConfig() != null) {
            String mixinConfig = manifest.getMixinConfig();
            Mixins.addConfiguration(mixinConfig);
        }
    }
}
