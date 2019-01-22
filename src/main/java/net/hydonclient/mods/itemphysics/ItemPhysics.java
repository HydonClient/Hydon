package net.hydonclient.mods.itemphysics;

import net.hydonclient.event.EventBus;
import net.hydonclient.mods.Mod;
import net.hydonclient.mods.Mod.Info;
import net.hydonclient.mods.itemphysics.event.EventHandlerLite;

@Info(name = "Item Physics", author = "CreativeMD", version = "1.0")
public class ItemPhysics extends Mod {

    @Override
    public void load() {
        EventBus.register(new EventHandlerLite());
    }
}
