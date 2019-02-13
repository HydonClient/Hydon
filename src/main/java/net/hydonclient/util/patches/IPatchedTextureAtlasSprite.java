package net.hydonclient.util.patches;

public interface IPatchedTextureAtlasSprite {
    void markNeedsAnimationUpdate();
    boolean needsAnimationUpdate();
    void unmarkNeedsAnimationUpdate();
}
