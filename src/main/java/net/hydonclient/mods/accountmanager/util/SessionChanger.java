package net.hydonclient.mods.accountmanager.util;

import net.hydonclient.Hydon;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.lang.reflect.Field;

public class SessionChanger {

    public static void setSession(Session newSession) throws Exception {
        Class<? extends Minecraft> mc = Minecraft.getMinecraft().getClass();

        try {
            Field session = null;

            for (Field field : mc.getDeclaredFields()) {
                if (field.getType().isInstance(newSession)) {
                    session = field;
                    Hydon.LOGGER.info("Attempting injection into Session");
                }
            }

            if (session == null) {
                throw new IllegalStateException("No field of type " + Session.class.getCanonicalName() + " declared.");
            }

            session.setAccessible(true);
            session.set(Minecraft.getMinecraft(), newSession);
            session.setAccessible(false);
        } catch (Exception e) {
            Hydon.LOGGER.warn("Failed to inject to session", e);
            throw e;
        }
    }
}
