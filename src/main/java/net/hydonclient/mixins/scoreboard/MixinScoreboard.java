package net.hydonclient.mixins.scoreboard;

import net.hydonclient.mixinsimp.scoreboard.HydonScoreboard;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(Scoreboard.class)
public abstract class MixinScoreboard {

    @Shadow
    @Final
    private Map<String, ScorePlayerTeam> teams;

    @Shadow
    @Final
    private Map<String, ScorePlayerTeam> teamMemberships;

    private HydonScoreboard impl = new HydonScoreboard((Scoreboard) (Object) this);

    /**
     * @author boomboompower
     * @reason Fix NPE's
     */
    @Overwrite
    public void removeTeam(ScorePlayerTeam team) {
        impl.removeTeam(team, teams, teamMemberships);
    }
}
