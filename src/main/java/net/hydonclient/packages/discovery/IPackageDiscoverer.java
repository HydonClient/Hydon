package net.hydonclient.packages.discovery;

import java.util.List;
import net.hydonclient.packages.PackageManifest;

public interface IPackageDiscoverer {

    List<PackageManifest> discoverPackages();

}
