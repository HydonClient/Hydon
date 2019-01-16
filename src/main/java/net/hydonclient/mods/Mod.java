package net.hydonclient.mods;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Mod {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Info {
        String name();

        String author();

        String version();
    }

    public abstract void load();

    public String getName() {
        return getClass().getDeclaredAnnotation(Info.class).name();
    }

    public String getAuthor() {
        return getClass().getDeclaredAnnotation(Info.class).author();
    }

    public String getVersion() {
        return getClass().getDeclaredAnnotation(Info.class).version();
    }

}
