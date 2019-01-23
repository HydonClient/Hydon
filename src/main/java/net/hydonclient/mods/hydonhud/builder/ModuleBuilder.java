package net.hydonclient.mods.hydonhud.builder;

import net.hydonclient.event.events.render.RenderGameOverlayEvent;
import net.hydonclient.mods.hydonhud.HydonHUD;

public interface ModuleBuilder {

    /**
     * Create a display void when extending ModuleBuilder
     * void needs to be manually applied with @EventListener
     *
     * @param event renders hud items
     */
    void display(RenderGameOverlayEvent event);
}
