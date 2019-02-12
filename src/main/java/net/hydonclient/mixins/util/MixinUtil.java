package net.hydonclient.mixins.util;

import net.hydonclient.Hydon;
import net.minecraft.util.Util;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nullable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Mixin(Util.class)
public abstract class MixinUtil {

    /**
     * @author Runemoro
     * @reason Safer scheduled exception tasks
     */
    @Overwrite
    @Nullable
    public static <V> V runTask(FutureTask<V> task, Logger logger) {
        task.run();
        try {
            return task.get();
        } catch (InterruptedException | ExecutionException e) {
            Hydon.LOGGER.warn("Error executing task", e);
        }

        return null;
    }
}
