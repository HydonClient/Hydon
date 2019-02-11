package net.hydonclient.packages.transformer;

import net.hydonclient.packages.PackageManifest;

public interface IPackageTransformer {

    void transformPackage(PackageManifest manifest);

}
