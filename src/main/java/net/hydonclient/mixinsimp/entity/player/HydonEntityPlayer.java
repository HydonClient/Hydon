package net.hydonclient.mixinsimp.entity.player;

import net.hydonclient.Hydon;
import net.minecraft.entity.player.EntityPlayer;

public class HydonEntityPlayer {

    private EntityPlayer parent;

    private float currentHeight = 1.62F;
    private long lastMillis = System.currentTimeMillis();

    public HydonEntityPlayer(EntityPlayer parent) {
        this.parent = parent;
    }

    public float getEyeHeight() {
        if (Hydon.SETTINGS.oldSneaking) {
            int timeDelay = 1000 / 60;
            if (parent.isSneaking()) {
                float sneakingHeight = 1.54F;
                if (currentHeight > sneakingHeight) {
                    long time = System.currentTimeMillis();
                    long timeSinceLastChange = time - lastMillis;
                    if (timeSinceLastChange > timeDelay) {
                        currentHeight -= 0.012F;
                        lastMillis = time;
                    }
                }
            } else {
                float standingHeight = 1.62F;
                if (currentHeight < standingHeight && currentHeight > 0.2F) {
                    long time = System.currentTimeMillis();
                    long timeSinceLastChange = time - lastMillis;
                    if (timeSinceLastChange > timeDelay) {
                        currentHeight += 0.012F;
                        lastMillis = time;
                    }
                } else {
                    currentHeight = 1.62F;
                }
            }

            if (parent.isPlayerSleeping()) {
                currentHeight = 0.2F;
            }

            return currentHeight;
        } else {
            float eyeHeight = 1.62F;

            if (parent.isPlayerSleeping()) {
                eyeHeight = 0.2F;
            }

            if (parent.isSneaking()) {
                eyeHeight -= 0.08F;
            }

            return eyeHeight;
        }
    }
}
