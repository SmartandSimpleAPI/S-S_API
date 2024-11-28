package fr.dojo59.api.provided.ScoreboardAPI.bossbar;

import fr.dojo59.api.compile.ConsoleAPI.Console;
import fr.dojo59.api.compile.ConsoleAPI.ConsoleColor;
import fr.dojo59.main.SSAPI_Plugin;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class BossBarBuilder {

    public BossBarBuilder(String id, String title, BarColor color, BarStyle barStyle) {
        if (id == null || title == null || color == null || barStyle == null) {
            Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.RED + "Arguments BossBarBuilder can not be null");
            return;
        }
        this.id = id.replace(" ", "_").toLowerCase();
        this.title = title;
        this.color = color;
        this.barStyle = barStyle;
    }

    private String id;
    private String title;
    private BarColor color;
    private BarStyle barStyle;
    private double progress = 0.0;
    private int delay = 0;
    private int timer = -1;
    private double startValue = 0.0;
    private double endValue = 0.0;
    private boolean visible = true;
    private Player player;

    private static final HashMap<String, BossBar> bossBarList = new HashMap<>();
    private static final HashMap<String, BukkitTask> bossBarAnimationList = new HashMap<>();
    private static final HashMap<String, BossBarBuilder> bossBarBuilderList = new HashMap<>();

    public BossBarBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public BossBarBuilder setColor(BarColor color) {
        this.color = color;
        return this;
    }

    public BossBarBuilder setProgress(double progress, boolean percentage) {
        if (percentage) {
            this.progress = progress / 100;
            return this;
        }
        this.progress = progress;
        return this;
    }

    public BossBarBuilder setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public BossBarBuilder setTimer(int timer) {
        this.timer = timer;
        return this;
    }

    public BossBarBuilder setValueStart(double startValue, boolean percentage) {
        if (percentage) {
            this.startValue = startValue / 100;
            return this;
        }
        this.startValue = startValue;
        return this;
    }

    public BossBarBuilder setEndValue(double endValue, boolean percentage) {
        if (percentage) {
            this.endValue = endValue / 100;
            return this;
        }
        this.endValue = endValue;
        return this;
    }

    public BossBarBuilder setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public BossBarBuilder setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public BossBar build() {
        BossBar bossBar = Bukkit.createBossBar(title, color, barStyle);
        if (timer >= 0 || startValue != 0 || endValue != 0) {
            progress = progress + startValue;
            if (delay != 0) {
                delay = 20 * delay;
            }
            BukkitTask bukkitTask = new BukkitRunnable() {
                @Override
                public void run() {
                    progress = progress + progress;

                    if (progress >= endValue) {
                        bossBar.setProgress(1);
                        cancelTask();
                        return;
                    }
                    bossBar.setProgress(progress);
                }
            }.runTaskTimer(SSAPI_Plugin.getINSTANCE(), delay, 20L * timer);

            bossBarAnimationList.put(id, bukkitTask);
        }

        bossBar.setVisible(visible);

        if (player != null) {
            bossBar.addPlayer(player);
        }

        bossBarList.put(id, bossBar);
        bossBarBuilderList.put(id, this);
        return bossBar;
    }

    public void cancelTask() {
        BukkitTask bukkitTask = bossBarAnimationList.get(id);
        if (bukkitTask != null) {
            bukkitTask.cancel();
            bossBarAnimationList.remove(id);
        }
    }

    public BossBarBuilder delete() {
        BossBar bossBar = bossBarList.get(id);
        if (bossBar != null) {
            bossBar.setVisible(false);
            bossBar.removeAll();
            bossBarList.remove(id);
            cancelTask();
        }
        return this;
    }

    public BossBar getBossBar() {
        return bossBarList.get(id);
    }

    public BossBarBuilder editBossBar(String id) {
        return bossBarBuilderList.get(id);
    }
}
