package net.hydonclient.packages;

public class PackageManifest {

    private String name, version, author, mainClass, mixinConfig, transformerClass;
    private String[] dependencies;

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getAuthor() {
        return author;
    }

    public String getMainClass() {
        return mainClass;
    }

    public String getMixinConfig() {
        return mixinConfig;
    }

    public String getTransformerClass() {
        return transformerClass;
    }

    public String[] getDependencies() {
        return dependencies == null ? new String[0] : dependencies;
    }
}
