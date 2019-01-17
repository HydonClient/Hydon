package net.hydonclient.mixins.client.resources;

import net.hydonclient.mixinsimp.client.resources.HydonLocale;
import net.minecraft.client.resources.Locale;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

@Mixin(Locale.class)
public abstract class MixinLocale {

    @Shadow
    protected abstract void loadLocaleData(InputStream input) throws IOException;

    /**
     * @author asbyth
     * @reason Register custom languages
     */
    @Redirect(method = "loadLocaleDataFiles", at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"))
    private String loadLocaleDataFiles(String format, Object... args) {
        for (Supplier<InputStream> supplier : HydonLocale.LANGUAGE_FILES.get(args[0].toString())) {
            try (InputStream stream = supplier.get()) {
                loadLocaleData(stream);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return String.format(format, args);
    }
}
