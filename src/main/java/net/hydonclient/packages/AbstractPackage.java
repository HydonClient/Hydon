package net.hydonclient.packages;

import net.hydonclient.gui.main.tab.SettingsDropdownElement;

public abstract class AbstractPackage {

    private PackageManifest packageManifest;

    public abstract void load();

    public abstract void shutdown();

    public void loadSettingsElements(SettingsDropdownElement dropdownElement) {

    }

    void setPackageManifest(PackageManifest packageManifest) {
        this.packageManifest = packageManifest;
    }

    public PackageManifest getPackageManifest() {
        return packageManifest;
    }
}
