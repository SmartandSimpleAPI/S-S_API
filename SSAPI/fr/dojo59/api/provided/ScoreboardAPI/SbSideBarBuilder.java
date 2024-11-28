package fr.dojo59.api.provided.ScoreboardAPI;

import fr.dojo59.api.compile.ConsoleAPI.Console;
import fr.dojo59.api.compile.ConsoleAPI.ConsoleColor;
import fr.dojo59.main.SSAPI_Plugin;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Objects;

public class SbSideBarBuilder {

    private Scoreboard scoreboard = null;
    private Objective objective;
    private String title;

    public SbSideBarBuilder(String id, Criteria criteria, String title) {
        if (id == null || criteria == null || title == null) {
            Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.RED + "Arguments SbSideBarBuilder can not be null");
            return;
        }
        objective = scoreboardList.get(id.replace(" ", "_").toLowerCase()).getObjective();

        if (objective == null) {
            scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
            objective = scoreboard.registerNewObjective(id.replace(" ", "_").toLowerCase(), criteria, title);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            this.title = title;

            scoreboardList.put(id.replace(" ", "_").toLowerCase(), this);
        } else {
            scoreboard = scoreboardList.get(id.replace(" ", "_").toLowerCase()).getScoreboard();
            scoreboard.getEntries().forEach(entry -> {
                if (objective.getScore(entry).isScoreSet()) {
                    scoreboard.resetScores(entry);
                }
            });
        }
    }

    private static final HashMap<String, SbSideBarBuilder> scoreboardList = new HashMap<>();


    public SbSideBarBuilder setTitle(String title) {
        objective.setDisplayName(title);
        this.title = title;

        return this;
    }

    public String getTitle() {
        return title;
    }

    public SbSideBarBuilder setLines(String... args) {
        int i = 0;
        for (String line : args) {
            objective.getScore(line).setScore(i);
            i++;
        }

        return this;
    }

    public SbSideBarBuilder setLine(int line, String value) {
        objective.getScore(value).setScore(line);
        return this;
    }

    public Scoreboard build() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public static SbSideBarBuilder editeScoreboard(String id) {
        return scoreboardList.get(id.replace(" ", "_").toLowerCase());
    }
}