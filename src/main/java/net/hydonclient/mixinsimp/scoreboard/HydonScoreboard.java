package net.hydonclient.mixinsimp.scoreboard;

import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

import java.util.Map;

public class HydonScoreboard {

    private Scoreboard scoreboard;

    public HydonScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public void removeTeam(ScorePlayerTeam team, Map<String, ScorePlayerTeam> teams, Map<String, ScorePlayerTeam> teamMemberships) {
        if (team == null) {
            return;
        }

        if (team.getRegisteredName() != null) {
            teams.remove(team.getRegisteredName());
        }

        for (String s : team.getMembershipCollection()) {
            teamMemberships.remove(s);
        }

        scoreboard.func_96513_c(team);
    }
}
