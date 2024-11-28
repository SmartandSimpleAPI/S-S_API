package fr.dojo59.api.provided.ScoreboardAPI.bossbar;//package fr.dojo59.scoreboard.bossbar;
//
//import fr.dojo59.console.Console;
//import fr.dojo59.console.ConsoleColor;
//import fr.dojo59.main.Main;
//import org.bukkit.Bukkit;
//import org.bukkit.boss.BarColor;
//import org.bukkit.boss.BarStyle;
//import org.bukkit.boss.BossBar;
//import org.bukkit.entity.Player;
//import org.bukkit.scheduler.BukkitRunnable;
//import org.bukkit.scheduler.BukkitTask;
//
//import java.util.HashMap;
//
//public class BossBarBuilder2 {
//
//    public BossBarBuilder2(String id, String name, BarColor color, BarStyle barStyle) {
//        if (id == null || name == null || color == null || barStyle == null) {
//            Console.log(Main.getINSTANCE().getLogger(), ConsoleColor.RED + "Arguments BossBarBuilder can not be null");
//            return;
//        }
//        this.id = id.replace(" ", "_").toLowerCase();
//        this.name = name;
//        this.color = color;
//        this.barStyle = barStyle;
//    }
//    private String id;
//    private String name;
//    private BarColor color;
//    private BarStyle barStyle;
//    private double progress = 0.0;
//    private int delay = 0;
//    private int timer = 0;
//    private double startValue = 0.0;
//    private double endtValue = 0.0;
//
//    private boolean visible = true;
//    private Player player;
//
//    private static final HashMap<String, BossBar> bossBarList = new HashMap<>();
//    private static final HashMap<String, BukkitTask> bossBarAnimationList = new HashMap<>();
//    private static final HashMap<String, BossBarBuilder2> bossBarBuilderList = new HashMap<>();
//
//    public BossBarBuilder2 setName(String name) {
//        this.name = name;
//        return this;
//    }
//
//    public BossBarBuilder2 setColor(BarColor color) {
//        this.color = color;
//        return this;
//    }
//
//    public BossBarBuilder2 setProgress(double progress, boolean percentage) {
//        if (percentage) {
//            this.progress = progress / 100;
//            return this;
//        }
//        this.progress = progress;
//        return this;
//    }
//
//    public BossBarBuilder2 setVisible(boolean visible) {
//        this.visible = visible;
//        return this;
//    }
//
//    public BossBarBuilder2 setPlayer(Player player) {
//        this.player = player;
//        return this;
//    }
//
//    public BossBar build() {
//        BossBar bossBar = Bukkit.createBossBar(name, color, barStyle);
//        bossBar.setProgress(progress);
//        bossBar.setVisible(visible);
//
//        if (player != null) {
//            bossBar.addPlayer(player);
//        }
//
//        bossBarList.put(id, bossBar);
//        return bossBar;
//    }
//
//    public BossBarBuilder2 setProgress(int timer, double value, boolean percentage) {
//        BossBar bossBar = bossBarList.get(id);
//        if (bossBar == null) {
//            return null;
//        }
//        if (percentage) {
//            value = value / 100;
//        }
//
//        double valueFinal = value;
//        BukkitTask bukkitTask = new BukkitRunnable() {
//            double progress = 0;
//
//            @Override
//            public void run() {
//                progress = progress + valueFinal;
//
//                if (progress >= 1) {
//                    bossBar.setProgress(1);
//                    cancelTask();
//                    return;
//                }
//                bossBar.setProgress(progress);
//            }
//        }.runTaskTimer(Main.getINSTANCE(), 0, 20L * timer);
//
//        bossBarAnimationList.put(id, bukkitTask);
//        return this;
//    }
//
//    public BossBarBuilder2 setProgress_Start(int timer, double value, boolean percentage, double startValue) {
//        BossBar bossBar = bossBarList.get(id);
//        if (bossBar == null) {
//            return null;
//        }
//        if (percentage) {
//            value = value / 100;
//            startValue = startValue / 100;
//        }
//        double valueFinal = value;
//        double startValueFinal = startValue;
//
//        BukkitTask bukkitTask = new BukkitRunnable() {
//            double progress = startValueFinal;
//
//            @Override
//            public void run() {
//
//                progress = progress + valueFinal;
//
//                if (progress >= 1) {
//                    bossBar.setProgress(1);
//                    cancelTask();
//                    return;
//                }
//                bossBar.setProgress(progress);
//            }
//        }.runTaskTimer(Main.getINSTANCE(), 0, 20L * timer);
//
//        bossBarAnimationList.put(id, bukkitTask);
//        return this;
//    }
//
//    public BossBarBuilder2 setProgress_End(int timer, double value, boolean percentage, double endValue) {
//        BossBar bossBar = bossBarList.get(id);
//        if (bossBar == null) {
//            return null;
//        }
//        if (percentage) {
//            value = value / 100;
//            endValue = endValue / 100;
//        }
//
//        double valueFinal = value;
//        double endValueFinal = endValue;
//        BukkitTask bukkitTask = new BukkitRunnable() {
//            double progress = 0.00;
//
//            @Override
//            public void run() {
//
//                progress = progress + valueFinal;
//
//                if (progress >= endValueFinal) {
//                    bossBar.setProgress(endValueFinal);
//                    cancelTask();
//                    return;
//                }
//                bossBar.setProgress(progress);
//            }
//        }.runTaskTimer(Main.getINSTANCE(), 0, 20L * timer);
//
//        bossBarAnimationList.put(id, bukkitTask);
//        return this;
//    }
//
//    public BossBarBuilder2 setProgress_Start_End(int timer, double value, boolean percentage, double startValue, double endValue) {
//        BossBar bossBar = bossBarList.get(id);
//        if (bossBar == null) {
//            return null;
//        }
//        if (percentage) {
//            value = value / 100;
//            startValue = startValue / 100;
//            endValue = endValue / 100;
//        }
//
//        double valueFinal = value;
//        double startValueFinal = startValue;
//        double endValueFinal = endValue;
//        BukkitTask bukkitTask = new BukkitRunnable() {
//            double progress = startValueFinal;
//
//            @Override
//            public void run() {
//
//                progress = progress + valueFinal;
//
//                if (progress >= endValueFinal) {
//                    bossBar.setProgress(endValueFinal);
//                    cancelTask();
//                    return;
//                }
//                bossBar.setProgress(progress);
//            }
//        }.runTaskTimer(Main.getINSTANCE(), 0, 20L * timer);
//
//        bossBarAnimationList.put(id, bukkitTask);
//        return this;
//    }
//
//    public BossBarBuilder2 setProgress(int delay, int timer, double value, boolean percentage) {
//        BossBar bossBar = bossBarList.get(id);
//        if (bossBar == null) {
//            return null;
//        }
//        if (percentage) {
//            value = value / 100;
//        }
//
//        double valueFinal = value;
//        BukkitTask bukkitTask = new BukkitRunnable() {
//            double progress = 0;
//
//            @Override
//            public void run() {
//                progress = progress + valueFinal;
//
//                if (progress >= 1) {
//                    bossBar.setProgress(1);
//                    cancelTask();
//                    return;
//                }
//                bossBar.setProgress(progress);
//            }
//        }.runTaskTimer(Main.getINSTANCE(), 20L * delay, 20L * timer);
//
//        bossBarAnimationList.put(id, bukkitTask);
//        return this;
//    }
//
//    public BossBarBuilder2 setProgress_Start(int delay, int timer, double value, boolean percentage, double startValue) {
//        BossBar bossBar = bossBarList.get(id);
//        if (bossBar == null) {
//            return null;
//        }
//        if (percentage) {
//            value = value / 100;
//            startValue = startValue / 100;
//        }
//
//        double valueFinal = value;
//        double startValueFinal = startValue;
//        BukkitTask bukkitTask = new BukkitRunnable() {
//            double progress = startValueFinal;
//
//            @Override
//            public void run() {
//
//                progress = progress + valueFinal;
//
//                if (progress >= 1) {
//                    bossBar.setProgress(1);
//                    cancelTask();
//                    return;
//                }
//                bossBar.setProgress(progress);
//            }
//        }.runTaskTimer(Main.getINSTANCE(), 20L * delay, 20L * timer);
//
//        bossBarAnimationList.put(id, bukkitTask);
//        return this;
//    }
//
//    public BossBarBuilder2 setProgress_End(int delay, int timer, double value, boolean percentage, double endValue) {
//        BossBar bossBar = bossBarList.get(id);
//        if (bossBar == null) {
//            return null;
//        }
//        if (percentage) {
//            value = value / 100;
//            endValue = endValue / 100;
//        }
//
//        double valueFinal = value;
//        double endValueFinal = endValue;
//        BukkitTask bukkitTask = new BukkitRunnable() {
//            double progress = 0.00;
//
//            @Override
//            public void run() {
//
//                progress = progress + valueFinal;
//
//                if (progress >= endValueFinal) {
//                    bossBar.setProgress(endValueFinal);
//                    cancelTask();
//                    return;
//                }
//                bossBar.setProgress(progress);
//            }
//        }.runTaskTimer(Main.getINSTANCE(), 20L * delay, 20L * timer);
//
//        bossBarAnimationList.put(id, bukkitTask);
//        return this;
//    }
//
//    public BossBarBuilder2 setProgress_Start_End(int delay, int timer, double value, boolean percentage, double startValue, double endValue) {
//        BossBar bossBar = bossBarList.get(id);
//        if (bossBar == null) {
//            return null;
//        }
//        if (percentage) {
//            value = value / 100;
//            startValue = startValue / 100;
//            endValue = endValue / 100;
//        }
//
//        double valueFinal = value;
//        double startValueFinal = startValue;
//        double endValueFinal = endValue;
//        BukkitTask bukkitTask = new BukkitRunnable() {
//            double progress = startValueFinal;
//
//            @Override
//            public void run() {
//
//                progress = progress + valueFinal;
//
//                if (progress >= endValueFinal) {
//                    bossBar.setProgress(endValueFinal);
//                    cancelTask();
//                    return;
//                }
//                bossBar.setProgress(progress);
//            }
//        }.runTaskTimer(Main.getINSTANCE(), 20L * delay, 20L * timer);
//
//        bossBarAnimationList.put(id, bukkitTask);
//        return this;
//    }
//
//
//    public void cancelTask() {
//        BukkitTask bukkitTask = bossBarAnimationList.get(id);
//        if (bukkitTask != null) {
//            bukkitTask.cancel();
//            bossBarAnimationList.remove(id);
//        }
//    }
//
//    public BossBarBuilder2 delete() {
//        BossBar bossBar = bossBarList.get(id);
//        if (bossBar != null) {
//            bossBar.setVisible(false);
//            bossBar.removeAll();
//            bossBarList.remove(id);
//            cancelTask();
//        }
//        return this;
//    }
//
//    public static BossBar getBossBar(String id) {
//        return bossBarList.get(id);
//    }
//
//    public BossBarBuilder2 editBossBar(String id) {
//        return bossBarBuilderList.get(id);
//    }
//}
