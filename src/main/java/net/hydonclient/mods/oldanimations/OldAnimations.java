package net.hydonclient.mods.oldanimations;

import net.hydonclient.managers.HydonManagers;
import net.hydonclient.mods.Mod;
import net.hydonclient.mods.Mod.Info;
import net.hydonclient.mods.oldanimations.config.OldAnimationsConfig;

@Info(name = "Old Animations", author = "OrangeMarshall", version = "1.0")
public class OldAnimations extends Mod {

    @Override
    public void load() {
        HydonManagers.INSTANCE.getConfigManager().register(new OldAnimationsConfig());
    }
}
