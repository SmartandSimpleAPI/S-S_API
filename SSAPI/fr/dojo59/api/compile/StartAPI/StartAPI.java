package fr.dojo59.api.compile.StartAPI;


import fr.dojo59.api.compile.ConsoleAPI.Console;
import fr.dojo59.api.compile.ConsoleAPI.ConsoleColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class StartAPI {
    public static void statutPlugin(StatuteMessageList statut, @Nonnull JavaPlugin javaPlugin) {
        PluginDescriptionFile description;
        Logger logger;
        String name;
        String version;
        String authors;
        String statutSelected;
        ConsoleColor color;
        if (statut != null) {
            statutSelected = switch (statut) {
                case ENABLE -> {
                    color = ConsoleColor.GREEN;
                    yield " On";
                }
                case ERROR -> {
                    color = ConsoleColor.GOLD;
                    yield " Erreur";
                }
                case DISABLE -> {
                    color = ConsoleColor.RED;
                    yield " Off";
                }
            };
        } else {
            throw new IllegalArgumentException("StatutMessage cannot be null");
        }
        description = javaPlugin.getDescription();
        logger = javaPlugin.getLogger();
        version = description.getVersion();
        authors = String.join("-", description.getAuthors());
        name = description.getName();

        logStatus(logger, name, version, authors, statutSelected, color, ConsoleColor.LIGHT_PURPLE, ConsoleColor.WHITE);
    }

    public static void statutPlugin(StatuteMessageList statut, ConsoleColor mainColor, ConsoleColor secondColor, @Nonnull JavaPlugin javaPlugin) {
        PluginDescriptionFile description;
        Logger logger;
        String pluginName;
        String version;
        String authors;
        String statutSelected;
        ConsoleColor statutColor;
        if (statut != null) {
            statutSelected = switch (statut) {
                case ENABLE -> {
                    statutColor = ConsoleColor.GREEN;
                    yield " On";
                }
                case ERROR -> {
                    statutColor = ConsoleColor.GOLD;
                    yield " Erreur";
                }
                case DISABLE -> {
                    statutColor = ConsoleColor.RED;
                    yield " Off";
                }
            };
        } else {
            throw new IllegalArgumentException("StatutMessage cannot be null");
        }
        description = javaPlugin.getDescription();
        logger = javaPlugin.getLogger();
        version = description.getVersion();
        authors = String.join("-", description.getAuthors());
        pluginName = description.getName();

        logStatus(logger, pluginName, version, authors, statutSelected, statutColor, mainColor, secondColor);
    }

    private static void logStatus(@Nonnull Logger logger, String pluginName, String version, String authors, String status, ConsoleColor statutColor, ConsoleColor mainColor, ConsoleColor secondColor) {
        if (mainColor == null) mainColor = ConsoleColor.LIGHT_PURPLE;
        if (secondColor == null) secondColor = ConsoleColor.AQUA;

        int lengthPluginName = pluginName.length() + status.length() + 1;
        int lengthVersion = version.length() + 9;
        int lengthAuthors = authors.length() + 4;

        int maxLength = Math.max(lengthPluginName, Math.max(lengthVersion, lengthAuthors)) + 9;

        String tiret = "-".repeat(maxLength);

        int RepeatL1 = (maxLength - lengthPluginName) / 2;
        int RepeatL2 = (maxLength - lengthVersion) / 2;
        int RepeatL3 = (maxLength - lengthAuthors) / 2;

        StringBuilder spaceL1 = new StringBuilder(" ".repeat(RepeatL1));
        StringBuilder spaceL2 = new StringBuilder(" ".repeat(RepeatL2));
        StringBuilder spaceL3 = new StringBuilder(" ".repeat(RepeatL3));

        String spaceBisL1 = "";
        String spaceBisL2 = "";
        String spaceBisL3 = "";

        if ((maxLength - lengthPluginName) % 2 != 0) {
            spaceBisL1 = " ";
        }
        if ((maxLength - lengthVersion) % 2 != 0) {
            spaceBisL2 = " ";
        }
        if ((maxLength - lengthAuthors) % 2 != 0) {
            spaceBisL3 = " ";
        }
        String line2 = mainColor + "   |     " + spaceL1 + pluginName + ":" + statutColor + status + mainColor + spaceL1 + spaceBisL1 + "     |    ";
        String line3 = mainColor + "   |     " + spaceL2 + "Version: " + secondColor + version + mainColor + spaceL2 + spaceBisL2 + "     |    ";
        String line4 = mainColor + "   |     " + spaceL3 + "By: " + secondColor + authors + mainColor + spaceL3 + spaceBisL3 + "     |    ";

        Console.log(logger, "");
        Console.log(logger, mainColor + "---+----+" + tiret + "+----+---");
        Console.log(logger, line2);
        Console.log(logger, line3);
        Console.log(logger, line4);
        Console.log(logger, mainColor + "---+----+" + tiret + "+----+---");
        Console.log(logger, " ");
    }
}
