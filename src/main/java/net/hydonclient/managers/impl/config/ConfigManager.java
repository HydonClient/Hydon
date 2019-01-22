package net.hydonclient.managers.impl.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    /**
     * List of registered configuration classes
     */
    private List<Object> registeredObjects = new ArrayList<>();

    /**
     * GSON used to create a JSON file for configuration
     */
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * The configuration file
     */
    private File configFile;

    /**
     * The config manager instance
     *
     * @param configFile the configuration file
     */
    public ConfigManager(File configFile) {
        this.configFile = configFile;
    }

    /**
     * Register any class that's called using
     * HydonManagers().getConfigManager().register(new Class(anything else here));
     *
     * @param obj the class being registered
     */
    public void register(Object obj) {
        registeredObjects.add(obj);
    }

    /**
     * Save the configuration file
     */
    public void save() {
        JsonObject saveObject = new JsonObject();
        for (Object o : registeredObjects) {
            Class<?> clazz = o.getClass();
            JsonObject classObject = new JsonObject();
            for (Field f : clazz.getDeclaredFields()) {
                if (f.getAnnotation(SaveVal.class) != null) {
                    try {
                        f.setAccessible(true);
                        classObject.add(f.getName(), gson.toJsonTree(f.get(o)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            saveObject.add(clazz.getName(), classObject);
        }
        try {
            if (!configFile.exists())
                configFile.createNewFile();
            IOUtils.write(gson.toJson(saveObject), new FileOutputStream(configFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the configuration file
     */
    public void load() {
        try {
            if (!configFile.exists())
                configFile.createNewFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile));
            JsonObject jsonObject = gson.fromJson(bufferedReader, JsonObject.class);
            if (jsonObject == null)
                return;
            for (Object o : registeredObjects) {
                try {
                    Class<?> clazz = o.getClass();
                    if (!jsonObject.has(clazz.getName()))
                        continue;
                    JsonObject classObject = jsonObject.getAsJsonObject(clazz.getName());
                    for (Field f : clazz.getDeclaredFields()) {
                        if (f.isAnnotationPresent(SaveVal.class) && classObject.has(f.getName())) {
                            try {
                                f.setAccessible(true);
                                f.set(o, gson.fromJson(classObject.get(f.getName()), f.getType()));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
