package net.hydonclient.packages;

public abstract class AbstractPackage {

    private PackageManifest packageManifest;

    public abstract void load();

    public abstract void shutdown();

    void setPackageManifest(PackageManifest packageManifest) {
        this.packageManifest = packageManifest;
    }

    public PackageManifest getPackageManifest() {
        return packageManifest;
    }
}
