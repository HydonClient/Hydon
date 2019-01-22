package net.hydonclient.managers.impl.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SaveVal {

    /*
     * Used to create saved options in the configuration file
     * Anything annotated with @SaveVal and is registered to the config manager
     * will be saved into Hydon/config.json
     */
}
