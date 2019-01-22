package net.hydonclient.mixinsimp.client.resources;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.InputStream;
import java.util.function.Supplier;

public class HydonLocale {

    public static Multimap<String, Supplier<InputStream>> LANGUAGE_FILES = ArrayListMultimap.create();

    public static void registerLanguages(String language) {
        LANGUAGE_FILES.put(language, () -> HydonLocale.class.getResourceAsStream("/assets/minecraft/lang/" + language + ".lang"));
    }
}
