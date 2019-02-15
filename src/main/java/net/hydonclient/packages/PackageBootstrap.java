package net.hydonclient.packages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.hydonclient.packages.discovery.impl.DevelopmentPackageDiscoverer;
import net.hydonclient.packages.discovery.impl.FolderPackageDiscoverer;
import net.hydonclient.packages.transformer.impl.ASMPackageTransformer;
import net.hydonclient.packages.transformer.impl.MixinPackageTransformer;

public class PackageBootstrap {

    public static final List<PackageManifest> DISCOVERED_PACKAGES = new ArrayList<>();

    public static void init() {
        List<PackageManifest> packageManifests = new ArrayList<>();

        Arrays.asList(
            new DevelopmentPackageDiscoverer(),
            new FolderPackageDiscoverer()
        ).forEach(
            iPackageDiscoverer -> packageManifests.addAll(iPackageDiscoverer.discoverPackages()));

        List<String> resolved = new ArrayList<>(), unresolved = new ArrayList<>();
        for (PackageManifest manifest : packageManifests) {
            if (!resolveDependency(manifest, resolved, unresolved)) {
                return;
            }
        }

        DISCOVERED_PACKAGES.addAll(Arrays.asList(resolved.stream().map(
            s -> packageManifests.stream()
                .filter(manifest -> manifest.getName().equalsIgnoreCase(s))
                .findFirst().orElse(null)).filter(Objects::nonNull)
            .toArray(PackageManifest[]::new)));

        Arrays.asList(
            new MixinPackageTransformer(),
            new ASMPackageTransformer()
        ).forEach(
            iPackageTransformer -> DISCOVERED_PACKAGES
                .forEach(iPackageTransformer::transformPackage));
    }

    private static boolean resolveDependency(PackageManifest manifest, List<String> resolved,
        List<String> unresolved) {
        unresolved.add(manifest.getName());

        for (String dependency : manifest.getDependencies()) {
            if (!resolved.contains(dependency)) {
                if (unresolved.contains(dependency)) {
                    System.out.println(
                        "ERROR: Circular dependency reference found for package (" + manifest
                            .getName()
                            + ").");
                    return false;
                }

                resolveDependency(manifest, resolved, unresolved);
            }
        }

        resolved.add(manifest.getName());
        unresolved.remove(manifest.getName());
        return true;
    }
}
